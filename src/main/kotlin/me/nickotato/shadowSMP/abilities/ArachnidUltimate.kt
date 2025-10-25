package me.nickotato.shadowSMP.abilities

import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class ArachnidUltimate: Ability(60) {
    override fun execute(player: Player) {
        val radius = 10.0
        val nearbyEntities = player.getNearbyEntities(radius, radius, radius)

        // Spawn a circular particle effect around the player to show AoE
        val center = player.location.add(0.0, 1.0, 0.0)
        val points = 100 // number of particles for the circle

        for (i in 0 until points) {
            val angle = 2 * PI * i / points
            val x = radius * cos(angle)
            val z = radius * sin(angle)
            val particleLocation = center.clone().add(x, 0.0, z)
            player.world.spawnParticle(
                Particle.DUST,
                particleLocation,
                1,
                0.0, 0.0, 0.0,
                Particle.DustOptions(Color.PURPLE, 1.0f)
            )
        }

        for (entity in nearbyEntities) {
            if (entity == player) continue

            val footBlock = entity.location.block
            val headBlock = footBlock.getRelative(0, 1, 0)

            // Trap the entity in a cobweb if possible
            if (headBlock.type == Material.AIR) {
                headBlock.type = Material.COBWEB
            }

            // Spawn particles around the entity for feedback
            val entityCenter = entity.location.add(0.0, 1.0, 0.0)
            for (i in 0 until 20) {
                val offsetX = Math.random() - 0.5      // random double between -0.5 and 0.5
                val offsetY = Math.random() * 1.5      // random double between 0.0 and 1.5
                val offsetZ = Math.random() - 0.5      // random double between -0.5 and 0.5
                val particleLoc = entityCenter.clone().add(offsetX, offsetY, offsetZ)
                entity.world.spawnParticle(
                    Particle.CRIMSON_SPORE,
                    particleLoc,
                    1,
                    0.0, 0.0, 0.0,
                    0.05
                )
            }

            // Play sound effect for trapped entities
            entity.world.playSound(
                entity.location,
                Sound.BLOCK_COBWEB_PLACE,
                2.0f, // louder
                1.2f // slightly higher pitch
            )
        }

        // Optional: global sound to indicate ultimate activation
        player.world.playSound(
            player.location,
            Sound.ENTITY_SPIDER_AMBIENT,
            1.5f,
            1.0f
        )
    }
}
