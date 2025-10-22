package me.nickotato.shadowSMP.commands

import me.nickotato.shadowSMP.items.Soul
import me.nickotato.shadowSMP.manager.PlayerManager
import me.nickotato.shadowSMP.utils.CommandUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class WithdrawSoulCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        val player = CommandUtils.requirePlayer(sender) ?: return false
        val playerData = PlayerManager.getPlayerData(player)
        if (playerData.souls <= -5) {
            player.sendMessage("§cCannot withdraw anymore souls")
            return true
        }

        PlayerManager.changePlayerSouls(player, -1)
        player.inventory.addItem(Soul.create())
        player.sendMessage("§dYou have §b${playerData.souls} §dsouls")

        return true
    }
}