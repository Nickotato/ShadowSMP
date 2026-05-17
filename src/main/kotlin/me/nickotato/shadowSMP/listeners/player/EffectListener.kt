package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.events.PlayerDataChangeEvent
import me.nickotato.shadowSMP.manager.EffectManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityResurrectEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerQuitEvent

class EffectListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        EffectManager.applyPassiveEffects(event.player)
    }

    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {
        Bukkit.getScheduler().runTaskLater(
            ShadowSMP.instance,
            Runnable {
                EffectManager.applyPassiveEffects(event.player)
            },
            1L
        )
    }

    @EventHandler
    fun onWorldChange(event: PlayerChangedWorldEvent) {
        EffectManager.applyPassiveEffects(event.player)
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
//        EffectManager.clearTrackedState(event.player) // optional but recommended
    }

    @EventHandler
    fun onDataChange(event: PlayerDataChangeEvent) {
        EffectManager.applyPassiveEffects(event.player)
    }

    @EventHandler
    fun onTotemPop(event: EntityResurrectEvent) {
        val player = event.entity as? Player ?: return

        Bukkit.getScheduler().runTask(ShadowSMP.instance, Runnable {
            EffectManager.applyPassiveEffects(player)
        })
    }
}