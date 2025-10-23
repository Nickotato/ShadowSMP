package me.nickotato.shadowSMP.abilities

import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class BansheeUltimate: Ability(60) {
    override fun execute(player: Player) {
        val nearbyPlayers = player.world.getNearbyPlayers(player.location, 1.0)
        val blindness = PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 6)
        val slowness = PotionEffect(PotionEffectType.SLOWNESS, 20*5, 6)

        for (nearbyPlayer in nearbyPlayers) {
            if (nearbyPlayer == player) continue
            nearbyPlayer.addPotionEffect(blindness)
            nearbyPlayer.addPotionEffect(slowness)
        }

        playSoundEffects(player)
        playShadeParticles(player)
    }

    private fun playShadeParticles(player: Player) {
        val world = player.world
        val center = player.location.clone().add(0.0, 1.0, 0.0)

        val radius = 3.0
        val density = 250 // how many particles (higher = thicker cloud)
        val blackDust = Particle.DustOptions(Color.fromRGB(71, 24, 24), 2f)
        val grayDust = Particle.DustOptions(Color.fromRGB(186, 181, 181), 2f)

        repeat(density) {
            // pick a random point inside a circle
            val angle = Random.nextDouble(0.0, 2 * Math.PI)
            val r = Random.nextDouble(0.0, radius)
            val x = cos(angle) * r
            val z = sin(angle) * r
            val yOffset = Random.nextDouble(-0.4, 0.4)

            val loc = center.clone().add(x, yOffset, z)

            // mix of dark particles and smoke for atmosphere
            world.spawnParticle(Particle.DUST, loc, 1, 0.05, 0.05, 0.05, 0.0, blackDust)
            if (Random.nextDouble() < 0.5) {
                world.spawnParticle(Particle.DUST, loc, 1, 0.05, 0.05, 0.05, 0.0, grayDust)
            }
            if (Random.nextDouble() < 0.15) {
                world.spawnParticle(Particle.SMOKE, loc, 1, 0.1, 0.1, 0.1, 0.01)
            }
        }
    }

    private fun playSoundEffects(player: Player) {
        val world = player.world
        val loc = player.location

        // 👻 Low haunting ambient wind
        world.playSound(loc, Sound.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 1.0f, 0.6f)

        // 💨 Whoosh burst when the ability activates
        world.playSound(loc, Sound.ENTITY_PHANTOM_FLAP, 1.5f, 0.5f)

        // 😱 The banshee “scream” effect
        world.playSound(loc, Sound.ENTITY_GHAST_SCREAM, 1.0f, 1.2f)

        // 💀 Optional subtle after-echo / reverb style
        world.playSound(loc, Sound.BLOCK_BEACON_DEACTIVATE, 0.7f, 0.8f)

        // For nearby players: a slightly quieter echo to increase immersion
        for (nearby in world.getNearbyPlayers(loc, 10.0)) {
            if (nearby != player) {
                nearby.playSound(loc, Sound.ENTITY_PHANTOM_AMBIENT, 0.7f, 0.6f)
            }
        }
    }

}