package me.nickotato.shadowSMP.abilities.deogen

import me.nickotato.shadowSMP.abilities.Ability
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import me.nickotato.shadowSMP.ShadowSMP

class DeogenUltimate: Ability(90) {

    private val hitCount = 10
    private val hitInterval = 5L
    private val damagePerHit = 2.0

    override fun execute(player: Player) {
        val target = getNearestEntity(player)
        if (target == null) {
            player.sendMessage("§cNo target nearby!")
            return
        }

        player.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, (hitCount * hitInterval + 20).toInt(), 1, false, false, true))
        if (target is LivingEntity) {
            target.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, (hitCount * hitInterval + 20).toInt(), 1, false, false, true))
        }

        object : BukkitRunnable() {
            var hits = 0

            override fun run() {
                if (hits >= hitCount || !target.isValid || !player.isValid) {
                    this.cancel()
                    return
                }

                player.teleport(player.location.clone().apply { direction = target.location.toVector().subtract(player.location.toVector()) })
                if (target is LivingEntity) {
                    target.teleport(target.location.clone().apply { direction = player.location.toVector().subtract(target.location.toVector()) })
                }

                val offsetX = (Math.random() - 0.5) * 1.5
                val offsetZ = (Math.random() - 0.5) * 1.5
                val targetLocation: Location = target.location.clone().add(offsetX, 0.0, offsetZ)
                player.teleport(targetLocation)

                if (target is LivingEntity) {
                    target.damage(damagePerHit, player)
                    target.velocity = target.velocity.setY(0.5)
                }

                player.world.playSound(player.location, Sound.ENTITY_PLAYER_ATTACK_STRONG, 1.0f, 1.0f)
                target.world.playSound(target.location, Sound.ENTITY_PLAYER_HURT, 1.0f, 1.0f)

                hits++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, hitInterval)
    }

    private fun getNearestEntity(player: Player): Entity? {
        val nearbyEntities = player.getNearbyEntities(10.0, 5.0, 10.0)
        return nearbyEntities.filterIsInstance<LivingEntity>()
            .minByOrNull { it.location.distanceSquared(player.location) }
    }
}
