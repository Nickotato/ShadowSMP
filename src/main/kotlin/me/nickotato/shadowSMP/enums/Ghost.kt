package me.nickotato.shadowSMP.enums

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilities.ArachnidAbility
import me.nickotato.shadowSMP.abilities.ArachnidUltimate
import me.nickotato.shadowSMP.abilities.BansheeAbility
import me.nickotato.shadowSMP.abilities.BansheeUltimate
import me.nickotato.shadowSMP.abilities.GolemAbility
import me.nickotato.shadowSMP.abilities.GolemUltimate
import me.nickotato.shadowSMP.abilities.JinnAbility
import me.nickotato.shadowSMP.abilities.JinnUltimate
import me.nickotato.shadowSMP.abilities.OniAbility
import me.nickotato.shadowSMP.abilities.OniUltimate
import me.nickotato.shadowSMP.abilities.RevenantAbility
import me.nickotato.shadowSMP.abilities.RevenantUltimate
import me.nickotato.shadowSMP.abilities.SpiritAbility
import me.nickotato.shadowSMP.abilities.SpiritUltimate
import me.nickotato.shadowSMP.abilities.TimeKeeperAbility
import me.nickotato.shadowSMP.abilities.TimeKeeperUltimate
import me.nickotato.shadowSMP.abilities.ignis.IgnisAbility
import me.nickotato.shadowSMP.abilities.ignis.IgnisUltimate
import me.nickotato.shadowSMP.abilities.reaper.ReaperAbility
import me.nickotato.shadowSMP.abilities.reaper.ReaperUltimate
import me.nickotato.shadowSMP.abilities.titan.TitanAbility
import me.nickotato.shadowSMP.abilities.titan.TitanUltimate

enum class Ghost(val ability: Ability, val ultimate: Ability) {
    REVENANT(RevenantAbility(), RevenantUltimate()),
    BANSHEE(BansheeAbility(), BansheeUltimate()),
    GOLEM(GolemAbility(), GolemUltimate()),
    ARACHNID(ArachnidAbility(), ArachnidUltimate()),
    ONI(OniAbility(), OniUltimate()), //Event
    TIMEKEEPER(TimeKeeperAbility(), TimeKeeperUltimate()),
    SPIRIT(SpiritAbility(), SpiritUltimate()),
    JINN(JinnAbility(), JinnUltimate()), // Event
    REAPER(ReaperAbility(), ReaperUltimate()),
    TITAN(TitanAbility(), TitanUltimate()),
    IGNIS(IgnisAbility(), IgnisUltimate()),

}
// NEED TO CHECK FOR PLAYERS LEAVING THE GAME WITH SPECIAL EFFECTS / ABILITIES. Like Spectator or Invincibility.

//    CHRONOMANCER
//    Passive: Cooldowns reduced by 10%
//    Ability: Rewind your last 5 seconds of movement
//    Ultimate: Freeze all entities in a 10-block radius for 3 seconds

//    Blight
//    Passive: Immune to poison and wither
//    Ability: Spread corruption that deals wither 1 to enemies standing on it for 10 seconds
//    Ultimate: Infect all players in a 15-block radius with wither 3 and blindness 5 for 5 seconds

//  Zephyr / Deogen
//  Passive: Speed 1 always
//  Ability: Dash forward 10 blocks, dealing small damage to anything in path
//  Ultimate: Summon a windstorm that pushes all entities outward and upward

//Templar
//Passive: Permanent regeneration 1
//Ability: Creates a holy zone (5-block radius) that heals allies for 5 seconds
//Ultimate: Grants all nearby allies absorption 4 and resistance 2 for 10 seconds

// MAYBE WARDEN FOR AN EVENT GHOST

//Ignis
//Passive: Fire heals you instead of damaging.
//Ability: Summon a wall of fire that lingers for 10 seconds.
//Ultimate: Erupt, sending fireballs in all directions and igniting everything in range.

// Extra:
//Passive: Gain 10% life steal on melee hits.

//Finished:
//Reaper
//Passive: Glow black all the time
//Ultimate: do 1 hit that deals true damage that bypasses armor.
//Ability: Instantly teleport behind the nearest enemy and deal 25% of their max HP as damage.

//Titan
//Passive: Permanent knockback resistance.
//Ability: Gain Resistance 3 for 5 seconds.
//Ultimate: Slam the ground to create shock waves that deal damage and send players outward.