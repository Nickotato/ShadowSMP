package me.nickotato.shadowSMP.gui

import me.nickotato.shadowSMP.enums.Charm
import me.nickotato.shadowSMP.manager.PlayerManager
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class ManagingCharmGui(val target: Player): Gui(Component.text("Setting charm of ${target.name}"), 27){
    init {
        for ((index, charm) in Charm.entries.withIndex()) {
//            val item = ItemStack(Material.ENCHANTED_BOOK, 1)
//            val meta = item.itemMeta
//            meta.displayName(Component.text("§d${charm.name}"))
//            item.itemMeta = meta

            setItem(index, charm.item)
        }
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true

        val slot = event.slot
        if (slot < 0 || slot >= Charm.entries.size) return  // safety check

        val selectedCharm = Charm.entries[slot]  // Get the ghost by the slot index
        val player = event.whoClicked as Player

        // Now you can set the ghost to the player
        // e.g., PlayerManager.setGhost(player, selectedGhost)
        val targetData = PlayerManager.getPlayerData(target)
        targetData.charm = selectedCharm
        player.sendMessage(Component.text("Changed ${target.name}'s charm to: ${selectedCharm.name}"))
    }
}