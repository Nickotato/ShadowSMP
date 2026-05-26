package me.nickotato.shadowSMP.abilities.arachnid

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.utils.EntityUtils
import org.bukkit.Color
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class ArachnidUltimate: Ability(60) {
    override fun execute(player: Player) {
        val radius = 10.0
        val nearbyEntities = player.getNearbyEntities(radius, radius, radius)

        makeCircleParticles(player, radius)

        for (entity in nearbyEntities) {
            if (!isValidEntity(entity, player)) continue

            spawnCobweb(entity)

            playCaughtParticles(entity)

            playCaughtSound(entity)
        }

        playMainSound(player)
    }

    private fun makeCircleParticles(player: Player, radius: Double) {
        val center = player.location.clone().add(0.0, 1.0, 0.0)
        val points = 100

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
    }

    private fun spawnCobweb(entity: Entity) {
        val footBlock = entity.location.block
        val headBlock = footBlock.getRelative(0, 1, 0)

        if (headBlock.type == Material.AIR) {
            headBlock.type = Material.COBWEB
        }
    }

    private fun playMainSound(player: Player) {
        player.world.playSound(
            player.location,
            Sound.ENTITY_SPIDER_AMBIENT,
            1.5f,
            1.0f
        )
    }

    private fun playCaughtParticles(entity: Entity) {
        val entityCenter = entity.location.clone().add(0.0, 1.0, 0.0)
        for (i in 0 until 20) {
            val offsetX = Math.random() - 0.5
            val offsetY = Math.random() * 1.5
            val offsetZ = Math.random() - 0.5
            val particleLoc = entityCenter.clone().add(offsetX, offsetY, offsetZ)
            entity.world.spawnParticle(
                Particle.CRIMSON_SPORE,
                particleLoc,
                1,
                0.0, 0.0, 0.0,
                0.05
            )
        }
    }
    private fun playCaughtSound(entity: Entity) {
        entity.world.playSound(
            entity.location,
            Sound.BLOCK_COBWEB_PLACE,
            2.0f,
            1.2f
        )
    }

    private fun isValidEntity(entity: Entity, player: Player): Boolean {
        if (entity == player) return false
        if (entity is Player && entity.gameMode == GameMode.SPECTATOR) return false
        if (EntityUtils.isImmune(entity.type)) return false
        return true
    }
}
