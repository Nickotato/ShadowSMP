package me.nickotato.shadowSMP.listeners.item

import me.nickotato.shadowSMP.manager.ItemManager
import me.nickotato.shadowSMP.utils.ItemUtils
import org.bukkit.entity.Item
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityCombustByBlockEvent
import org.bukkit.event.entity.EntityCombustEvent

class ItemBurnListener: Listener {
    @EventHandler
    fun onItemBurn(event: EntityCombustEvent) {
        val item = event.entity as? Item ?: return
        val type = ItemUtils.getItemType(item.itemStack) ?: return
        if (ItemManager.isIndestructible(type)) {
            event.isCancelled = true
        }else if (ItemUtils.isCharm(item.itemStack)) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onItemCombustByBlock(event: EntityCombustByBlockEvent) {
        val item = event.entity as? Item ?: return
        val type = ItemUtils.getItemType(item.itemStack) ?: return
        if (ItemManager.isIndestructible(type)) {
            event.isCancelled = true
        }else if (ItemUtils.isCharm(item.itemStack)) {
            event.isCancelled = true
        }
    }
}