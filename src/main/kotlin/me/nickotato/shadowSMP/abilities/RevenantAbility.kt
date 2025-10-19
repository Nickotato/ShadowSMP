package me.nickotato.shadowSMP.abilities

import org.bukkit.entity.Player
import org.bukkit.util.Vector

class RevenantAbility: Ability(5, false) {
    override fun execute(player: Player) {
        player.velocity = Vector(player.location.direction.x, 1.5, player.location.direction.z)
    }
}