package me.nickotato.shadowSMP.abilities.mace

import me.nickotato.shadowSMP.abilities.Ability
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player

object MaceAbility: Ability(15) {
    override fun execute(player: Player) {
        player.world.spawnParticle(
            Particle.CLOUD, player.location.add(0.0, 1.0, 0.0),
            10, 0.5, 0.5, 0.5, 0.05
        )
        player.world.playSound(player.location, Sound.ENTITY_PLAYER_BURP, 1.0f, 1.5f)

        val direction = player.location.direction.clone().normalize()
        val dashDistance = 5.0
        val velocity = direction.multiply(dashDistance / 0.5)
        velocity.y = 0.3
        player.velocity = velocity

//        player.sendMessage(Component.text("§aYou dashed forward!"))
    }
}