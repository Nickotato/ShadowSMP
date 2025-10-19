package me.nickotato.shadowSMP.items

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Upgrader: ItemStack(Material.ENCHANTED_BOOK, 1) {
    init {
        val meta = this.itemMeta
        meta.displayName(Component.text("§dUpgrader"))
        meta.lore(listOf<Component>(Component.text("§7Use to upgrade your ghost"), Component.text("§6§l§oRelic")))
        this.itemMeta = meta
    }
}