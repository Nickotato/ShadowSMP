package me.nickotato.shadowSMP.commands

import me.nickotato.shadowSMP.manager.PlayerManager
import me.nickotato.shadowSMP.utils.CommandUtils
import net.kyori.adventure.text.Component
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class CharmAbilityCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        val player = CommandUtils.requirePlayer(sender) ?: return false

        val data = PlayerManager.getPlayerData(player)
        val charm = data.charm
        if (charm == null) {
            player.sendMessage(Component.text("§cYou have no charm"))
            return true
        } else if (charm.ability == null) {
            player.sendMessage(Component.text("§cCharm does not have an ability"))
            return true
        }

        charm.ability.activate(player)

        return true
    }
}