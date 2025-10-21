package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.data.PlayerData
import me.nickotato.shadowSMP.data.PlayerDataStorage
import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.manager.PlayerManager
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerJoinListener: Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        val existing = PlayerDataStorage.loadPlayerData(player.uniqueId) ?: PlayerData(
            player.uniqueId,
            player.name,
            0,
            false,
            Ghost.entries.random(),
            null,
        )

        PlayerManager.addNewPlayerFromData(existing)

        val playerData = PlayerManager.getPlayerData(player)

        if (!player.hasPlayedBefore()) {
            event.joinMessage(Component.text("§e Welcome §a${player.name} §eto the server for the first time!!!"))
            player.sendMessage("§3Your ghost is §d${playerData.ghost.name}")

        }
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        val data = PlayerManager.players[player.uniqueId] ?: return
        PlayerDataStorage.savePlayerData(data)
        PlayerManager.players.remove(player.uniqueId)
    }
}