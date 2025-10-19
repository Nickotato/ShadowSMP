package me.nickotato.shadowSMP.utils

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object CommandUtils {
    fun requirePlayer(sender: CommandSender): Player? {
        if (sender !is Player) {
            sender.sendMessage("§cOnly a player can use this command.")
            return null
        }
        return sender
    }
}