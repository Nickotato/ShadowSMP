package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin

class RevenantUltimate : Ability(60) {

    override fun execute(player: Player) {
        val plugin = ShadowSMP.instance
        val loc = player.location.clone()

        // WIND-UP EFFECT BEFORE JUMP
        object : BukkitRunnable() {
            var tick = 0
            override fun run() {
                tick++
                val radius = 0.5 + tick * 0.05 // slowly expanding spiral
                val angle = tick * 20.0
                val x = cos(Math.toRadians(angle)) * radius
                val z = sin(Math.toRadians(angle)) * radius
                val y = 0.5 + sin(Math.toRadians((tick * 15).toDouble())) * 0.5

                loc.world?.spawnParticle(
                    Particle.DUST,
                    loc.clone().add(x, y, z),
                    50,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    Particle.DustOptions(Color.PURPLE, 1f)
                )

                // Play wind-up sound occasionally (optional)
                if (tick % 10 == 0) {
                    player.world.playSound(player.location, "entity.evoker.prepare_attack", 1f, 1f)
                }

                if (tick >= 20) { // after ~1 second (20 ticks), launch
                    cancel()
                    launchPlayer(player)
                }
            }
        }.runTaskTimer(plugin, 0L, 1L)
    }

    private fun launchPlayer(player: Player) {
        val plugin = ShadowSMP.instance
        // Launch player with a strong upward and forward velocity
        val direction = player.location.direction
        player.velocity = Vector(direction.x, 2.5, direction.z).multiply(2)

        // TRAIL EFFECT DURING JUMP
        object : BukkitRunnable() {
            var tick = 0

            override fun run() {
                if (!player.isValid || tick >= 70) {
                    cancel()
                    return
                }

                val loc = player.location.clone()
                // Particle trail behind player
                for (i in 0..2) {
                    val offsetX = (Math.random() - 0.5) * 0.5
                    val offsetY = (Math.random() - 0.5) * 0.5
                    val offsetZ = (Math.random() - 0.5) * 0.5

                    loc.world?.spawnParticle(
                        Particle.SOUL,
                        loc.clone().add(offsetX, offsetY, offsetZ),
                        1,
                        0.0, 0.0, 0.0,
                        0.0
                    )
                }
                tick++
            }
        }.runTaskTimer(plugin, 0L, 1L)
    }
}
