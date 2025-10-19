package me.nickotato.shadowSMP.utils

import net.kyori.adventure.text.Component
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.util.UUID

object ItemUtils {
    fun makeUnique(item: ItemStack): ItemStack {
        val meta = item.itemMeta ?: return item

        val key = NamespacedKey("shadowsmp", "unique_id")
        meta.persistentDataContainer.set(key, PersistentDataType.STRING, UUID.randomUUID().toString())

        item.itemMeta = meta

        return item
    }

    fun setItemType(item: ItemStack, type: String): ItemStack {
        val meta = item.itemMeta ?: return item
        val key = NamespacedKey("shadowsmp", "item_type")
        meta.persistentDataContainer.set(key, PersistentDataType.STRING, type)
        item.itemMeta = meta
        return item
    }

    fun getItemType(item: ItemStack?): String? {
        if (item == null || !item.hasItemMeta()) return null
        val key = NamespacedKey("shadowsmp", "item_type")
        return item.itemMeta?.persistentDataContainer?.get(key, PersistentDataType.STRING)
    }

    fun addRelicLore(item: ItemStack): ItemStack {
        val meta = item.itemMeta ?: return item

        // Get existing lore or create a new mutable list
        val existingLore = meta.lore()?.toMutableList() ?: mutableListOf()

        // Add the "Relic" line
        existingLore.add(Component.text("§6§l§oRelic"))

        // Set the lore back
        meta.lore(existingLore)
        item.itemMeta = meta

        return item
    }

    fun markAsCharm(item: ItemStack): ItemStack {
        val meta = item.itemMeta ?: return item
        val key = NamespacedKey("shadowsmp", "is_charm")
        meta.persistentDataContainer.set(key, PersistentDataType.BYTE, 1)

        val existingLore = meta.lore()?.toMutableList() ?: mutableListOf()
        existingLore.add(Component.text("§3§o§lCharm"))
        meta.lore(existingLore)
        item.itemMeta = meta

        // Make the item unique AFTER setting charm metadata
        return item
    }

    fun isCharm(item: ItemStack?): Boolean {
        if (item == null || !item.hasItemMeta()) return false
        val key = NamespacedKey("shadowsmp", "is_charm")
        return item.itemMeta?.persistentDataContainer?.has(key, PersistentDataType.BYTE) == true
    }
}