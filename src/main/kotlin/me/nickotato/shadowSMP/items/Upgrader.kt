package me.nickotato.shadowSMP.items

import me.nickotato.shadowSMP.utils.ItemUtils
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object Upgrader {

    fun create(): ItemStack {
        val item = ItemUtils.setItemType(ItemStack(Material.ENCHANTED_BOOK), "upgrader")
        val meta = item.itemMeta ?: return item

        meta.displayName(Component.text("§dUpgrader"))
        meta.lore(
            mutableListOf<Component>(
                Component.text("§7Use to upgrade your ghost")
            )
        )

        item.itemMeta = meta

        return ItemUtils.makeUnique(ItemUtils.addRelicLore(item))
    }
}