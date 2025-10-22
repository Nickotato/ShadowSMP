package me.nickotato.shadowSMP.gui

import me.nickotato.shadowSMP.manager.PlayerManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryClickEvent

class ReviveBookGui: Gui(Component.text("Reviving"), 27) {
    init {
        val players = Bukkit.getBannedPlayers()
        for ((index, player) in players.withIndex()) {
            setItem(index, PlayerManager.getOfflinePlayerHead(player))
        }
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
        val clickedItem = event.currentItem ?: return

        val displayNameComponent = clickedItem.itemMeta?.displayName() ?: return
        val playerName = PlainTextComponentSerializer.plainText().serialize(displayNameComponent)

        PlayerManager.revivePlayer(playerName)

        event.whoClicked.sendMessage("Player $playerName has been revived!")
        event.inventory.setItem(event.slot, null)
    }

}