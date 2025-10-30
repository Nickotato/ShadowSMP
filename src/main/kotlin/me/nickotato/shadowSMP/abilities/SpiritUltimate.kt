package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.GameMode
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class SpiritUltimate: Ability(90) {
    override fun execute(player: Player) {
        val originalGm = player.gameMode

        player.gameMode = GameMode.SPECTATOR

        object : BukkitRunnable(){
            override fun run() {
                player.gameMode = originalGm
            }
        }.runTaskLater(ShadowSMP.instance, 10 * 20)

        object : BukkitRunnable(){
            override fun run() {
                if (player.gameMode != GameMode.SPECTATOR) {
                    cancel()
                    return
                }

                player.world.spawnParticle(Particle.GLOW, player.location.clone().add(0.0, 2.0, 0.0), 10, 0.0, 0.0, 0.0)
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }
}