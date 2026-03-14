package me.nickotato.shadowSMP.abilities.mace

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

object MaceAbility2: Ability(120) {
    override fun execute(player: Player) {
        val durationSeconds = 10
        val speedLevel = 1

        player.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, durationSeconds * 20, 0, false, false))
        player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, durationSeconds * 20, speedLevel, true, true))

        object : BukkitRunnable() {
            var ticksRun = 0

            init {
                Bukkit.getOnlinePlayers().forEach { other ->
                    if (other != player) other.hidePlayer(ShadowSMP.instance, player)
                }
            }

            override fun run() {
                if (ticksRun >= durationSeconds * 20) {
                    Bukkit.getOnlinePlayers().forEach { other ->
                        if (other != player) other.showPlayer(ShadowSMP.instance, player)
                    }
                    cancel()
                    return
                }

                player.world.spawnParticle(
                    Particle.LARGE_SMOKE,
                    player.location.add(0.0, 1.0, 0.0),
                    5, 0.2, 0.2, 0.2, 0.01
                )

                ticksRun++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)


        player.sendMessage("§aYou are now invisible for 10 seconds")
    }
}