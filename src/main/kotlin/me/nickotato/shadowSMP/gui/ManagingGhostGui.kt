package me.nickotato.shadowSMP.gui

import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.manager.PlayerManager
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class ManagingGhostGui(val target: Player): Gui(Component.text("Setting ghost of ${target.name}"), 27){
    init {
        for ((index, ghost) in Ghost.entries.withIndex()) {
            val item = ItemStack(Material.ENCHANTED_BOOK, 1)
            val meta = item.itemMeta
            meta.displayName(Component.text("§d${ghost.name}"))
            item.itemMeta = meta

            setItem(index, item)
        }
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true

        val slot = event.slot
        if (slot < 0 || slot >= Ghost.entries.size) return  // safety check

        val selectedGhost = Ghost.entries[slot]  // Get the ghost by the slot index
        val player = event.whoClicked as Player

        // Now you can set the ghost to the player
        // e.g., PlayerManager.setGhost(player, selectedGhost)
        val targetData = PlayerManager.getPlayerData(target)
        targetData.ghost = selectedGhost
        player.sendMessage(Component.text("Changed ${target.name}'s ghost to: ${selectedGhost.name}"))
    }
}