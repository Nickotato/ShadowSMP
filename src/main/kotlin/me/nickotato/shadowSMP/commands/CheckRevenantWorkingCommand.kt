package me.nickotato.shadowSMP.commands

import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CheckRevenantWorkingCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (args.isEmpty()) {
            sender.sendMessage("Usage: /check_revenant <player>")
            return true
        }

        val target: Player? = Bukkit.getPlayer(args[0])

        if (target == null) {
            sender.sendMessage("Player not found.")
            return true
        }

        val data = PlayerManager.getPlayerData(target)

        val isRevenant = data.ghost == Ghost.REVENANT
        val abilityReady = !data.ghost.ability.isOnCooldown(target)
        val canNormallyFly = target.allowFlight
        val isFlying = target.isFlying
        val inCreative = target.gameMode == GameMode.CREATIVE || target.gameMode == GameMode.SPECTATOR
        val onGround = target.location.block.getRelative(BlockFace.DOWN).type.isSolid
        val wearingElytra = target.inventory.chestplate?.type == Material.ELYTRA

        sender.sendMessage("== Revenant Flight Check ==")
        sender.sendMessage("Revenant? $isRevenant")
        sender.sendMessage("Ability Ready? $abilityReady")
        sender.sendMessage("Allow Flight? $canNormallyFly")
        sender.sendMessage("Is Flying? $isFlying")
        sender.sendMessage("In Creative/Spectator? $inCreative")
        sender.sendMessage("On Ground? $onGround")
        sender.sendMessage("Wearing Elytra? $wearingElytra")

        return true
    }
}
