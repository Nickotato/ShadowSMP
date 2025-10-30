package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.cos
import kotlin.math.sin

class TimeKeeperAbility : Ability(60) {
    override fun execute(player: Player) {
        val haste = PotionEffect(PotionEffectType.HASTE, 10 * 20, 9)
        player.addPotionEffect(haste)

        object : BukkitRunnable() {
            var timesRun = 0
            override fun run() {
                if (timesRun > 36) {
                    cancel()
                    return
                }
                val angle = 20 * timesRun
                val radians = Math.toRadians(angle.toDouble())
                val radius = 1

                val x = cos(radians) * radius
                val z = sin(radians) * radius
                val y = timesRun * 0.07

                val particleLocation = player.location.clone().add(x, y, z)

                val particle = Particle.DustOptions(Color.fromRGB(210, 0, 0), 1f)


                player.world.spawnParticle(Particle.DUST, particleLocation, 10, 0.0, 0.0, 0.0, particle)
                timesRun++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)

    }
}
