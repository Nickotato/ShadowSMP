package me.nickotato.shadowSMP.abilities.ignis

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import org.bukkit.Particle
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class IgnisUltimate : Ability(60) {

    override fun execute(player: Player) {
        val world = player.world
        val durationTicks = 100L
        val baseRadius = 3.0
        val height = 6
        val pullStrength = 0.3
        val damagePerTick = 2.0

        object : BukkitRunnable() {
            var ticks = 0

            override fun run() {
                ticks++
                val playerLoc = player.location

                for (y in 0 until height * 5) {
                    val progress = y.toDouble() / (height * 5)
                    val layerRadius = baseRadius * (1 - progress)
                    val angle = ticks * 0.3 + y * 0.5
                    val x = cos(angle) * layerRadius + Random.nextDouble(-0.1, 0.1)
                    val z = sin(angle) * layerRadius + Random.nextDouble(-0.1, 0.1)
                    val loc = playerLoc.clone().add(x, height - progress * height, z)
                    world.spawnParticle(Particle.FLAME, loc, 5, 0.05, 0.05, 0.05, 0.02)
                }

                val nearby = player.getNearbyEntities(baseRadius + 2, height.toDouble(), baseRadius + 2)
                for (entity in nearby) {
                    if (entity is LivingEntity && entity != player) {
                        val center = playerLoc.clone().add(0.0, height / 2.0, 0.0)
                        val direction = center.toVector().subtract(entity.location.toVector()).normalize().multiply(pullStrength)
                        entity.velocity = direction.add(Vector(0.0, 0.1, 0.0))

                        entity.fireTicks = 40
                        entity.damage(damagePerTick, player)
                    }
                }

                if (ticks >= durationTicks) cancel()
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 2L)
    }
}
