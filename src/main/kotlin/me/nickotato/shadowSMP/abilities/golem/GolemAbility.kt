package me.nickotato.shadowSMP.abilities.golem

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.cos
import kotlin.math.sin

@Suppress("SameParameterValue")
class GolemAbility: Ability(60) {


    override fun execute(player: Player) {
        beginTask(player)
        applyResistance(player)
        playSound(player)
    }

    private fun beginTask(player: Player) {
        val radius = 10.0
        val nearbyEntities = player.world
            .getNearbyEntities(player.location, radius, radius, radius)
            .filterIsInstance<LivingEntity>()

        object : BukkitRunnable() {
            var t = 0
            override fun run() {
                if (t>= 40) {
                    cancel()
                    return
                }

                for (entity in nearbyEntities) {
                    if (entity == player) continue
                    applySlowness(entity)
                    pullEntityTowardPlayer(player, entity)
                }

                playParticles(player, radius)

                t++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }

    private fun playParticles(player: Player, radius: Double) {
        val world = player.world
        val center = player.location.clone().add(0.0, 1.0, 0.0)
        val particleCount = 100

        for (i in 0 until particleCount) {
            val angle = 2 * Math.PI * i / particleCount
            val x = radius * cos(angle)
            val z = radius * sin(angle)
            val loc = center.clone().add(x, 0.0, z)

            world.spawnParticle(Particle.CLOUD, loc, 0, 0.0, 0.0, 0.0, 0.0)
        }
    }

    private fun playSound(player: Player) {
        player.world.playSound(player.location, Sound.BLOCK_ANVIL_BREAK, 1f, 1f)
    }

    private fun applyResistance(player: Player) {
        val resistance = PotionEffect(PotionEffectType.RESISTANCE, 10, 1)
        player.addPotionEffect(resistance)
    }

    private fun applySlowness(entity: LivingEntity) {
        val slowness = PotionEffect(PotionEffectType.SLOWNESS, 10 * 20, 7)
        entity.addPotionEffect(slowness)
    }

    private fun pullEntityTowardPlayer(player: Player, entity: LivingEntity) {
        val direction = player.location.toVector()
            .subtract(entity.location.toVector())
            .normalize()

        val distance = player.location.distance(entity.location)
        val strength = (0.25 - (distance / 40.0)).coerceIn(0.02, 0.12)

        entity.velocity = entity.velocity.add(direction.multiply(strength))
    }
}