package me.nickotato.shadowSMP.commands

import me.nickotato.shadowSMP.gui.GiveShadowItemsGui
import me.nickotato.shadowSMP.manager.GuiManager
import me.nickotato.shadowSMP.utils.CommandUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class GiveShadowItemsCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        val player = CommandUtils.requirePlayer(sender) ?: return false

        GuiManager.open(GiveShadowItemsGui(), player)

        return true
    }
}