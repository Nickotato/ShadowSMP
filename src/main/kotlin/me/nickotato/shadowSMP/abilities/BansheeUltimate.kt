package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.sin

class BansheeUltimate: Ability(60) {
    override fun execute(player: Player) {
        val nearbyPlayers = player.world.getNearbyPlayers(player.location, 1.0)
        val blindness = PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 6)
        val slowness = PotionEffect(PotionEffectType.SLOWNESS, 20*5, 6)

        for (nearbyPlayer in nearbyPlayers) {
            if (nearbyPlayer == player) continue
            nearbyPlayer.addPotionEffect(blindness)
            nearbyPlayer.addPotionEffect(slowness)
        }

        playShadeParticles(player)
    }

    private fun playShadeParticles(player: Player) {
        val world = player.world
        val startLoc = player.location.clone().add(0.0, 1.0, 0.0)
        val direction = player.location.direction.normalize()

        object : BukkitRunnable() {
            var step = 0.0
            var radius = 3.0
            var heightWave = 0.0
            var rising = true

            override fun run() {
                if (step > 40) { // roughly equivalent to your Skript’s 40-block range
                    cancel()
                    return
                }

                // Move the origin forward along the player’s facing direction
                val currentLoc = startLoc.clone().add(direction.clone().multiply(step))

                // Vertical “wave” motion
                if (rising) heightWave += 0.3 else heightWave -= 0.3
                if (heightWave > 1) rising = false
                if (heightWave < 0) rising = true

                val center = currentLoc.clone().add(0.0, heightWave, 0.0)

                // Particle colors
                val grayToBlack = Particle.DustOptions(Color.fromRGB(186, 181, 181), 2f)
                val blackToRed = Particle.DustOptions(Color.fromRGB(71, 24, 24), 2f)

                // Central core
                world.spawnParticle(Particle.DUST, center, 12, 0.5, 0.5, 0.5, 0.2, grayToBlack)

                // Side waves (left & right arms)
                val yaw = player.location.yaw.toDouble()
                val rightVec = Vector(cos(toRadians(yaw + 90)), 0.0, sin(toRadians(yaw + 90)))
                val leftVec = Vector(cos(toRadians(yaw - 90)), 0.0, sin(toRadians(yaw - 90)))

                val rightLoc = center.clone().add(rightVec.multiply(2))
                val leftLoc = center.clone().add(leftVec.multiply(2))
                val rightLower = rightLoc.clone().subtract(0.0, 2.0, 0.0)
                val leftLower = leftLoc.clone().subtract(0.0, 2.0, 0.0)

                // Outer arms (red-black transition dusts + end rods)
                listOf(rightLoc, leftLoc, rightLower, leftLower).forEach { loc ->
                    world.spawnParticle(Particle.DUST, loc, 12, 0.5, 0.5, 0.5, 0.2, blackToRed)
                    world.spawnParticle(Particle.END_ROD, loc, 8, 0.3, 0.3, 0.3, 0.1)
                }

                // Circular motion (end rods orbiting)
                val angleStep = 20
                for (i in 0 until 18) {
                    val angle = toRadians(i * angleStep + step * 10)
                    val offset = Vector(
                        cos(angle) * radius,
                        0.0,
                        sin(angle) * radius
                    )
                    val circleLoc = center.clone().add(offset)
                    world.spawnParticle(Particle.END_ROD, circleLoc, 2, 0.1, 0.1, 0.1, 0.0)
                    world.spawnParticle(Particle.DUST, circleLoc, 3, 0.2, 0.2, 0.2, 0.0, blackToRed)
                }

                step += 1.0
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }
}