package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.enums.Charm
import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

class PlayerFallListener: Listener {
    @EventHandler
    fun onPlayerFall(event: EntityDamageEvent) {
        if (event.cause != EntityDamageEvent.DamageCause.FALL) return

        val entity = event.entity
        if (entity !is Player) return
        val data = PlayerManager.getPlayerData(entity)

        if (data.ghost == Ghost.REVENANT || data.charm == Charm.FEATHER) {
            event.isCancelled = true
        }
    }
}