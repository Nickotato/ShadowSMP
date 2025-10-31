package me.nickotato.shadowSMP.abilities.ignis

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class IgnisAbility : Ability(60) {
    override fun execute(player: Player) {
        val world = player.world
        val dir = player.location.direction.normalize()
        val start = player.location.add(dir.multiply(2))

        val wallWidth = 40
        val wallHeight = 7
        val wallDuration = 100L
        val damagePerTick = 5.0

        val right = dir.clone().crossProduct(Vector(0, 1, 0)).normalize()

        val fireBlocks = mutableListOf<Location>()
        for (w in -wallWidth / 2..wallWidth / 2) {
            val baseLoc = start.clone().add(right.clone().multiply(w))
            val ground = baseLoc.clone().block
            val above = baseLoc.clone().add(0.0, 1.0, 0.0).block

            if (ground.type.isSolid && above.type == Material.AIR) {
                above.type = Material.FIRE
                fireBlocks.add(above.location)
            }
        }

        object : BukkitRunnable() {
            var ticks = 0
            override fun run() {
                ticks++

                for (w in -wallWidth / 2..wallWidth / 2) {
                    for (h in 0 until wallHeight) {
                        val loc = start.clone()
                            .add(right.clone().multiply(w))
                            .add(0.0, h.toDouble(), 0.0)
                        world.spawnParticle(Particle.FLAME, loc, 4, 0.1, 0.1, 0.1, 0.01)
                    }
                }

                val nearby = world.getNearbyEntities(start, wallWidth.toDouble(), wallHeight.toDouble(), 2.0)
                for (entity in nearby) {
                    if (entity != player && entity is LivingEntity) {
                        entity.fireTicks = 60
                        entity.damage(damagePerTick, player)
                    }
                }

                if (ticks >= wallDuration / 5) {
                    for (loc in fireBlocks) {
                        if (loc.block.type == Material.FIRE) loc.block.type = Material.AIR
                    }
                    cancel()
                }
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 5L)
    }
}
