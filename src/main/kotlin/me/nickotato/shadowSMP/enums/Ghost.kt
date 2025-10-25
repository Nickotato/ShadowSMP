package me.nickotato.shadowSMP.enums

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilities.ArachnidAbility
import me.nickotato.shadowSMP.abilities.BansheeAbility
import me.nickotato.shadowSMP.abilities.BansheeUltimate
import me.nickotato.shadowSMP.abilities.GolemAbility
import me.nickotato.shadowSMP.abilities.GolemUltimate
import me.nickotato.shadowSMP.abilities.RevenantAbility
import me.nickotato.shadowSMP.abilities.RevenantUltimate

enum class Ghost(val ability: Ability, val ultimate: Ability) {
    REVENANT(RevenantAbility(), RevenantUltimate()),
    BANSHEE(BansheeAbility(), BansheeUltimate()),
    GOLEM(GolemAbility(), GolemUltimate()),
    ARACHNID(ArachnidAbility(), RevenantAbility()),
}