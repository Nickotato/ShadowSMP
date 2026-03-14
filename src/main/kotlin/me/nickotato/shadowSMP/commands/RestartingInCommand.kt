package me.nickotato.shadowSMP.commands

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class RestartingInCommand : CommandExecutor {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {

        if (args.isEmpty()) {
            sender.sendMessage("§cUsage: /restarting <seconds> [reason]")
            return true
        }

        val seconds: Int = try {
            args[0].toInt()
        } catch (e: NumberFormatException) {
            sender.sendMessage("§cPlease provide a valid number of seconds or $e.")
            return true
        }

        val reason: String = if (args.size > 1) {
            args.slice(1 until args.size).joinToString(" ")
        } else {
            "Server restarting"
        }

        Bukkit.broadcast(Component.text("§c$reason in $seconds seconds!"))

        // Bukkit.getServer().scheduler.runTaskLater(plugin, Runnable { Bukkit.shutdown() }, seconds * 20L)

        return true
    }
}
