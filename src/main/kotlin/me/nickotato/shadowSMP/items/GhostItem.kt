package me.nickotato.shadowSMP.items

import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.utils.ItemUtils
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object GhostItem {
    fun create(ghost: Ghost): ItemStack {
        val item = ItemStack(Material.ENCHANTED_BOOK, 1)
        val meta = item.itemMeta ?: return item
        meta.setMaxStackSize(1)

        val displayName = ghost.name
            .lowercase()
            .replaceFirstChar { it.uppercase() }

        meta.displayName(Component.text("§d$displayName")) // Consider adding display name to the ghost enum

        val lore = mutableListOf<Component>()
        meta.lore(lore)

        item.itemMeta = meta

        val markedItem = ItemUtils.markAsGhost(item)

        val typedItem = ItemUtils.setItemType(markedItem, "ghost_${ghost.name.lowercase()}")

        return typedItem
    }
}