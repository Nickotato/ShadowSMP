package me.nickotato.shadowSMP.enums

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilities.RevenantAbility

enum class Ghost(val ability: Ability, val ultimate: Ability) {
    REVENANT(RevenantAbility(), RevenantAbility())
}