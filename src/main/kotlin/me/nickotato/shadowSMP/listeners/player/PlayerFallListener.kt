package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.config.Settings
import me.nickotato.shadowSMP.enums.Charm
import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.manager.AbilityManager
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class PlayerFallListener: Listener {
    @EventHandler
    fun onPlayerFall(event: EntityDamageEvent) {
        if (event.cause != EntityDamageEvent.DamageCause.FALL) return

        val entity = event.entity
        if (entity !is Player) return
        val data = PlayerManager.getPlayerData(entity)

        if (AbilityManager.tempNoFallPlayers.contains(entity.uniqueId)) {
            event.isCancelled = true
            AbilityManager.tempNoFallPlayers.remove(entity.uniqueId)
            return
        }

        if (data.ghost == Ghost.REVENANT || data.charm == Charm.FEATHER) {
            if (data.ghost == Ghost.REVENANT && data.charm != Charm.FEATHER && Settings.revenantCausesSlowness) {
                entity.addPotionEffect(PotionEffect(PotionEffectType.SLOWNESS, 20*5, 4))
            }
            event.isCancelled = true
            return
        }

    }
}