package me.nickotato.shadowSMP.commands

import me.nickotato.shadowSMP.data.PlayerData
import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AbilityCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§cOnly a player can use this command")
            return false
        }

        val playerData: PlayerData = PlayerManager.getPlayerData(sender)
        val ghost = playerData.ghost

        if (ghost == Ghost.REVENANT) {
            sender.sendMessage("§cYou use this ability by jumping mid-air")
            return true
        }

        ghost.ability.activate(sender)

        return true
    }
}