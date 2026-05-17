package me.nickotato.shadowSMP.events

import me.nickotato.shadowSMP.data.PlayerData
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PlayerDataChangeEvent(
    val player: Player,
    val oldData: PlayerData,
    val newData: PlayerData
) : Event() {

    override fun getHandlers(): HandlerList = handlerList

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}