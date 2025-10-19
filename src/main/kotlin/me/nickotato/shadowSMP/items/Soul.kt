package me.nickotato.shadowSMP.items

import me.nickotato.shadowSMP.utils.ItemUtils
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object Soul {
    fun create(): ItemStack {
        val item = ItemUtils.setItemType(ItemStack(Material.ENCHANTED_BOOK, 1), "soul")
        val meta = item.itemMeta ?: return item
        meta.setMaxStackSize(1)

        meta.displayName(Component.text("§dSoul"))
        meta.lore(mutableListOf<Component>(Component.text("§7Use to get +1 soul")))

        item.itemMeta = meta

        return ItemUtils.addRelicLore(item)
    }
}