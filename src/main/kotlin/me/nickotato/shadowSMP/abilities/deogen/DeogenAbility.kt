package me.nickotato.shadowSMP.abilities.deogen

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.manager.AbilityManager
import me.nickotato.shadowSMP.utils.EntityUtils
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Sound
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
                val height = timesRun * 0.05
                val x = cos(radians) * radius
                val z = sin(radians) * radius

                val loc1 = player.location.clone().add(x, height, z)
                val loc2 = player.location.clone().add(-x, height, -z)

                val dust = Particle.DustOptions(Color.fromRGB(58, 228, 234), 1f)

                player.world.spawnParticle(Particle.DUST, loc1, 2, 0.0, 0.0, 0.0, dust)
                player.world.spawnParticle(Particle.DUST, loc2, 2, 0.0, 0.0, 0.0, dust)

                player.playSound(player.location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 0.5f, 1.0f)

                timesRun++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }

    private fun mainLogic(player: Player) {
        AbilityManager.tempNoFallPlayers.add(player.uniqueId)
        player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 100, 3))

        val direction = player.location.direction.normalize()
        val launchVelocity = direction.multiply(50.0)
        player.velocity = launchVelocity

        player.playSound(player.location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1.0f, 1.0f)

        object : BukkitRunnable(){
            var timesRun = 0
            override fun run() {
                if (EntityUtils.isOnGround(player) && timesRun > 40) {
                    cancel()
                    player.playSound(player.location, Sound.ENTITY_PLAYER_BIG_FALL, 1.0f, 1.0f)
                    return
                }

                player.world.spawnParticle(Particle.SNOWFLAKE, player.location.clone(), 10, 0.0, 0.0, 0.0)

                if (timesRun % 5 == 0) {
                    player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HAT, 0.3f, 1.2f)
                }

                timesRun++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }
}
