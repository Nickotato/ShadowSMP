package me.nickotato.shadowSMP.commands

import me.nickotato.shadowSMP.gui.ManagePlayerGui
import me.nickotato.shadowSMP.manager.GuiManager
import me.nickotato.shadowSMP.utils.CommandUtils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ManageCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        val player = CommandUtils.requirePlayer(sender) ?: return false

        if (args.isEmpty()) {
            sender.sendMessage("§cPlease Provide a Player")
            return false
        }

        val target = Bukkit.getPlayerExact(args[0]) ?: run {
            sender.sendMessage("§cPlayer '${args[0]}' not found!")
            return false
        }

        GuiManager.open(ManagePlayerGui(target), player)
        return true
    }
}