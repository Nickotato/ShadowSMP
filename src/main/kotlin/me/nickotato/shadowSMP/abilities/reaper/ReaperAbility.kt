package me.nickotato.shadowSMP.abilities.reaper

import me.nickotato.shadowSMP.abilities.Ability
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

class ReaperAbility : Ability(90) {
    override fun execute(player: Player) {
        val nearbyEntities = player.getNearbyEntities(10.0, 10.0, 10.0)

        var nearest: LivingEntity? = null
        var closest = Double.MAX_VALUE

        for (entity in nearbyEntities) {
            if (entity == player) continue
            if (entity !is LivingEntity) continue

            val dist = player.location.distanceSquared(entity.location)
            if (dist < closest) {
                closest = dist
                nearest = entity
            }
        }

        if (nearest == null) {
            player.sendMessage("§cNo entities in range")
            return
        }

        val maxHp = nearest.getAttribute(Attribute.MAX_HEALTH)?.value ?: return
        if (maxHp > 150) {
            player.sendMessage("§7That entity is too powerful to reap.")
            return
        }

        val world = player.world
        world.spawnParticle(Particle.LARGE_SMOKE, player.location, 20, 0.3, 0.3, 0.3, 0.01)
        world.spawnParticle(
            Particle.DUST, player.location, 20, 0.3, 0.3, 0.3, 0.01,
            Particle.DustOptions(Color.fromRGB(80, 0, 0), 1.5f)
        )
        world.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.8f)

        val targetLoc = nearest.location
        val behind = targetLoc.clone().add(targetLoc.direction.multiply(-1))
        behind.y = targetLoc.y
        player.teleport(behind)

        world.spawnParticle(Particle.LARGE_SMOKE, player.location, 30, 0.4, 0.4, 0.4, 0.01)
        world.spawnParticle(
            Particle.DUST, player.location, 30, 0.4, 0.4, 0.4, 0.01,
            Particle.DustOptions(Color.fromRGB(150, 0, 0), 2f)
        )
        world.playSound(player.location, Sound.ENTITY_WITHER_SPAWN, 0.6f, 1.5f)

        val currentHealth = nearest.health
        val damage = maxHp * 0.25
        nearest.health = maxOf(0.0, currentHealth - damage)
    }
}
