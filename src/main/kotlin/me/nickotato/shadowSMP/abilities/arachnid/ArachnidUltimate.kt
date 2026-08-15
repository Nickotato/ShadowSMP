package me.nickotato.shadowSMP.abilities.arachnid

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import me.nickotato.shadowSMP.utils.EntityUtils
import org.bukkit.Color
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class ArachnidUltimate: Ability(60) {
    override fun execute(context: AbilityContext) {
        val caster = context.caster
        val radius = 10.0
        val nearbyEntities = context.nearbyEntities(radius, radius, radius)

        makeCircleParticles(caster, radius)

        for (entity in nearbyEntities) {
            if (!isValidEntity(entity, caster)) continue

            spawnCobweb(entity)

            playCaughtParticles(entity)

            playCaughtSound(entity)
        }

        playMainSound(context)
    }

    private fun makeCircleParticles(caster: LivingEntity, radius: Double) {
        val center = caster.location.clone().add(0.0, 1.0, 0.0)
        val points = 100

        for (i in 0 until points) {
            val angle = 2 * PI * i / points
            val x = radius * cos(angle)
            val z = radius * sin(angle)
            val particleLocation = center.clone().add(x, 0.0, z)
            caster.world.spawnParticle(
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

    private fun playMainSound(context: AbilityContext) {
        context.playSound(Sound.ENTITY_SPIDER_AMBIENT, 1.5f, 1.0f)
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

    private fun isValidEntity(entity: Entity, caster: LivingEntity): Boolean {
        if (entity == caster) return false
        if (entity is Player && entity.gameMode == GameMode.SPECTATOR) return false
        if (EntityUtils.isImmune(entity.type)) return false
        return true
    }
}
