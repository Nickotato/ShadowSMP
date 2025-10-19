package me.nickotato.shadowSMP.manager

import me.nickotato.shadowSMP.gui.Gui
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

object GuiManager: Listener {
    private val openGuis = mutableMapOf<String, Gui>()

    fun open(gui: Gui, player: Player) {
        openGuis[player.name] = gui
        gui.open(player)
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        openGuis.remove(event.player.name)
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = openGuis[event.whoClicked.name] ?: return

        if (event.clickedInventory == gui.inventory) {
            gui.onClick(event)
        }
    }
}