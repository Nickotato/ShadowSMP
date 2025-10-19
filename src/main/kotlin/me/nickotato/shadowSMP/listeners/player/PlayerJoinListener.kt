package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.manager.PlayerManager
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener: Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val playerData = PlayerManager.getPlayerData(player)
        playerData.name = player.name

        if (!player.hasPlayedBefore()) {
            event.joinMessage(Component.text("§e Welcome §a${player.name} §eto the server for the first time!!!"))
            player.sendMessage("§3Your ghost is §d${playerData.ghost.name}")

        }
    }
}