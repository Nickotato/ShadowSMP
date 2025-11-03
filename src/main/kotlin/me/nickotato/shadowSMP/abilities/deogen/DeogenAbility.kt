package me.nickotato.shadowSMP.abilities.deogen

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.manager.AbilityManager
import me.nickotato.shadowSMP.utils.EntityUtils
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.cos
import kotlin.math.sin

class DeogenAbility: Ability(30) {
    override fun execute(player: Player) {
        object : BukkitRunnable() {
            var timesRun = 0
            override fun run() {
                if (timesRun > 3 * 20) {
                    cancel()
                    mainLogic(player)
                    return
                }

                val radians = Math.toRadians(timesRun * 10.0)
                val radius = 1.0
                val height = timesRun * 0.05 // gentle upward movement
                val x = cos(radians) * radius
                val z = sin(radians) * radius

                val loc1 = player.location.clone().add(x, height, z)
                val loc2 = player.location.clone().add(-x, height, -z)

                val dust = Particle.DustOptions(Color.fromRGB(58, 228, 234), 1f)

                player.world.spawnParticle(Particle.DUST, loc1, 2, 0.0, 0.0, 0.0, dust)
                player.world.spawnParticle(Particle.DUST, loc2, 2, 0.0, 0.0, 0.0, dust)

                timesRun++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)


    }

    private fun mainLogic(player: Player) {
        AbilityManager.tempNoFallPlayers.add(player.uniqueId)
        player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 100, 3))

        val direction = player.location.direction.normalize()

        val launchVelocity = direction.multiply(50.0) // adjust magnitude as needed

        player.velocity = launchVelocity

        object : BukkitRunnable(){
            var timesRun = 0
            override fun run() {
                if (EntityUtils.isOnGround(player) && timesRun > 40) {
                    cancel()
                    return
                }

                player.world.spawnParticle(Particle.SNOWFLAKE, player.location.clone(), 10, 0.0, 0.0, 0.0)

                timesRun++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }


}