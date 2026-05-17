package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.events.PlayerDataChangeEvent
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PlayerDataChangeListener: Listener {
    @EventHandler
    fun onChange(event: PlayerDataChangeEvent) {
        val player = event.player
        val oldData = event.oldData
        val newData = event.newData

        PlayerManager.updatePlayerNametag(player)
        PlayerManager.updatePlayerMaxHP(player)

//        player.allowFlight = newData.ghost == Ghost.REVENANT || player.gameMode == GameMode.CREATIVE || player.gameMode == GameMode.SPECTATOR
    }
}