package me.nickotato.shadowSMP.abilities

import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.util.Vector

class JinnAbility(): Ability(90) {

    override fun execute(player: Player) {
        val entities = player.getNearbyEntities(10.0, 10.0, 10.0).filter {
            it != player && it !is org.bukkit.entity.Item && it is LivingEntity
        }.map { it as LivingEntity }

        if (entities.isEmpty()) return

        val damage = 2.0 * entities.size //  2 damage per entity affected

        for (entity in entities) {
            val direction = entity.location.toVector().subtract(player.location.toVector()).normalize()
            val distance = player.location.distance(entity.location)

            for (i in 0..distance.toInt()) {
                val point = player.location.add(direction.clone().multiply(i.toDouble()))
                player.world.spawnParticle(Particle.DUST, point, 1, 0.0, 0.0, 0.0, 0.0, Particle.DustOptions(Color.AQUA, 1f))
            }

            entity.velocity = Vector(0, 0, 0)

            entity.damage(damage, player)
        }
    }
}
