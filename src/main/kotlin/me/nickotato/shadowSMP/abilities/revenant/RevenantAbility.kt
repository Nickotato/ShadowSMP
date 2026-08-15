package me.nickotato.shadowSMP.abilities.revenant

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import me.nickotato.shadowSMP.abilitycontext.PlayerAbilityContext
import me.nickotato.shadowSMP.manager.AbilityManager
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class RevenantAbility: Ability(10) {
    override fun execute(context: AbilityContext) {
        if (context !is PlayerAbilityContext) return
        val player = context.player
        AbilityManager.tempNoFallPlayers.add(player.uniqueId)
        player.allowFlight = false
        val world = player.world
        val loc = player.location

        world.playSound(loc, Sound.ENTITY_BREEZE_HURT, 2.0f, 1.0f)

        player.velocity = Vector(player.location.direction.x, 1.5, player.location.direction.z)

        object : BukkitRunnable() {
            var i = 0
            override fun run() {
                if (i > 30) {
                    cancel()
                    return
                }

                val velocity = player.velocity.y

                val dustColor = when {
                    velocity > 0 -> Color.fromRGB(20, 255, 25)
                    velocity <= 0 -> Color.fromRGB(20, 100, 25)
                    else -> Color.fromRGB(255, 255, 255)
                }

                val dust = Particle.DustOptions(dustColor, 0.6f)
                val location = player.location.clone()

                player.world.spawnParticle(Particle.DUST, location, 100, 0.3, 0.1, 0.3, dust)

                i++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }
}