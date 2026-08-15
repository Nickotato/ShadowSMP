package me.nickotato.shadowSMP.abilities.spirit

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import me.nickotato.shadowSMP.manager.AbilityManager
import org.bukkit.Particle
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.cos
import kotlin.math.sin

class SpiritAbility: Ability(120) {
    override fun execute(context: AbilityContext) {
        AbilityManager.invulnerablePlayers.add(context.caster.uniqueId)

        object : BukkitRunnable(){
            override fun run() {
                AbilityManager.invulnerablePlayers.remove(context.caster.uniqueId)
            }
        }.runTaskLater(ShadowSMP.instance, 15 * 20)

        object : BukkitRunnable() {
            var timesRun = 0
            var rotation = 0.0
            override fun run() {
                if (!AbilityManager.invulnerablePlayers.contains(context.caster.uniqueId)) {
                    cancel()
                    return
                }

                val playerLoc = context.location.clone()

                val angle = 20 * timesRun
                val radians = Math.toRadians(angle.toDouble())
                val radius = 1
                val x = cos(radians) * radius
                val z = sin(radians) * radius
                val y = timesRun * 0.07

                val location1 = playerLoc.clone().add(x, y, z)
                val location2 = playerLoc.clone().add(-x, y, -z)

                context.world.spawnParticle(Particle.SCRAPE, location1, 10, 0.0, 0.0, 0.0)
                context.world.spawnParticle(Particle.SCRAPE, location2, 10, 0.0, 0.0, 0.0)

                val pillarCount = 2
                val pillarRadius = 1.5
                val pillarHeight = 3

                for (i in 0 until pillarCount) {
                    val angleDeg = rotation + i * (360.0 / pillarCount)
                    val rad = Math.toRadians(angleDeg)
                    val px = cos(rad) * pillarRadius
                    val pz = sin(rad) * pillarRadius

                    for (py in 0..pillarHeight) {
                        context.world.spawnParticle(Particle.SCRAPE,
                            playerLoc.clone().add(px, py.toDouble(), pz),
                            1, 0.0, 0.0, 0.0)
                    }
                }

                timesRun++
                rotation += 10 // adjust rotation speed
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }
}