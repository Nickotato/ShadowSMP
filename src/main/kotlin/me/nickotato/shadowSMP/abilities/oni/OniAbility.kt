package me.nickotato.shadowSMP.abilities.oni

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.Sound
import org.bukkit.scheduler.BukkitRunnable

class OniAbility: Ability(30) {

    private val yellowDust = DustOptions(Color.fromRGB(255, 230, 50), 1.5f)

    override fun execute(context: AbilityContext) {
        val world = context.world
        val startLocation = context.location.clone()
        val direction = startLocation.direction.normalize()
        val targetLocation = startLocation.clone().add(direction.multiply(10))

        world.spawnParticle(Particle.ELECTRIC_SPARK, startLocation, 50, 0.5, 0.5, 0.5, 0.1)
        world.spawnParticle(Particle.DUST, startLocation, 30, 0.4, 0.4, 0.4, 0.02, yellowDust)
        world.playSound(startLocation, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.2f, 1.0f)
        world.playSound(startLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.3f)

        context.caster.teleport(targetLocation)

        world.spawnParticle(Particle.ELECTRIC_SPARK, targetLocation, 70, 0.8, 0.8, 0.8, 0.15)
        world.spawnParticle(Particle.DUST, targetLocation, 40, 0.5, 0.5, 0.5, 0.02, yellowDust)
        world.playSound(targetLocation, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1.2f, 1.0f)
        world.playSound(targetLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.2f)

        val steps = 20
        val distance = startLocation.distance(targetLocation)
        val stepVector = direction.clone().multiply(distance / steps)

        object : BukkitRunnable() {
            var step = 0
            override fun run() {
                if (step > steps) {
                    cancel()
                    return
                }
                val point = startLocation.clone().add(stepVector.clone().multiply(step.toDouble()))

                world.spawnParticle(Particle.DUST, point, 6, 0.1, 0.1, 0.1, 0.0, yellowDust)
                world.spawnParticle(Particle.ELECTRIC_SPARK, point, 3, 0.05, 0.05, 0.05, 0.01)

                step++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }
}
