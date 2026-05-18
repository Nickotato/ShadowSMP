package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.abilities.RevenantAbility
import me.nickotato.shadowSMP.enums.AbilityType
import me.nickotato.shadowSMP.events.AbilityReadyEvent
import net.kyori.adventure.text.Component
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

        player.sendActionBar(Component.text("§a$message"))
        player.playSound(player.location, org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT, 1f, 1f)
    }
}