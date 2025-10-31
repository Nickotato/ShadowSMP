package me.nickotato.shadowSMP.listeners.player

import io.papermc.paper.event.entity.EntityKnockbackEvent
import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PlayerKnockbackListener: Listener {
    @EventHandler
    fun onKnockback(event: EntityKnockbackEvent) {
        val player = event.entity
        if (player !is Player) return
        val data = PlayerManager.getPlayerData(player)

        if (data.ghost == Ghost.TITAN) {
            event.isCancelled = true
        }
    }
}