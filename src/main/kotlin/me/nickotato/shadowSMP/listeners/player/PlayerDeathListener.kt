package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.items.Soul
import me.nickotato.shadowSMP.items.Upgrader
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerDeathListener: Listener {
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.player
        val playerData = PlayerManager.getPlayerData(player)
        val charm = playerData.charm
        val isUpgraded = playerData.isUpgraded

        if (charm != null) {
            player.world.dropItemNaturally(player.location, charm.item)
            playerData.charm = null
        }
        if (isUpgraded) {
            player.world.dropItemNaturally(player.location, Upgrader.create())
            playerData.isUpgraded = false
        }

        player.world.dropItemNaturally(player.location, Soul.create())
        PlayerManager.changePlayerSouls(player, -1)
    }
}