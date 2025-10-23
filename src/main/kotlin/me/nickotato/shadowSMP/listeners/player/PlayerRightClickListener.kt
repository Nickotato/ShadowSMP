package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.enums.Charm
import me.nickotato.shadowSMP.enums.Ghost
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

            item.isSimilar(Charm.ARES_BRACELET.item) -> {
                if (playerData.charm != null) {
                    player.sendMessage("§cYou already have a charm equipped.")
                    return
                }

                playerData.charm = Charm.ARES_BRACELET
                player.sendMessage("§aYou equipped Ares's Bracelet")
                consumeOne(player)
                event.isCancelled = true
            }

            item.isSimilar(Charm.FROST_GALE.item) -> {
                if (playerData.charm != null) {
                    player.sendMessage("§cYou already have a charm equipped.")
                    return
                }

                playerData.charm = Charm.FROST_GALE
                player.sendMessage("§aYou equipped the Frost Gale")
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
                // Define which ghosts should be excluded
                val excludedGhosts = mutableSetOf<Ghost>()

                // Always exclude the player's current ghost
                playerData.ghost.let { excludedGhosts.add(it) }

                // Optionally exclude others manually (example)
                // excludedGhosts.add(Ghost.SPOOKY)
                // excludedGhosts.add(Ghost.HAUNTER)

                // Filter available ghosts
                val availableGhosts = Ghost.entries.filterNot { it in excludedGhosts }

                if (availableGhosts.isEmpty()) {
                    player.sendMessage("§cNo available ghosts to choose from!")
                    return
                }

                // Pick a new ghost from the available ones
                val newGhost = availableGhosts.random()
                playerData.ghost = newGhost

                player.sendMessage("§aYour new ghost is §d${newGhost.name}")
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

            ItemUtils.getItemType(item) == "revive_book" -> {
                GuiManager.open(ReviveBookGui(), player)
                //consumeOne(player)
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