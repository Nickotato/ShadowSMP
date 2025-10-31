package me.nickotato.shadowSMP.abilities.titan

import me.nickotato.shadowSMP.abilities.Ability
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class TitanAbility: Ability(90) {
    override fun execute(player: Player) {
        player.addPotionEffect(PotionEffect(PotionEffectType.RESISTANCE, 5 * 20, 2))

        player.world.spawnParticle(Particle.WAX_ON, player.location.clone(), 200, 1.0, 3.0, 1.0)
    }
}