package me.nickotato.shadowSMP.abilities.charms

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class HermesBootsAbility : Ability(60) {

    override fun execute(context: AbilityContext) {
        context.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 5 * 20, 4))
        val taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(
            ShadowSMP.instance,
            {
                if (!context.isValid) return@scheduleSyncRepeatingTask

                // Particle trail at player's location
                context.world.spawnParticle(
                    Particle.CLOUD,
                    context.location.add(0.0, 1.0, 0.0),
                    5, // number of particles
                    0.2, 0.2, 0.2,
                    0.0 // speed
                )
            },
            0L,
            2L
        )

        object : BukkitRunnable() {
            override fun run() {
                Bukkit.getScheduler().cancelTask(taskId)
            }
        }.runTaskLater(ShadowSMP.instance, 30 * 20L)
    }
}
