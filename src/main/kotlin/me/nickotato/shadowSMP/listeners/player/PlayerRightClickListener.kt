package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.data.PlayerData
import me.nickotato.shadowSMP.enums.Charm
import me.nickotato.shadowSMP.gui.ReviveBookGui
import me.nickotato.shadowSMP.manager.GuiManager
import me.nickotato.shadowSMP.manager.PlayerManager
import me.nickotato.shadowSMP.utils.ItemUtils
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

        // Check for charms first
        Charm.entries.forEach { charm ->
            if (item.isSimilar(charm.item)) {
                if (hasCharm(player, playerData)) return
                equipCharm(player, playerData, charm)
                event.isCancelled = true
                return
            }
        }

        // Non-charm items
        when (ItemUtils.getItemType(item)) {
            "upgrader" -> {
                if (playerData.isUpgraded) {
                    player.sendMessage("§cAlready Upgraded")
                    return
                }
                playerData.isUpgraded = true
                player.sendMessage("§aUpgraded your ghost")
                consumeOne(player)
                event.isCancelled = true
            }
            "haunted_dice" -> {
                val oldGhost = playerData.ghost
                PlayerManager.changeToRandomGhost(player)
                if (playerData.ghost != oldGhost) {
                    player.sendMessage("§aYour new ghost is §d${playerData.ghost.name}")
                    consumeOne(player)
                    event.isCancelled = true
                }
            }
            "soul" -> {
                if (playerData.souls >= 5) {
                    player.sendMessage("§cYou already have 5 souls.")
                    return
                }
                PlayerManager.changePlayerSouls(player, 1)
                player.sendMessage("§aYou now have §d${playerData.souls}§a souls")
                consumeOne(player)
                event.isCancelled = true
            }
            "revive_book" -> {
                GuiManager.open(ReviveBookGui(), player)
                event.isCancelled = true
            }
        }
    }

    private fun hasCharm(player: Player, playerData: PlayerData): Boolean {
        return if (playerData.charm != null) {
            player.sendMessage("§cYou already have a charm equipped.")
            true
        } else {
            false
        }
    }

    private fun equipCharm(player: Player, playerData: PlayerData, charm: Charm) {
        playerData.charm = charm
        player.sendMessage("§aYou equipped ${charm.displayName}") // You can add a displayName() method in Charm enum for proper names
        consumeOne(player)
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
