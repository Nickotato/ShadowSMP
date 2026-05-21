package me.nickotato.shadowSMP.enums

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilities.arachnid.ArachnidAbility
import me.nickotato.shadowSMP.abilities.arachnid.ArachnidUltimate
import me.nickotato.shadowSMP.abilities.banshee.BansheeAbility
import me.nickotato.shadowSMP.abilities.banshee.BansheeUltimate
import me.nickotato.shadowSMP.abilities.golem.GolemAbility
import me.nickotato.shadowSMP.abilities.golem.GolemUltimate
import me.nickotato.shadowSMP.abilities.jinn.JinnAbility
import me.nickotato.shadowSMP.abilities.jinn.JinnUltimate
import me.nickotato.shadowSMP.abilities.oni.OniAbility
import me.nickotato.shadowSMP.abilities.oni.OniUltimate
import me.nickotato.shadowSMP.abilities.revenant.RevenantAbility
import me.nickotato.shadowSMP.abilities.revenant.RevenantUltimate
import me.nickotato.shadowSMP.abilities.spirit.SpiritAbility
import me.nickotato.shadowSMP.abilities.spirit.SpiritUltimate
import me.nickotato.shadowSMP.abilities.timekeeper.TimeKeeperAbility
import me.nickotato.shadowSMP.abilities.timekeeper.TimeKeeperUltimate
import me.nickotato.shadowSMP.abilities.chronomancer.ChronomancerAbility
import me.nickotato.shadowSMP.abilities.chronomancer.ChronomancerUltimate
import me.nickotato.shadowSMP.abilities.deogen.DeogenAbility
import me.nickotato.shadowSMP.abilities.deogen.DeogenUltimate
import me.nickotato.shadowSMP.abilities.god.GodAbility
import me.nickotato.shadowSMP.abilities.god.GodUltimate
import me.nickotato.shadowSMP.abilities.ignis.IgnisAbility
import me.nickotato.shadowSMP.abilities.ignis.IgnisUltimate
import me.nickotato.shadowSMP.abilities.reaper.ReaperAbility
import me.nickotato.shadowSMP.abilities.reaper.ReaperUltimate
import me.nickotato.shadowSMP.abilities.spriggan.SprigganAbility
import me.nickotato.shadowSMP.abilities.spriggan.SprigganUltimate
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
    CHRONOMANCER(ChronomancerAbility(), ChronomancerUltimate()),
    GOD(GodAbility(), GodUltimate()),
    DEOGEN(DeogenAbility(), DeogenUltimate()),

    SPRIGGAN(SprigganAbility(), SprigganUltimate()),
}
// NEED TO CHECK FOR PLAYERS LEAVING THE GAME WITH SPECIAL EFFECTS / ABILITIES. Like Spectator or Invincibility.

//    Blight
//    Passive: Immune to poison and wither
//    Ability: Spread corruption that deals wither 1 to enemies standing on it for 10 seconds
//    Ultimate: Infect all players in a 15-block radius with wither 3 and blindness 5 for 5 seconds

//WRAITH
//Passive:
//Ability:
//Ultimate:

// MAYBE WARDEN FOR AN EVENT GHOST

// Extra:
//Passive: Gain 10% life steal on melee hits.
// GIANT SPIRAL IN AN AREA COVERING AN ENTIRE ARENA.

//Finished:

//Reaper
//Passive: Glow black all the time
//Ultimate: do 1 hit that deals true damage that bypasses armor.
//Ability: Instantly teleport behind the nearest enemy and deal 25% of their max HP as damage.

//Titan
//Passive: Permanent knockback resistance.
//Ability: Gain Resistance 3 for 5 seconds.
//Ultimate: Slam the ground to create shock waves that deal damage and send players outward.

//CHRONOMANCER
//Passive: Charm Cooldowns reduced by 10%
//Ability: Rewind your last 5 seconds of movement
//Ultimate: Hit People 3 times according to held item.

//Ignis
//Passive: Fire heals you instead of damaging.
//Ability: Summon a wall of fire that lingers for 10 seconds.
//Ultimate: Fire tornado that pulls entities in and lights them on fire.

//Deogen
//Passive: Speed 1 always
//Ability: Dash forward very far and gain temp speed 4
//Ultimate: Teleport around the closest entity and combo them into the air