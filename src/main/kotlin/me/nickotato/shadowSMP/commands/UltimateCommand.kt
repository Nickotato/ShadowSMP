package me.nickotato.shadowSMP.commands

import me.nickotato.shadowSMP.config.Settings
import me.nickotato.shadowSMP.manager.PlayerManager
import me.nickotato.shadowSMP.utils.CommandUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class UltimateCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        val player = CommandUtils.requirePlayer(sender) ?: return false
        val playerData = PlayerManager.getPlayerData(player)

        if (!playerData.isUpgraded && Settings.upgradersNeeded) {
            player.sendMessage("§cYou need to upgrade your ghost to use this ability.")
            return false
        }

        val souls = playerData.souls

        if (souls < -5 && Settings.disableGhostOnSoulLimit) {
            sender.sendMessage("§cYou need -5 souls or more to use this ability")
            return true
        }

        playerData.ghost.ultimate.activate(player)

        return true
    }
}