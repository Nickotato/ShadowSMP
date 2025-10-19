package me.nickotato.shadowSMP.listeners.item

import me.nickotato.shadowSMP.manager.ItemManager
import me.nickotato.shadowSMP.utils.ItemUtils
import org.bukkit.entity.Item
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

class ItemDamageListener: Listener {
    @EventHandler
    fun onItemDamage(event: EntityDamageEvent) {
        val item = event.entity as? Item ?: return
        val type = ItemUtils.getItemType(item.itemStack) ?: return
        if (ItemManager.isUnburnable(type)) {
            event.isCancelled = true
        }
    }
}