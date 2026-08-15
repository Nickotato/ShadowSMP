package me.nickotato.shadowSMP.abilities.revenant

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin

class RevenantUltimate : Ability(240) {

    override fun execute(context: AbilityContext) {
        val plugin = ShadowSMP.instance
        val loc = context.location.clone()

        object : BukkitRunnable() {
            var tick = 0
            override fun run() {
                tick++
                val radius = 0.5 + tick * 0.05
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

                if (tick % 10 == 0) {
                    context.world.playSound(context.location, "entity.evoker.prepare_attack", 1f, 1f)
                }

                if (tick >= 20) {
                    cancel()
                    launchPlayer(context)
                }
            }
        }.runTaskTimer(plugin, 0L, 1L)
    }

    private fun launchPlayer(context: AbilityContext) {
        val plugin = ShadowSMP.instance
        val direction = context.location.direction
        context.caster.velocity = Vector(direction.x, 2.5, direction.z).multiply(2)
        object: BukkitRunnable() {
            override fun run() {

                context.caster.isGliding = true

            }
        }.runTaskLater(ShadowSMP.instance, 20 * 2L)

        object : BukkitRunnable() {
            var tick = 0

            override fun run() {
                if (!context.isValid || tick >= 70) {
                    cancel()
                    return
                }

                val loc = context.location.clone()
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
