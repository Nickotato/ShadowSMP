package me.nickotato.shadowSMP.abilities.charms

import me.nickotato.shadowSMP.abilities.Ability
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.math.cos
import kotlin.math.sin

class FrostGaleAbility: Ability(45) {
    override fun execute(player: Player) {
        val world = player.world
        val start = player.location.clone().add(0.0, 1.2, 0.0)
        val direction = player.location.direction.normalize()
        val speed = 1.2
        val range = 15
        val stepCount = (range / speed).toInt()

        val slowness = PotionEffect(PotionEffectType.SLOWNESS, 20 * 5, 2)

        val radius = 2.0 // radius of the frost circle
        val points = 20   // how many points around the circle

        for (i in 0..stepCount) {
            val center = start.clone().add(direction.clone().multiply(i * speed))

            for (p in 0 until points) {
                val angle = (2 * Math.PI / points) * p
                val x = cos(angle) * radius
                val z = sin(angle) * radius
                val loc = center.clone().add(x, 0.0, z)

                // Spawn icy particles for the circle
                val iceDust = Particle.DustOptions(Color.AQUA, 1.5f)
                world.spawnParticle(Particle.DUST, loc, 1, 0.05, 0.05, 0.05, 0.0, iceDust)
                world.spawnParticle(Particle.ITEM_SNOWBALL, loc, 1, 0.05, 0.05, 0.05, 0.05)

                // Play subtle wind sound occasionally
                if (i % 3 == 0 && p % 5 == 0) {
                    world.playSound(loc, Sound.ENTITY_SNOW_GOLEM_HURT, 0.2f, 1.0f)
                }

                // Apply slowness to nearby players
                val hitPlayers = world.getNearbyPlayers(loc, 1.0)
                for (target in hitPlayers) {
                    if (target != player) {
                        target.addPotionEffect(slowness)
                        world.playSound(target.location, Sound.BLOCK_GLASS_BREAK, 1.0f, 1.2f)
                        world.spawnParticle(Particle.SNOWFLAKE, target.location.add(0.0,1.0,0.0), 8, 0.3, 0.3, 0.3, 0.05)
                    }
                }
            }
        }

        // Final gust sound at the end
        world.playSound(start.add(direction.multiply(range)), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 0.7f)
    }
}
