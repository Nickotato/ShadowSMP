package me.nickotato.shadowSMP.abilities.spriggan

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.manager.AbilityManager
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

class SprigganAbility : Ability(0) {
    override fun execute(player: Player) {

        if (AbilityManager.sprigganAbilityPlayers.add(player.uniqueId)) {
            player.sendActionBar(Component.text("Spriggan ability: §aOn"))
        } else {
            AbilityManager.sprigganAbilityPlayers.remove(player.uniqueId)
            player.sendActionBar(Component.text("Spriggan ability: §cOff"))
        }
    }
}