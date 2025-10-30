package me.nickotato.shadowSMP.listeners.player

import com.destroystokyo.paper.event.player.PlayerJumpEvent
import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.manager.PlayerManager
import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleFlightEvent

class PlayerJumpListener : Listener {

    @EventHandler
    fun onJump(event: PlayerJumpEvent) {
        val player = event.player
        val data = PlayerManager.getPlayerData(player)

        if (data.ghost == Ghost.REVENANT) {
            Bukkit.getScheduler().runTaskLater(ShadowSMP.instance, Runnable {
                if (!player.isFlying && !player.allowFlight) {
                    player.allowFlight = true
                }
            }, 1L)
        }
    }

    @EventHandler
    fun onFlightToggle(event: PlayerToggleFlightEvent) {
        val player = event.player
        val data = PlayerManager.getPlayerData(player)

        if (data.ghost != Ghost.REVENANT) return
        if (player.gameMode == GameMode.CREATIVE || player.gameMode == GameMode.SPECTATOR) return

        event.isCancelled = true // cancel default flight

        Bukkit.getScheduler().runTaskLater(ShadowSMP.instance, Runnable {
            player.allowFlight = false
            player.isFlying = false
        }, 2L)

        data.ghost.ability.activate(player)
    }
}
