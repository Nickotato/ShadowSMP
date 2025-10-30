package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Item
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.cos
import kotlin.math.sin

@Suppress("SameParameterValue")
class JinnUltimate() : Ability(120) {

    override fun execute(player: Player) {
        val center = player.location.clone().add(0.0, 20.0, 0.0) // 12 blocks above player
        val radiusSound = 40.0

        player.world.getNearbyPlayers(center, radiusSound).forEach {
            it.playSound(center, Sound.ENTITY_WARDEN_EMERGE, 1f, 1f)
        }

        startFirstLoop(player, center, radiusSound)
    }

    private fun startFirstLoop(player: Player, center: Location, radiusSound: Double) {
        object : BukkitRunnable() {
            var loopCount = 0
            override fun run() {
                if (loopCount >= 5) {
                    this.cancel()
                    startSecondLoop(player, center, radiusSound)
                    return
                }

                val iteration = loopCount + 1

                player.world.getNearbyPlayers(center, radiusSound).forEach {
                    it.playSound(center, Sound.BLOCK_SCULK_SPREAD, 0.7f, 3f / iteration)
                    it.playSound(center, Sound.BLOCK_SCULK_PLACE, 2f, -3f + iteration)
                }

                center.world.spawnParticle(
                    Particle.DUST, center, 15, 0.2, 0.2, 0.2, 0.0,
                    Particle.DustOptions(Color.fromRGB(52, 152, 219), 2f)
                )

                center.world.spawnParticle(
                    Particle.SOUL, center, 10, 0.6, 0.6, 0.6, 0.1
                )

                loopCount++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 2L)
    }

    private fun startSecondLoop(player: Player, center: Location, radiusSound: Double) {
        object : BukkitRunnable() {
            var loopCount = 0
            override fun run() {
                if (loopCount >= 8) {
                    this.cancel()
                    startMainLoop(player, center, radiusSound)
                    return
                }

                val iteration = loopCount + 1

                player.world.getNearbyPlayers(center, radiusSound).forEach {
                    it.playSound(center, Sound.BLOCK_SCULK_SPREAD, 0.7f, 3f / iteration)
                    it.playSound(center, Sound.BLOCK_SCULK_PLACE, 2f, -3f + iteration)
                }

                val i2 = iteration.toDouble() / 4
                center.world.spawnParticle(
                    Particle.SOUL, center, 15, 0.6 - i2, 0.6 - i2, 0.6 - i2, 0.1
                )

                loopCount++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 2L)
    }

    private fun startMainLoop(player: Player, center: Location, radiusSound: Double) {
        object : BukkitRunnable() {
            var loopCount = 0
            override fun run() {
                if (loopCount >= 300) {
                    this.cancel()
                    return
                }

                val iteration = loopCount + 1

                player.world.getNearbyPlayers(center, radiusSound).forEach {
                    it.playSound(center, Sound.BLOCK_SCULK_SPREAD, 0.03f, 3f / iteration)
                    it.playSound(center, Sound.BLOCK_SCULK_PLACE, 0.05f, -3f + iteration)
                }

                center.world.getNearbyEntities(center, 45.0, 45.0, 45.0).forEach { entity ->
                    if (entity == player || entity is Item) return@forEach
                    if (entity !is LivingEntity) return@forEach

                    val direction = center.toVector().subtract(entity.location.toVector())
                    val distance = entity.location.distance(center)

                    if (distance > 3) {
                        entity.velocity = direction.normalize().multiply(0.1)
                    } else {
                        entity.velocity = direction.normalize().multiply(1.5)
                        entity.damage(0.14, player)
                    }
                }

                val radius = 3.5
                val yaw = Math.toRadians((iteration * 10).toDouble())
                val pitch = Math.toRadians((iteration * 10).toDouble())
                val x = radius * cos(yaw) * cos(pitch)
                val y = radius * sin(pitch)
                val z = radius * sin(yaw) * cos(pitch)

                center.world.spawnParticle(
                    Particle.END_ROD,
                    center.clone().add(x, y, z),
                    15,
                    0.3,
                    0.4,
                    0.3,
                    0.1
                )

                val sphereRadius = 4.0
                val density = 15

                for (i in 0..density) {
                    val theta = Math.PI * i / density
                    for (j in 0..density) {
                        val phi = 2 * Math.PI * j / density

                        val sx = sphereRadius * sin(theta) * cos(phi)
                        val sy = sphereRadius * cos(theta)
                        val sz = sphereRadius * sin(theta) * sin(phi)

                        center.world.spawnParticle(
                            Particle.DUST,
                            center.clone().add(sx, sy, sz),
                            1,
                            Particle.DustOptions(Color.fromRGB(10, 20, 150), 2f) // dark blue
                        )
                    }
                }

                loopCount++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }

}
