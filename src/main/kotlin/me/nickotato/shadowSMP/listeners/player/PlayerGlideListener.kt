package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityToggleGlideEvent

class PlayerGlideListener: Listener {
    @EventHandler
    fun onGlideToggle(event: EntityToggleGlideEvent) {
        val entity = event.entity
        if (entity !is Player) return

        val data = PlayerManager.getPlayerData(entity)

        val isOnGround = entity.location.block.getRelative(BlockFace.DOWN).type.isSolid
        if (data.ghost == Ghost.REVENANT && !isOnGround && entity.inventory.chestplate?.type != Material.ELYTRA) {
            event.isCancelled = true

        }
    }

}