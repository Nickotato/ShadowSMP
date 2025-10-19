package me.nickotato.shadowSMP.commands

import me.nickotato.shadowSMP.manager.PlayerManager
import me.nickotato.shadowSMP.utils.CommandUtils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ResetCooldownCommand: CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        // Determine the target player
        val targetPlayer = if (args.isNotEmpty()) {
            // Try to get the player from the first argument
            Bukkit.getPlayerExact(args[0]) ?: run {
                sender.sendMessage("§cPlayer '${args[0]}' not found!")
                return false
            }
        } else {
            // No args: use sender if it's a player
            CommandUtils.requirePlayer(sender) ?: run {
                sender.sendMessage("§cYou must specify a player!")
                return false
            }
        }

        // Reset cooldowns
        val playerData = PlayerManager.getPlayerData(targetPlayer)
        playerData.ghost.ability.resetCooldown(targetPlayer)
        playerData.ghost.ultimate.resetCooldown(targetPlayer)
        playerData.charm?.ability?.resetCooldown(targetPlayer)

        // Optional feedback
        sender.sendMessage("§aCooldowns reset for §f${targetPlayer.name}§a!")

        return true
    }
}
