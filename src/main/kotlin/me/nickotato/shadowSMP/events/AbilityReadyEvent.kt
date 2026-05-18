package me.nickotato.shadowSMP.events

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.enums.AbilityType
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class AbilityReadyEvent(
    val player: Player,
    val ability: Ability,
    val type: AbilityType
): Event() {
    override fun getHandlers(): HandlerList = handlerList

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}