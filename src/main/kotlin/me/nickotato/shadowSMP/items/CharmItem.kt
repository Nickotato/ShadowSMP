package me.nickotato.shadowSMP.items

import me.nickotato.shadowSMP.utils.ItemUtils
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object CharmItem {
    fun create(name: Component, baseLore: List<Component>): ItemStack {
        val item = ItemStack(Material.BOOK, 1)
        val meta = item.itemMeta ?: return item
        meta.setMaxStackSize(1)

        meta.displayName(name)

        val lore = baseLore.toMutableList()
        meta.lore(lore)

        item.itemMeta = meta
        return ItemUtils.markAsCharm(item)
    }
}