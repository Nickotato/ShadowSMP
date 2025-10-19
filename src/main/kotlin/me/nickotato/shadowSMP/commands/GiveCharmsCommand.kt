package me.nickotato.shadowSMP.commands

import me.nickotato.shadowSMP.gui.GiveCharmsGui
import me.nickotato.shadowSMP.manager.GuiManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GiveCharmsCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§cOnly a player can use this command")
            return false
        }

        GuiManager.open(GiveCharmsGui(), sender)
        return true
    }
}