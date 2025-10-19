package me.nickotato.shadowSMP.items

import me.nickotato.shadowSMP.utils.ItemUtils
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object HauntedDice {
    fun create(): ItemStack {
        val item = ItemUtils.setItemType(ItemStack(Material.ENCHANTED_BOOK), "haunted_dice")
        val meta = item.itemMeta ?: return item

        meta.displayName(Component.text("§dHaunted Dice"))
        meta.lore(
            mutableListOf<Component>(
                Component.text("§7Use to get a random ghost")
            )
        )

        item.itemMeta = meta

        return ItemUtils.makeUnique(ItemUtils.addRelicLore(item))

    }
}