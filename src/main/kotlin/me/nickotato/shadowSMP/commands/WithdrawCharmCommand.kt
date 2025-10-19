package me.nickotato.shadowSMP.commands

import me.nickotato.shadowSMP.manager.PlayerManager
import me.nickotato.shadowSMP.utils.CommandUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class WithdrawCharmCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        val player = CommandUtils.requirePlayer(sender) ?: return false
        val playerData = PlayerManager.getPlayerData(player)
        val charm = playerData.charm

        if (charm == null) {
            player.sendMessage("§cYou have no charm to withdraw")
            return false
        }

        if (player.inventory.firstEmpty() == -1) {
            player.sendMessage("§cYour inventory is full! Make space before withdrawing your charm.")
            return false
        }

        player.inventory.addItem(charm.item)
        playerData.charm = null

        return true
    }
}