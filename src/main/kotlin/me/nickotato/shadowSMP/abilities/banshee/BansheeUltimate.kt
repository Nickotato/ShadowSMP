package me.nickotato.shadowSMP.abilities.banshee

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class BansheeUltimate: Ability(60) {
    override fun execute(context: AbilityContext) {
        val nearbyPlayers = context.world.getNearbyPlayers(context.location, 1.5)
        val blindness = PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 6)
        val slowness = PotionEffect(PotionEffectType.SLOWNESS, 20*5, 6)

        for (nearbyPlayer in nearbyPlayers) {
            if (nearbyPlayer == context.caster) continue
            nearbyPlayer.addPotionEffect(blindness)
            nearbyPlayer.addPotionEffect(slowness)
        }

        playSoundEffects(context)
        playShadeParticles(context)
    }

    private fun playShadeParticles(context: AbilityContext) {
        val world = context.world
        val center = context.location.clone().add(0.0, 1.0, 0.0)

        val radius = 3.0
        val density = 250
        val blackDust = Particle.DustOptions(Color.fromRGB(71, 24, 24), 2f)
        val grayDust = Particle.DustOptions(Color.fromRGB(186, 181, 181), 2f)

        repeat(density) {
            val angle = Random.nextDouble(0.0, 2 * Math.PI)
            val r = Random.nextDouble(0.0, radius)
            val x = cos(angle) * r
            val z = sin(angle) * r
            val yOffset = Random.nextDouble(-0.4, 0.4)

            val loc = center.clone().add(x, yOffset, z)

            world.spawnParticle(Particle.DUST, loc, 1, 0.05, 0.05, 0.05, 0.0, blackDust)
            if (Random.nextDouble() < 0.5) {
                world.spawnParticle(Particle.DUST, loc, 1, 0.05, 0.05, 0.05, 0.0, grayDust)
            }
            if (Random.nextDouble() < 0.15) {
                world.spawnParticle(Particle.SMOKE, loc, 1, 0.1, 0.1, 0.1, 0.01)
            }
        }
    }

    private fun playSoundEffects(context: AbilityContext) {
        val world = context.world
        val loc = context.location

        world.playSound(loc, Sound.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 1.0f, 0.6f)

        world.playSound(loc, Sound.ENTITY_PHANTOM_FLAP, 1.5f, 0.5f)

        world.playSound(loc, Sound.ENTITY_GHAST_SCREAM, 1.0f, 1.2f)

        world.playSound(loc, Sound.BLOCK_BEACON_DEACTIVATE, 0.7f, 0.8f)

        for (nearby in world.getNearbyPlayers(loc, 10.0)) {
            if (nearby != context.caster) {
                nearby.playSound(loc, Sound.ENTITY_PHANTOM_AMBIENT, 0.7f, 0.6f)
            }
        }
    }

}