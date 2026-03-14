package me.nickotato.shadowSMP.items

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.utils.ItemUtils
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object SpookyObsidian {
    fun create(): ItemStack {
        // In SpookyObsidian.create()

        val item = ItemUtils.setItemType(ItemStack(Material.OBSIDIAN), "spooky_obsidian")
        val meta = item.itemMeta ?: return item

        meta.displayName(Component.text("§dSpooky Obsidian"))
        meta.lore(
            listOf(
                Component.text("§7This could be useful perchance.")
            )
        )

        val container = meta.persistentDataContainer
        val key = NamespacedKey(ShadowSMP.instance, "spooky_obsidian")
        container.set(key, PersistentDataType.BYTE, 1.toByte())

        item.itemMeta = meta

        return ItemUtils.makeUnique(ItemUtils.addRelicLore(item))
    }
}