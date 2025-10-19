package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.enums.Charm
import me.nickotato.shadowSMP.enums.Ghost
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

            ItemUtils.getItemType(item) == "upgrader" -> {
                if (playerData.isUpgraded) {
                    player.sendMessage("§cAlready Upgraded")
                    return
                }

                playerData.isUpgraded = true
                player.sendMessage("§aUpgraded your ghost")
                consumeOne(player)
                event.isCancelled = true
            }

            ItemUtils.getItemType(item) == "haunted_dice" -> {
                playerData.ghost = Ghost.entries.random()
                player.sendMessage("§aYour new ghost is §d${playerData.ghost.name}")
                consumeOne(player)
                event.isCancelled = true
            }

            ItemUtils.getItemType(item) == "soul" -> {
                if (playerData.souls >= 5) {
                    player.sendMessage("§cYou already have 5 souls.")
                    return
                }
                PlayerManager.changePlayerSouls(player, 1)
                player.sendMessage("§aYou now have §d${playerData.souls}§a souls")
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