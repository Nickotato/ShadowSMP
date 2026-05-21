package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.data.PlayerData
import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.events.PlayerDataChangeEvent
import me.nickotato.shadowSMP.manager.AbilityManager
import me.nickotato.shadowSMP.manager.EffectManager
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.GameMode
import org.bukkit.entity.Player
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

        AbilityManager.sprigganUltimatePlayers.remove(player.uniqueId)
        AbilityManager.sprigganAbilityPlayers.remove(player.uniqueId)

        player.allowFlight = newData.ghost == Ghost.REVENANT || player.gameMode == GameMode.CREATIVE || player.gameMode == GameMode.SPECTATOR

        handleEffects(player, oldData, newData)
    }

    private fun handleEffects(player: Player, oldData: PlayerData, newData: PlayerData) {
        val oldEffects = EffectManager.getDesiredEffects(oldData).toSet()
        val newEffects = EffectManager.getDesiredEffects(newData).toSet()

        val removedEffects = oldEffects - newEffects

        for (effect in removedEffects) {
            player.removePotionEffect(effect.type)
        }
    }
}