package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.persistence.PersistentDataType

class PlayerPlaceListener: Listener {
    private val key = NamespacedKey(ShadowSMP.instance, "spooky_obsidian")

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val item = event.itemInHand
        val meta = item.itemMeta ?: return

        if (meta.persistentDataContainer.has(key, PersistentDataType.BYTE)) {
            event.isCancelled = true
        }
    }
}