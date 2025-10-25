package me.nickotato.shadowSMP.abilities.charms

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class HermesBootsAbility : Ability(60) {

    override fun execute(player: Player) {
        // Give Speed 5 for 30 seconds (20 ticks = 1 second)
        player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 30 * 20, 4)) // amplifier is 0-indexed, so 4 = Speed V

        // Schedule a repeating task to create a particle trail
        val taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(
            ShadowSMP.instance,  // Replace with your plugin instance
            {
                if (!player.isOnline) return@scheduleSyncRepeatingTask

                // Particle trail at player's location
                player.world.spawnParticle(
                    Particle.CLOUD,
                    player.location.add(0.0, 1.0, 0.0), // slightly above ground
                    5, // number of particles
                    0.2, 0.2, 0.2, // offset for randomness
                    0.0 // speed
                )
            },
            0L, // delay before first run
            2L  // repeat every 2 ticks (~0.1 seconds)
        )

        object : BukkitRunnable() {
            override fun run() {
                Bukkit.getScheduler().cancelTask(taskId)
            }
        }.runTaskLater(ShadowSMP.instance, 30 * 20L)
    }
}
