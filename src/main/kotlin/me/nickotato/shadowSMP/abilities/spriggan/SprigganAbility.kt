package me.nickotato.shadowSMP.abilities.spriggan

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import me.nickotato.shadowSMP.manager.AbilityManager
import net.kyori.adventure.text.Component

class SprigganAbility : Ability(0) {
    override fun execute(context: AbilityContext) {

        if (AbilityManager.sprigganAbilityPlayers.add(context.caster.uniqueId)) {
            context.sendActionBar(Component.text("Spriggan ability: §aOn"))
        } else {
            AbilityManager.sprigganAbilityPlayers.remove(context.caster.uniqueId)
            context.sendActionBar(Component.text("Spriggan ability: §cOff"))
        }
    }
}