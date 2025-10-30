package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.manager.AbilityManager
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import kotlin.random.Random

class PlayerDamageListener: Listener {
    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        val player = event.entity
        if (player !is Player) return
        val data = PlayerManager.getPlayerData(player)
        val damager = event.damager

        if (AbilityManager.invulnerablePlayers.contains(player.uniqueId)) {
            event.isCancelled = true
            // Play a sound
            if (damager !is Player) return
            damager.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 0.5f, 1f)
        }

        if (data.ghost == Ghost.JINN) {
            if (Random.nextDouble() <= 0.10) { // 0.10 = 10%
                event.isCancelled = true
                player.world.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 1f, 1f)
            }
        }
    }
}