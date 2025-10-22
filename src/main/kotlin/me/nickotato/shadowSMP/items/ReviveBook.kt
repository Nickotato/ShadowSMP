package me.nickotato.shadowSMP.items

import me.nickotato.shadowSMP.utils.ItemUtils
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ReviveBook {
    fun create(): ItemStack {
        val item = ItemUtils.setItemType(ItemStack(Material.ENCHANTED_BOOK, 1), "revive_book")
        val meta = item.itemMeta ?: return item
        meta.setMaxStackSize(1)

        meta.displayName(Component.text("§dRevive Book"))
        meta.lore(mutableListOf<Component>(Component.text("§7Use to revive a player")))

        item.itemMeta = meta

        return ItemUtils.addRelicLore(item)
    }
}