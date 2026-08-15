package me.nickotato.shadowSMP.abilities.spirit

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import me.nickotato.shadowSMP.abilitycontext.PlayerAbilityContext
import org.bukkit.GameMode
import org.bukkit.Particle
import org.bukkit.scheduler.BukkitRunnable

class SpiritUltimate: Ability(90) {
    override fun execute(context: AbilityContext) {
        if (context !is PlayerAbilityContext) return
        val player = context.player
        val originalGm = player.gameMode

        player.gameMode = GameMode.SPECTATOR

        object : BukkitRunnable(){
            override fun run() {
                player.gameMode = originalGm
            }
        }.runTaskLater(ShadowSMP.instance, 20 * 20)

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