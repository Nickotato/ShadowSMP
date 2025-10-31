package me.nickotato.shadowSMP.abilities.reaper

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.manager.AbilityManager
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class ReaperUltimate: Ability(120) {
    override fun execute(player: Player) {
        AbilityManager.trueDamagePlayers.add(player.uniqueId)
        player.sendMessage("§4§oYour next hit against a player will deal true damage... §7unless you wait 10 seconds")

        object : BukkitRunnable(){
            override fun run() {
                if (AbilityManager.trueDamagePlayers.contains(player.uniqueId)) {
                    AbilityManager.trueDamagePlayers.remove(player.uniqueId)
                    player.sendMessage("§7§oha ha missed it")
                }
            }
        }.runTaskLater(ShadowSMP.instance, 20 * 10)
    }
}