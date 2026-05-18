package me.nickotato.shadowSMP.gui

import me.nickotato.shadowSMP.enums.Charm
import me.nickotato.shadowSMP.manager.PlayerManager
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class ManagingCharmGui(val target: Player): Gui(Component.text("Setting charm of ${target.name}"), 27){
    init {
        for ((index, charm) in Charm.entries.withIndex()) {
            setItem(index, charm.item)
        }
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true

        val slot = event.slot
        if (slot < 0 || slot >= Charm.entries.size) return

        val selectedCharm = Charm.entries[slot]
        val player = event.whoClicked as Player

        PlayerManager.equipCharm(player, selectedCharm)
        player.sendMessage(Component.text("Changed ${target.name}'s charm to: ${selectedCharm.name}"))
    }
}