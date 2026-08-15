package me.nickotato.shadowSMP.abilities.titan

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import org.bukkit.Particle
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class TitanAbility: Ability(90) {
    override fun execute(context: AbilityContext) {
        context.addPotionEffect(PotionEffect(PotionEffectType.RESISTANCE, 45 * 20, 2))

        context.world.spawnParticle(Particle.WAX_ON, context.location.clone(), 200, 1.0, 3.0, 1.0)
    }
}