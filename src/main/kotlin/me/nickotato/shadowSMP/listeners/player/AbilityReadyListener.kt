package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.abilities.reaper.ReaperUltimate
import me.nickotato.shadowSMP.abilities.revenant.RevenantAbility
import me.nickotato.shadowSMP.enums.AbilityType
import me.nickotato.shadowSMP.events.AbilityReadyEvent
import net.kyori.adventure.text.Component
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class AbilityReadyListener: Listener {
    @EventHandler
    fun onAbilityReady(event: AbilityReadyEvent) {
        val player = event.player
        val ability = event.ability
        val abilityType = event.type

        if (ability is RevenantAbility) {
            player.allowFlight = true
        }

        val message = when (abilityType) {
            AbilityType.NORMAL -> {
                "Ability is ready"
            }
            AbilityType.ULTIMATE -> {
                "Ultimate is ready"
            }
            AbilityType.CHARM ->  {
                "Charm ability is ready"
            }
            else -> "Item ability is ready"
        }

        if (ability is ReaperUltimate) {
            reaperUltimateParticles(player)
        }

        if (ability.cooldown > 0) {
            player.sendActionBar(Component.text("§a$message"))
//        player.sendMessage(Component.text("§6[ShadowSMP] §a$message"))
            player.sendMessage(Component.text("§a$message"))
            player.playSound(player.location, Sound.BLOCK_BEACON_POWER_SELECT, 1f, 1f)
        }

    }

    fun reaperUltimateParticles(player: Player) {
        player.world.spawnParticle(
            Particle.SCULK_SOUL,
            player.location.clone().add(0.0, 1.0, 0.0),
            40,
            0.6,
            0.8,
            0.6,
            0.3
        )

        player.world.playSound(player.location, Sound.ENTITY_GHAST_WARN, 1f, 1f)
    }
}