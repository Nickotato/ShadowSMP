package me.nickotato.shadowSMP.commands

import me.nickotato.shadowSMP.items.Upgrader
import me.nickotato.shadowSMP.manager.PlayerManager
import me.nickotato.shadowSMP.utils.CommandUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class WithdrawUpgrader: CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        val player = CommandUtils.requirePlayer(sender) ?: return true
        val data = PlayerManager.getPlayerData(player)
        if (!data.isUpgraded) {
            player.sendMessage("§cNo Upgrader to Withdraw :(")
            return true
        }

        data.isUpgraded = false
        player.inventory.addItem(Upgrader.create())
        player.sendMessage("§dWithdrew upgrader")

        return true
    }
}