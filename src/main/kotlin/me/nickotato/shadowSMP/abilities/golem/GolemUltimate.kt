package me.nickotato.shadowSMP.abilities.golem

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.cos
import kotlin.math.sin

class GolemUltimate: Ability(60) {
    override fun execute(context: AbilityContext) {
        val plLoc = context.location

        object : BukkitRunnable() {
            var loop1 = 0
            override fun run() {
                if (loop1 >= 15) {
                    cancel()
                    shootOut(plLoc, context)
                    return
                }
                val r = (15 - loop1) * 0.5
                for (i in 0 until 36) {
                    val angle = Math.toRadians(i * 10.0)
                    val x = r * cos(angle)
                    val z = r * sin(angle)
                    val particleLoc = plLoc.clone().add(x, 0.0, z)
                    plLoc.world?.spawnParticle(
                        Particle.DUST,
                        particleLoc,
                        10,
                        0.3,
                        0.3,
                        0.3,
                        0.0,
                        Particle.DustOptions(Color.fromRGB(100,100,100), 1f)
                    )
                    plLoc.world?.spawnParticle(
                        Particle.END_ROD,
                        particleLoc,
                        5,
                        0.3,
                        0.3,
                        0.3,
                        0.05
                    )
                }
                plLoc.world?.playSound(plLoc, Sound.ENTITY_IRON_GOLEM_ATTACK, 1f, 1f)
                loop1++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L) // 1 tick interval

    }

    private fun shootOut(loc: Location, context: AbilityContext) {
        object : BukkitRunnable() {
            var loop2 = 0
            override fun run() {
                if (loop2 >= 8) {
                    cancel()
                    blastEntities(loc, context)
                    return
                }
                val r = loop2 * 1.0
                for (i in 0 until 36) {
                    val angle = Math.toRadians(i * 10.0)
                    val x = r * cos(angle)
                    val z = r * sin(angle)
                    val particleLoc = loc.clone().add(x, 0.0, z)
                    loc.world?.spawnParticle(
                        Particle.DUST,
                        particleLoc,
                        10,
                        0.5,
                        0.5,
                        0.5,
                        0.0,
                        Particle.DustOptions(Color.fromRGB(255,150,100), 1f)
                    )
                    loc.world?.spawnParticle(
                        Particle.END_ROD,
                        particleLoc,
                        5,
                        0.3,
                        0.3,
                        0.3,
                        0.1
                    )
                }
                loop2++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }

    private fun blastEntities(loc: Location, context: AbilityContext) {
        val nearby = loc.world?.getNearbyEntities(loc, 8.0, 8.0, 8.0)
        nearby?.forEach { entity ->
            if (entity != context.caster) {
                val direction = entity.location.toVector().subtract(loc.toVector()).normalize()
                entity.velocity = direction.multiply(10)
            }
        }
        loc.world?.playSound(loc, Sound.ENTITY_IRON_GOLEM_ATTACK, 2f, 0.8f)
    }
}