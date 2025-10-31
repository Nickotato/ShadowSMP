package me.nickotato.shadowSMP.abilities.titan

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.utils.EntityUtils
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.LivingEntity

import org.bukkit.entity.Player

class TitanUltimate : Ability(90) {

    override fun execute(player: Player) {
        val world = player.world
        val startLoc = player.location

        player.velocity = player.velocity.setY(1.5)

        world.playSound(startLoc, Sound.ENTITY_IRON_GOLEM_ATTACK, 1.5f, 1.2f)
        world.spawnParticle(Particle.CLOUD, startLoc, 40, 1.0, 1.0, 1.0, 0.1)

        Bukkit.getScheduler().runTaskLater(ShadowSMP.instance, Runnable {

            player.velocity = player.velocity.setY(-2.5)

            Bukkit.getScheduler().runTaskTimer(ShadowSMP.instance, Runnable {
                if (EntityUtils.isOnGround(player)) {
                    val loc = player.location

                    world.playSound(loc, Sound.ENTITY_WITHER_BREAK_BLOCK, 1.5f, 0.6f)
                    world.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1.2f, 0.9f)
                    world.spawnParticle(Particle.EXPLOSION, loc, 1)
                    world.spawnParticle(Particle.CLOUD, loc, 80, 2.0, 1.0, 2.0, 0.1)
                    world.spawnParticle(Particle.BLOCK, loc, 100, 2.0, 0.5, 2.0, 0.2, loc.block.blockData)

                    val radius = 8.0
                    for (entity in world.getNearbyEntities(loc, radius, radius, radius)) {
                        if (entity is LivingEntity && entity != player) {
                            val direction = entity.location.toVector().subtract(loc.toVector()).normalize()
                            val knockback = direction.multiply(2.0).setY(0.8)
                            entity.velocity = knockback
                            entity.damage(8.0, player)
                        }
                    }

                    world.playSound(loc, Sound.BLOCK_ANVIL_PLACE, 1.0f, 0.5f)
                    world.spawnParticle(Particle.EXPLOSION, loc, 60, 3.0, 0.5, 3.0, 0.05)

                    Bukkit.getScheduler().cancelTasks(ShadowSMP.instance)
                }
            }, 0L, 2L)
        }, 20L)
    }

}
