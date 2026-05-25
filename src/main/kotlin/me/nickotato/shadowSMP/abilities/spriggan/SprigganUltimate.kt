package me.nickotato.shadowSMP.abilities.spriggan

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.manager.AbilityManager
import net.kyori.adventure.text.Component
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class SprigganUltimate: Ability(60) {
    private val durationTicks = 20 *10L

    override fun execute(player: Player) {
        playActivationParticles(player)
        AbilityManager.sprigganUltimatePlayers.add(player.uniqueId)
        player.sendActionBar(Component.text("§aUltimate Enabled"))

        object : BukkitRunnable() {
            override fun run() {
                AbilityManager.sprigganUltimatePlayers.remove(player.uniqueId)
                playDeactivationParticles(player)
                player.sendActionBar(Component.text("§cUltimate Disabled"))
            }
        }.runTaskLater(ShadowSMP.instance, durationTicks)
    }

    private fun playActivationParticles(player: Player) {
        player.world.spawnParticle(
            Particle.CLOUD,
            player.location,
            35,
            0.5, 0.6, 0.5,
            0.02
        )

        player.world.spawnParticle(
            Particle.WITCH,
            player.location,
            20,
            0.4, 0.5, 0.4,
            0.05
        )

        player.world.playSound(
            player.location,
            Sound.ENTITY_RABBIT_JUMP,
            1.2f,
            1.1f
        )
    }

    private fun playDeactivationParticles(player: Player) {
        player.world.spawnParticle(
            Particle.CLOUD,
            player.location,
            25,
            0.4, 0.2, 0.4,
            0.01
        )

        player.world.spawnParticle(
            Particle.LARGE_SMOKE,
            player.location,
            15,
            0.3, 0.2, 0.3,
            0.01
        )

        player.world.playSound(
            player.location,
            Sound.BLOCK_FIRE_EXTINGUISH,
            0.8f,
            1.2f
        )
    }


}