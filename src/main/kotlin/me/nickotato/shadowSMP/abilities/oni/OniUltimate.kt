package me.nickotato.shadowSMP.abilities.oni

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import me.nickotato.shadowSMP.utils.EntityUtils
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.entity.EntityType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class OniUltimate : Ability(120) {
    override fun execute(context: AbilityContext) {
        context.world.strikeLightningEffect(context.location)

        object : BukkitRunnable() {
            var ticksRun = 0
            val radius = 10.0
            val particleCount = 50
            val wallHeight = 6

            override fun run() {
                if (ticksRun >= 20 * 15) {
                    cancel()
                    return
                }

                val nearbyEntities = context.nearbyEntities(radius, radius, radius)
                for (entity in nearbyEntities) {
                    if (entity == context.caster) continue
                    if (entity.type == EntityType.ITEM) continue
                    if (EntityUtils.isImmune(entity.type)) continue

                    entity.world.strikeLightning(entity.location)
                }

                val world = context.world
                val center = context.location.clone().add(0.0, 0.5, 0.0)

                for (i in 0 until particleCount) {
                    val angle = 2 * Math.PI * i / particleCount
                    val x = cos(angle) * radius
                    val z = sin(angle) * radius

                    for (y in 0 until wallHeight) {
                        val pos = center.clone().add(x, y.toDouble(), z)

                        val offsetX = Random.nextDouble(-0.2, 0.2)
                        val offsetY = Random.nextDouble(-0.1, 0.1)
                        val offsetZ = Random.nextDouble(-0.2, 0.2)
                        val finalPos = pos.add(Vector(offsetX, offsetY, offsetZ))

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
