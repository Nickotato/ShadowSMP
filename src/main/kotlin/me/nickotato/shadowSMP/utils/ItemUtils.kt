package me.nickotato.shadowSMP.utils

import me.nickotato.shadowSMP.enums.Charm
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

        val existingLore = meta.lore()?.toMutableList() ?: mutableListOf()

        existingLore.add(Component.text("§6§l§oRelic"))

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

        return item
    }

    fun isCharm(item: ItemStack?): Boolean {
        if (item == null || !item.hasItemMeta()) return false
        val key = NamespacedKey("shadowsmp", "is_charm")
        return item.itemMeta?.persistentDataContainer?.has(key, PersistentDataType.BYTE) == true
    }

    fun getRandomCharm(): Charm {
        val excludedCharms = mutableSetOf<Charm>()

        //The Pursuit Update
        excludedCharms.add(Charm.FATES_THREAD) // Damage if either is too far
        excludedCharms.add(Charm.HUNTERS_VERDICT) // Stop if too far
        excludedCharms.add(Charm.HERMES_PURSUIT) // speed if too far

        val availableCharms = Charm.entries.filterNot { it in excludedCharms }

        if (availableCharms.isEmpty()) {
            return Charm.FEATHER
        }

        return availableCharms.random()
    }

    fun markAsGhost(item: ItemStack): ItemStack {
        val meta = item.itemMeta ?: return item
        val key = NamespacedKey("shadowsmp", "is_ghost")
        meta.persistentDataContainer.set(key, PersistentDataType.BYTE, 1)

        val existingLore = meta.lore()?.toMutableList() ?: mutableListOf()
        existingLore.add(Component.text("§5§o§lGhost"))
        meta.lore(existingLore)
        item.itemMeta = meta
        return item
    }
}