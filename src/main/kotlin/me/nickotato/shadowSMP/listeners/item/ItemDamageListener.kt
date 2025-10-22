package me.nickotato.shadowSMP.listeners.item

import me.nickotato.shadowSMP.manager.ItemManager
import me.nickotato.shadowSMP.utils.ItemUtils
import org.bukkit.Material
import org.bukkit.entity.Item
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

class ItemDamageListener: Listener {
    @EventHandler
    fun onItemDamage(event: EntityDamageEvent) {
        val item = event.entity as? Item ?: return
        val type = ItemUtils.getItemType(item.itemStack)

//        Bukkit.broadcast(Component.text(ItemUtils.isCharm(item.itemStack).toString()))

        if (type is String && ItemManager.isIndestructible(type)) {
            event.isCancelled = true
        } else if (ItemUtils.isCharm(item.itemStack)) {
            event.isCancelled = true
        } else if (item.itemStack.type == Material.DRAGON_EGG) {
            event.isCancelled = true
        }
    }
}