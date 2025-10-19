package me.nickotato.shadowSMP.enums

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilities.RevenantAbility

enum class Ghost(ability: Ability, ultimate: Ability) {
    REVENANT(RevenantAbility(), RevenantAbility())
}