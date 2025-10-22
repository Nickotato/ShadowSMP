package me.nickotato.shadowSMP.gui

import me.nickotato.shadowSMP.items.HauntedDice
import me.nickotato.shadowSMP.items.ReviveBook
import me.nickotato.shadowSMP.items.Soul
import me.nickotato.shadowSMP.items.Upgrader
import net.kyori.adventure.text.Component
import org.bukkit.event.inventory.InventoryClickEvent

class GiveShadowItemsGui: Gui(Component.text("§5Shadow items"), 27) {
    init {
        setItem(0, Upgrader.create())
        setItem(1, HauntedDice.create())
        setItem(2, Soul.create())
        setItem(3, ReviveBook.create())
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true

        val player = event.whoClicked
        val slot = event.slot
        val clickedItem = inventory.getItem(slot) ?: return

        player.inventory.addItem(clickedItem.clone())
    }
}