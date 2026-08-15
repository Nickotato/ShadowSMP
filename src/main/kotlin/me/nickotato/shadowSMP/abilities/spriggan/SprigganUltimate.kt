package me.nickotato.shadowSMP.abilities.spriggan

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import me.nickotato.shadowSMP.manager.AbilityManager
import net.kyori.adventure.text.Component
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.scheduler.BukkitRunnable

class SprigganUltimate: Ability(60) {
    private val durationTicks = 20 *10L

    override fun execute(context: AbilityContext) {
        playActivationParticles(context)
        AbilityManager.sprigganUltimatePlayers.add(context.caster.uniqueId)
        context.sendActionBar(Component.text("§aUltimate Enabled"))

        object : BukkitRunnable() {
            override fun run() {
                AbilityManager.sprigganUltimatePlayers.remove(context.caster.uniqueId)
                playDeactivationParticles(context)
                context.sendActionBar(Component.text("§cUltimate Disabled"))
            }
        }.runTaskLater(ShadowSMP.instance, durationTicks)
    }

    private fun playActivationParticles(context: AbilityContext) {
        context.world.spawnParticle(
            Particle.CLOUD,
            context.location,
            35,
            0.5, 0.6, 0.5,
            0.02
        )

        context.world.spawnParticle(
            Particle.WITCH,
            context.location,
            20,
            0.4, 0.5, 0.4,
            0.05
        )

        context.world.playSound(
            context.location,
            Sound.ENTITY_RABBIT_JUMP,
            1.2f,
            1.1f
        )
    }

    private fun playDeactivationParticles(context: AbilityContext) {
        context.world.spawnParticle(
            Particle.CLOUD,
            context.location,
            25,
            0.4, 0.2, 0.4,
            0.01
        )

        context.world.spawnParticle(
            Particle.LARGE_SMOKE,
            context.location,
            15,
            0.3, 0.2, 0.3,
            0.01
        )

        context.world.playSound(
            context.location,
            Sound.BLOCK_FIRE_EXTINGUISH,
            0.8f,
            1.2f
        )
    }


}