package me.nickotato.shadowSMP.abilities.god

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.cos
import kotlin.math.sin

@Suppress("SameParameterValue")
class GodUltimate: Ability(120) {

    override fun execute(player: Player) {
        val plugin = ShadowSMP.instance
        val warningRadius = 10.0
        val warningDuration = 60L // 3 seconds
        val beamRadius = 10.0
        val beamHeight = 200.0
        val damagePerTick = 20.0

        // Step 1: Circle warning around player
        object : BukkitRunnable() {
            var tickCount = 0
            override fun run() {
                if (tickCount > warningDuration) {
                    cancel()
                    startGodBeam(player, beamRadius, beamHeight, damagePerTick, plugin)
                    return
                }

                val center = player.location // Update warning circle to follow player
                val points = 48
                for (i in 0 until points) {
                    val angle = 2 * Math.PI / points * i
                    val x = warningRadius * cos(angle)
                    val z = warningRadius * sin(angle)
                    val loc = center.clone().add(x, 0.1, z)
                    center.world.spawnParticle(Particle.FLAME, loc, 2, 0.0, 0.0, 0.0, 0.0)
                    center.world.spawnParticle(Particle.END_ROD, loc, 2, 0.0, 0.0, 0.0, 0.0)
                }

                tickCount++
            }
        }.runTaskTimer(plugin, 0L, 1L)
    }

    private fun startGodBeam(
        player: Player,
        radius: Double,
        height: Double,
        damage: Double,
        plugin: ShadowSMP
    ) {
        // Epic sound to signal beam start
        player.world.playSound(player.location, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 6f, 1f)

        object : BukkitRunnable() {
            var ticks = 0
            val maxTicks = 60 // Duration of the beam

            override fun run() {
                if (ticks > maxTicks) {
                    cancel()
                    return
                }

                val center = player.location // Update the beam center every tick so it follows the player

                // Giant golden beam: multiple concentric circles for solid look
                val rings = 5
                val pointsPerRing = 40
                for (y in 0..height.toInt() step 2) {
                    for (r in 0 until rings) {
                        val currentRadius = radius * (r + 1) / rings
                        for (i in 0 until pointsPerRing) {
                            val angle = 2 * Math.PI / pointsPerRing * i
                            val x = currentRadius * cos(angle)
                            val z = currentRadius * sin(angle)
                            val loc = center.clone().add(x, y.toDouble(), z)
                            center.world.spawnParticle(Particle.HAPPY_VILLAGER, loc, 2, 0.0, 0.0, 0.0, 0.0)
                            center.world.spawnParticle(Particle.FLAME, loc, 2, 0.0, 0.0, 0.0, 0.0)
                            center.world.spawnParticle(Particle.END_ROD, loc, 3, 0.0, 0.0, 0.0, 0.0)
                        }
                    }
                }

                // Damage all living entities inside the beam
                val entities: List<Entity> = center.world.getNearbyEntities(center, radius, height, radius)
                    .filter { it != player }
                entities.forEach { entity ->
                    if (entity is LivingEntity) {
                        entity.damage(damage, player)
                    }
                }

                ticks++
            }
        }.runTaskTimer(plugin, 0L, 1L)
    }
}
