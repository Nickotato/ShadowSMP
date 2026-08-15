package me.nickotato.shadowSMP.abilities.mace

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import org.bukkit.Particle
import org.bukkit.Sound

object MaceAbility: Ability(15) {
    override fun execute(context: AbilityContext) {
        context.world.spawnParticle(
            Particle.CLOUD, context.location.add(0.0, 1.0, 0.0),
            10, 0.5, 0.5, 0.5, 0.05
        )
        context.world.playSound(context.location, Sound.ENTITY_PLAYER_BURP, 1.0f, 1.5f)

        val direction = context.location.direction.clone().normalize()
        val dashDistance = 5.0
        val velocity = direction.multiply(dashDistance / 0.5)
        velocity.y = 0.3
        context.caster.velocity = velocity

//        player.sendMessage(Component.text("§aYou dashed forward!"))
    }
}