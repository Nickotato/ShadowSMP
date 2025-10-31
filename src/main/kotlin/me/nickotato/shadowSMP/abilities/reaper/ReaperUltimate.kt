package me.nickotato.shadowSMP.abilities.reaper

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.manager.AbilityManager
import org.bukkit.entity.Player

class ReaperUltimate: Ability(120) {
    override fun execute(player: Player) {
        AbilityManager.trueDamagePlayers.add(player.uniqueId)
        player.sendMessage("§4§oYour next hit against a player will deal true damage...")
    }
}