package me.nickotato.shadowSMP.listeners.ghost

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.manager.AbilityManager
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import java.util.UUID

class SprigganListener: Listener {
    private val onGround = mutableSetOf<UUID>()
    private val canLaunch = mutableSetOf<UUID>()

    private val moveStartTime = mutableMapOf<UUID, Int>()
    private val lastMoveTick = mutableMapOf<UUID, Int>()

    @EventHandler
    fun onHit(event: EntityDamageByEntityEvent) {

        val attacker = event.damager as? Player ?: return
        val target = event.entity as? LivingEntity ?: return

        if (!AbilityManager.sprigganUltimatePlayers.contains(attacker.uniqueId)) return

        attacker.world.playSound(attacker.location.clone(), Sound.ENTITY_RABBIT_HURT, 1.0F, 1.0F)

        if (target is Player) {
            target.setCooldown(Material.SHIELD, 200)
        }


        event.damage *= 1.5

        val direction = target.location.toVector()
            .subtract(attacker.location.toVector())

        if (direction.lengthSquared() < 0.0001) return

        val normalized = direction.normalize()

        val launch = normalized.multiply(0.4).setY(0.9)
        attacker.velocity = launch

        target.velocity = target.velocity.add(Vector(0.0, 0.25, 0.0))

        object : BukkitRunnable() {
            override fun run() {
                startSprigganPull(attacker, target)
            }
        }.runTaskLater(ShadowSMP.instance, 3L)

    }

    private fun startSprigganPull(player: Player, target: LivingEntity) {

        object : BukkitRunnable() {

            var ticks = 0
            val maxTicks = 10

            override fun run() {

                if (ticks++ >= maxTicks || !player.isValid || !target.isValid) {
                    cancel()
                    return
                }

                val toTarget = target.location.toVector()
                    .subtract(player.location.toVector())

                if (toTarget.lengthSquared() < 0.0001) return

                val dir = toTarget.normalize()

                val currentY = player.velocity.y.coerceAtLeast(0.15)

                val horizontal = player.velocity.clone()
                horizontal.y = 0.0

                val toward = dir.multiply(1.2)

                player.velocity = horizontal.multiply(0.3)
                    .add(toward)
                    .setY(currentY)
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }

    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        handleNormalAbility(event)
        handlePassive(event)
    }

    private fun handleNormalAbility(event: PlayerMoveEvent) {
        val player = event.player
        val uuid = player.uniqueId
        if (!AbilityManager.sprigganAbilityPlayers.contains(uuid)) return
        val playerAsEntity = player as Entity

        val wasOnGround = onGround.contains(uuid)
        val nowOnGround = playerAsEntity.isOnGround

        if (nowOnGround) {
            onGround.add(uuid)
            canLaunch.add(uuid)
            return
        }

        if (wasOnGround) {
            onGround.remove(uuid)
        }

        if (!canLaunch.contains(uuid)) return

        if (player.velocity.y <= 0.1) return

        AbilityManager.tempNoFallPlayers.add(uuid)
        canLaunch.remove(uuid)

        val dir = player.location.clone().direction.normalize()

        player.velocity = dir.multiply(1.2).setY(0.8)
    }

    private fun handlePassive(event: PlayerMoveEvent) {
        val player = event.player
        val uuid = player.uniqueId

        val data = PlayerManager.getPlayerData(player)
        if (data.ghost != Ghost.SPRIGGAN) return

        val from = event.from
        val to = event.to

        val moved = from.distanceSquared(to) > 0.001

        val currentTick = player.ticksLived

        if (moved) {
            lastMoveTick[uuid] = currentTick

            if (!moveStartTime.containsKey(uuid)) {
                moveStartTime[uuid] = currentTick
            }
        }

        val last = lastMoveTick[uuid] ?: return

        if (currentTick - last > 10) {
            moveStartTime.remove(uuid)
            player.walkSpeed = 0.2f
            return
        }

        val start = moveStartTime[uuid] ?: return
        val movingTime = currentTick - start

        when {
            movingTime >= 300 -> {
                applySpeed(player, 1)
            }

            movingTime >= 100 -> {
                applySpeed(player, 0)
            }

            else -> {
                player.removePotionEffect(PotionEffectType.SPEED)
            }
        }
    }

    private fun applySpeed(player: Player, amplifier: Int) {
        player.removePotionEffect(PotionEffectType.SPEED)
        player.addPotionEffect(
            PotionEffect(
                PotionEffectType.SPEED,
                60,
                amplifier,
                false,
                false,
                true
            )
        )
    }
}