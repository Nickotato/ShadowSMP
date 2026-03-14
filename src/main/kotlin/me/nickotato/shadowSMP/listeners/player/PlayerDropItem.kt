package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.utils.ItemUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

class PlayerDropItem: Listener {
    @EventHandler
    fun onDrop(event: PlayerDropItemEvent) {
        val item = event.itemDrop

        if (ItemUtils.getItemType(item.itemStack) == "spooky_obsidian") {
            item.isGlowing = true
        }
    }

}