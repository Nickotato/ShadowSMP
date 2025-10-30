package me.nickotato.shadowSMP.enums

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilities.ArachnidAbility
import me.nickotato.shadowSMP.abilities.ArachnidUltimate
import me.nickotato.shadowSMP.abilities.BansheeAbility
import me.nickotato.shadowSMP.abilities.BansheeUltimate
import me.nickotato.shadowSMP.abilities.GolemAbility
import me.nickotato.shadowSMP.abilities.GolemUltimate
import me.nickotato.shadowSMP.abilities.OniAbility
import me.nickotato.shadowSMP.abilities.OniUltimate
import me.nickotato.shadowSMP.abilities.RevenantAbility
import me.nickotato.shadowSMP.abilities.RevenantUltimate
import me.nickotato.shadowSMP.abilities.TimeKeeperAbility
import me.nickotato.shadowSMP.abilities.TimeKeeperUltimate

enum class Ghost(val ability: Ability, val ultimate: Ability) {
    REVENANT(RevenantAbility(), RevenantUltimate()),
    BANSHEE(BansheeAbility(), BansheeUltimate()),
    GOLEM(GolemAbility(), GolemUltimate()),
    ARACHNID(ArachnidAbility(), ArachnidUltimate()),
    ONI(OniAbility(), OniUltimate()),
    TIMEKEEPER(TimeKeeperAbility(), TimeKeeperUltimate()),
    // Ultimate: Spectator, Normal: Immune to hits for time.
}