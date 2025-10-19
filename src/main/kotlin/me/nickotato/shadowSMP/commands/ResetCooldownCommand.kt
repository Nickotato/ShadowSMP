package me.nickotato.shadowSMP.commands

import me.nickotato.shadowSMP.manager.PlayerManager
import me.nickotato.shadowSMP.utils.CommandUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ResetCooldownCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        val player = CommandUtils.requirePlayer(sender) ?: return false

        val playerData = PlayerManager.getPlayerData(player)
        playerData.ghost.ability.resetCooldown(player)
        playerData.ghost.ultimate.resetCooldown(player)
        playerData.charm?.ability?.resetCooldown(player)

        return true
    }
}