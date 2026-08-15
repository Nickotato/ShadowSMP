package me.nickotato.shadowSMP.abilities.reaper

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import me.nickotato.shadowSMP.utils.EntityUtils
import net.kyori.adventure.text.Component
import org.bukkit.Color
import org.bukkit.GameMode
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

class ReaperAbility : Ability(90) {
    override fun execute(context: AbilityContext) {
        val nearbyEntities = context.nearbyEntities(10.0, 10.0, 10.0)

        var nearest: LivingEntity? = null
        var closest = Double.MAX_VALUE

        for (entity in nearbyEntities) {
            if (entity == context.caster) continue
            if (entity !is LivingEntity) continue
            if (EntityUtils.isImmune(entity.type)) continue
            if (entity is Player && entity.gameMode == GameMode.SPECTATOR) continue

            val dist = context.location.distanceSquared(entity.location)
            if (dist < closest) {
                closest = dist
                nearest = entity
            }
        }

        if (nearest == null) {
            context.sendMessage(Component.text("§cNo entities in range"))
            return
        }

        val maxHp = nearest.getAttribute(Attribute.MAX_HEALTH)?.value ?: return
        if (maxHp > 150) {
            context.sendMessage(Component.text("§7That entity is too powerful to reap."))
            return
        }

        val world = context.world
        world.spawnParticle(Particle.LARGE_SMOKE, context.location, 20, 0.3, 0.3, 0.3, 0.01)
        world.spawnParticle(
            Particle.DUST, context.location, 20, 0.3, 0.3, 0.3, 0.01,
            Particle.DustOptions(Color.fromRGB(80, 0, 0), 1.5f)
        )
        world.playSound(context.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.8f)

        val targetLoc = nearest.location
        val behind = targetLoc.clone().add(targetLoc.direction.multiply(-1))
        behind.y = targetLoc.y
        context.caster.teleport(behind)

        world.spawnParticle(Particle.LARGE_SMOKE, context.location, 30, 0.4, 0.4, 0.4, 0.01)
        world.spawnParticle(
            Particle.DUST, context.location, 30, 0.4, 0.4, 0.4, 0.01,
            Particle.DustOptions(Color.fromRGB(150, 0, 0), 2f)
        )
        world.playSound(context.location, Sound.ENTITY_WITHER_SPAWN, 0.6f, 1.5f)

        val currentHealth = nearest.health
        val damage = maxHp * 0.25
        nearest.health = maxOf(0.0, currentHealth - damage)
    }
}
