package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.cos
import kotlin.math.sin

class GolemUltimate: Ability(60) {
    override fun execute(player: Player) {
        val plLoc = player.location

        // 1️⃣ Charge-in particles (shrinking circle, faster)
        object : BukkitRunnable() {
            var loop1 = 0
            override fun run() {
                if (loop1 >= 15) {
                    cancel()
                    // After shrinking, start the shoot-out
                    shootOut(plLoc, player)
                    return
                }
                val r = (15 - loop1) * 0.5
                for (i in 0 until 36) {
                    val angle = Math.toRadians(i * 10.0)
                    val x = r * cos(angle)
                    val z = r * sin(angle)
                    val particleLoc = plLoc.clone().add(x, 0.0, z)
                    plLoc.world?.spawnParticle(
                        Particle.DUST,
                        particleLoc,
                        10,
                        0.3,
                        0.3,
                        0.3,
                        0.0,
                        Particle.DustOptions(Color.fromRGB(100,100,100), 1f)
                    )
                    plLoc.world?.spawnParticle(
                        Particle.END_ROD,
                        particleLoc,
                        5,
                        0.3,
                        0.3,
                        0.3,
                        0.05
                    )
                }
                plLoc.world?.playSound(plLoc, Sound.ENTITY_IRON_GOLEM_ATTACK, 1f, 1f)
                loop1++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L) // 1 tick interval

    }

    private fun shootOut(loc: Location, player: Player) {
        // 2️⃣ Reverse/shoot-out particles (almost instant)
        object : BukkitRunnable() {
            var loop2 = 0
            override fun run() {
                if (loop2 >= 8) {
                    cancel()
                    // After particles, push entities
                    blastEntities(loc, player)
                    return
                }
                val r = loop2 * 1.0
                for (i in 0 until 36) {
                    val angle = Math.toRadians(i * 10.0)
                    val x = r * cos(angle)
                    val z = r * sin(angle)
                    val particleLoc = loc.clone().add(x, 0.0, z)
                    loc.world?.spawnParticle(
                        Particle.DUST,
                        particleLoc,
                        10,
                        0.5,
                        0.5,
                        0.5,
                        0.0,
                        Particle.DustOptions(Color.fromRGB(255,150,100), 1f)
                    )
                    loc.world?.spawnParticle(
                        Particle.END_ROD,
                        particleLoc,
                        5,
                        0.3,
                        0.3,
                        0.3,
                        0.1
                    )
                }
                loop2++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }

    private fun blastEntities(loc: Location, player: Player) {
        // 3️⃣ Blast nearby entities
        val nearby = loc.world?.getNearbyEntities(loc, 8.0, 8.0, 8.0)
        nearby?.forEach { entity ->
            if (entity != player) {
                val direction = entity.location.toVector().subtract(loc.toVector()).normalize()
                entity.velocity = direction.multiply(10)
            }
        }
        loc.world?.playSound(loc, Sound.ENTITY_IRON_GOLEM_ATTACK, 2f, 0.8f)
    }
}