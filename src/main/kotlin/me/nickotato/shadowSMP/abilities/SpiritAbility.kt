package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.manager.AbilityManager
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.cos
import kotlin.math.sin

class SpiritAbility: Ability(120) {
    override fun execute(player: Player) {
        AbilityManager.invulnerablePlayers.add(player.uniqueId)

        object : BukkitRunnable(){
            override fun run() {
                AbilityManager.invulnerablePlayers.remove(player.uniqueId)
            }
        }.runTaskLater(ShadowSMP.instance, 30 * 20)

        object : BukkitRunnable() {
            var timesRun = 0
            var rotation = 0.0
            override fun run() {
                if (!AbilityManager.invulnerablePlayers.contains(player.uniqueId)) {
                    cancel()
                    return
                }

                val playerLoc = player.location.clone()

                val angle = 20 * timesRun
                val radians = Math.toRadians(angle.toDouble())
                val radius = 1
                val x = cos(radians) * radius
                val z = sin(radians) * radius
                val y = timesRun * 0.07

                val location1 = playerLoc.clone().add(x, y, z)
                val location2 = playerLoc.clone().add(-x, y, -z)

                player.world.spawnParticle(Particle.SCRAPE, location1, 10, 0.0, 0.0, 0.0)
                player.world.spawnParticle(Particle.SCRAPE, location2, 10, 0.0, 0.0, 0.0)

                val pillarCount = 2
                val pillarRadius = 1.5
                val pillarHeight = 3

                for (i in 0 until pillarCount) {
                    val angleDeg = rotation + i * (360.0 / pillarCount)
                    val rad = Math.toRadians(angleDeg)
                    val px = cos(rad) * pillarRadius
                    val pz = sin(rad) * pillarRadius

                    for (py in 0..pillarHeight) {
                        player.world.spawnParticle(Particle.SCRAPE,
                            playerLoc.clone().add(px, py.toDouble(), pz),
                            1, 0.0, 0.0, 0.0)
                    }
                }

                timesRun++
                rotation += 10 // adjust rotation speed
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }
}