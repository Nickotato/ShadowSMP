package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.cos
import kotlin.math.sin

@Suppress("SameParameterValue")
class GolemAbility: Ability(60) {
    override fun execute(player: Player) {
        val radius = 10.0
        val nearbyPlayers = player.world.getNearbyPlayers(player.location, radius)
        val slowness = PotionEffect(PotionEffectType.SLOWNESS, 10 * 20, 4)

        for (nearbyPlayer in nearbyPlayers) {
            if (nearbyPlayer == player) continue
            nearbyPlayer.addPotionEffect(slowness)
        }

        playParticles(player, radius)
        playSound(player)
    }

    private fun playParticles(player: Player, radius: Double) {
        val world = player.world
        val center = player.location.clone().add(0.0, 1.0, 0.0)
        val particleCount = 100 // number of particles around the circle

        object : BukkitRunnable() {
            var t = 0
            override fun run() {
                if (t >= 40) { // lasts 2 seconds (40 ticks)
                    cancel()
                    return
                }

                for (i in 0 until particleCount) {
                    val angle = 2 * Math.PI * i / particleCount
                    val x = radius * cos(angle)
                    val z = radius * sin(angle)
                    val loc = center.clone().add(x, 0.0, z)

                    world.spawnParticle(Particle.CLOUD, loc, 0, 0.0, 0.0, 0.0, 0.0)
                }

                t++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }

    private fun playSound(player: Player) {
        player.world.playSound(player.location, Sound.BLOCK_ANVIL_BREAK, 1f, 1f)
    }
}