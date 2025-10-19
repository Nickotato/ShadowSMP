package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.enums.Charm
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class PlayerRightClickListener: Listener {
    @EventHandler
    fun onPlayerRightClick(event: PlayerInteractEvent) {
        if (!event.action.isRightClick) return
        if (event.hand != EquipmentSlot.HAND) return

        val item = event.item ?: return
        val player = event.player
        val playerData = PlayerManager.getPlayerData(player)

        when {
            item.isSimilar(Charm.FEATHER.item) -> {
                if (playerData.charm != null) {
                    player.sendMessage("§cYou already have a charm equipped.")
                    return
                }

                playerData.charm = Charm.FEATHER
                player.sendMessage("§aYou equipped the Feather charm!")
                consumeOne(player)
                event.isCancelled = true
            }
        }
    }

    private fun consumeOne(player: Player) {
        val itemInHand = player.inventory.itemInMainHand
        if (itemInHand.amount > 1) {
            itemInHand.amount--
        } else {
            player.inventory.setItemInMainHand(null)
        }
    }
}