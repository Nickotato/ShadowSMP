package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class OniAbility() : Ability(30) {

    private val yellowDust = DustOptions(Color.fromRGB(255, 230, 50), 1.5f)

    override fun execute(player: Player) {
        val world = player.world
        val startLocation = player.location.clone()
        val direction = startLocation.direction.normalize()
        val targetLocation = startLocation.clone().add(direction.multiply(10))

        // Start particles + sound
        world.spawnParticle(Particle.ELECTRIC_SPARK, startLocation, 50, 0.5, 0.5, 0.5, 0.1)
        world.spawnParticle(Particle.DUST, startLocation, 30, 0.4, 0.4, 0.4, 0.02, yellowDust)
        world.playSound(startLocation, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.2f, 1.0f)
        world.playSound(startLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.3f)

        // Teleport player
        player.teleport(targetLocation)

        // End particles + sound
        world.spawnParticle(Particle.ELECTRIC_SPARK, targetLocation, 70, 0.8, 0.8, 0.8, 0.15)
        world.spawnParticle(Particle.DUST, targetLocation, 40, 0.5, 0.5, 0.5, 0.02, yellowDust)
        world.playSound(targetLocation, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1.2f, 1.0f)
        world.playSound(targetLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.2f)

        // Animated lightning/yellow trail from start -> target
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

                // Add lightning/yellow trail particles
                world.spawnParticle(Particle.DUST, point, 6, 0.1, 0.1, 0.1, 0.0, yellowDust)
                world.spawnParticle(Particle.ELECTRIC_SPARK, point, 3, 0.05, 0.05, 0.05, 0.01)

                step++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }
}
