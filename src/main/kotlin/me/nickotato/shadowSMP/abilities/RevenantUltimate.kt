package me.nickotato.shadowSMP.abilities

import org.bukkit.entity.Player
import org.bukkit.util.Vector

class RevenantUltimate: Ability(60) {
    override fun execute(player: Player) {
        player.velocity = Vector(player.location.direction.x, 50.0, player.location.direction.z)
    }
}