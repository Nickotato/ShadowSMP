package me.nickotato.shadowSMP.items

import me.nickotato.shadowSMP.utils.ItemUtils
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object Reliquary {
    fun create() : ItemStack {
        val item = ItemUtils.setItemType(ItemStack(Material.ENCHANTED_BOOK, 1), "reliquary")
        val meta = item.itemMeta ?: return item
        meta.setMaxStackSize(1)

        meta.displayName(Component.text("§dReliquary"))
        meta.lore(mutableListOf<Component>(Component.text("§7Use to get a random charm")))

        item.itemMeta = meta

        return ItemUtils.addRelicLore(item)
    }
}