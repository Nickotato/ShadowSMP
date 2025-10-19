package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class RevenantAbility: Ability(5) {
    override fun execute(player: Player) {
        player.velocity = Vector(player.location.direction.x, 1.5, player.location.direction.z)

        object : BukkitRunnable() {
            var i = 0
            override fun run() {
                if (i > 30) {
                    cancel()
                    return
                }

                val velocity = player.velocity.y

                val dustColor = when {
                    velocity > 0 -> Color.fromRGB(20, 255, 25)
                    velocity <= 0 -> Color.fromRGB(20, 100, 25)
                    else -> Color.fromRGB(255, 255, 255)
                }

                val dust = Particle.DustOptions(dustColor, 0.6f)
                val location = player.location.clone()

                player.world.spawnParticle(Particle.DUST, location, 100, 0.3, 0.1, 0.3, dust)

                i++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }
}