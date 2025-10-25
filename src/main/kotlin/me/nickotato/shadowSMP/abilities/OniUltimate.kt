package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class OniUltimate : Ability(120) {
    override fun execute(player: Player) {
        player.world.strikeLightningEffect(player.location)

        object : BukkitRunnable() {
            var ticksRun = 0
            val radius = 10.0
            val particleCount = 50
            val wallHeight = 6

            override fun run() {
                if (ticksRun >= 20 * 30) { // 30 seconds
                    cancel()
                    return
                }

                // Strike lightning on nearby entities
                val nearbyEntities = player.getNearbyEntities(radius, radius, radius)
                for (entity in nearbyEntities) {
                    if (entity == player) continue
                    if (entity.type == EntityType.ITEM) continue

                    entity.world.strikeLightning(entity.location)
                }

                // Vertical lightning-themed wall
                val world = player.world
                val center = player.location.clone().add(0.0, 0.5, 0.0)

                for (i in 0 until particleCount) {
                    val angle = 2 * Math.PI * i / particleCount
                    val x = cos(angle) * radius
                    val z = sin(angle) * radius

                    for (y in 0 until wallHeight) {
                        val pos = center.clone().add(x, y.toDouble(), z)

                        // Slight randomness for more natural effect
                        val offsetX = Random.nextDouble(-0.2, 0.2)
                        val offsetY = Random.nextDouble(-0.1, 0.1)
                        val offsetZ = Random.nextDouble(-0.2, 0.2)
                        val finalPos = pos.add(Vector(offsetX, offsetY, offsetZ))

                        // REDSTONE particle with yellow color for lightning look
                        val dustOptions = Particle.DustOptions(Color.YELLOW, 1.5f)

                        world.spawnParticle(
                            Particle.DUST,
                            finalPos,
                            20,
                            0.0, 0.0, 0.0,
                            0.0,
                            dustOptions
                        )
                    }
                }

                ticksRun += 10
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 10L)
    }
}
