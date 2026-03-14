package me.nickotato.shadowSMP.commands

import me.nickotato.shadowSMP.gui.ConfigGui
import me.nickotato.shadowSMP.manager.GuiManager
import me.nickotato.shadowSMP.utils.CommandUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ConfigCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, p2: String, p3: Array<out String>): Boolean {
        val player = CommandUtils.requirePlayer(sender) ?: return true


        GuiManager.open(ConfigGui(), player)
        return true
    }
}