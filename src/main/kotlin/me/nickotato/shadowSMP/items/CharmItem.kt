package me.nickotato.shadowSMP.items

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object CharmItem {
    fun create(name: Component, baseLore: List<Component>): ItemStack {
        val item = ItemStack(Material.BOOK, 1)
        val meta = item.itemMeta ?: return item

        meta.displayName(name)

        val lore = baseLore.toMutableList()
        lore.add(Component.text("§3§o§lCharm"))
        meta.lore(lore)

        item.itemMeta = meta
        return item
    }
}