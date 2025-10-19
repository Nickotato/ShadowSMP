package me.nickotato.shadowSMP.gui

import me.nickotato.shadowSMP.enums.Charm
import net.kyori.adventure.text.Component
import org.bukkit.event.inventory.InventoryClickEvent

class GiveCharmsGui: Gui(Component.text("Select a charm"), 27) {
    init {
        for ((index, charm) in Charm.entries.withIndex()) {
            setItem(index, charm.item)
        }
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true

        val player = event.whoClicked
        val slot = event.slot
        val clickedItem = inventory.getItem(slot) ?: return

        player.inventory.addItem(clickedItem.clone())
    }
}