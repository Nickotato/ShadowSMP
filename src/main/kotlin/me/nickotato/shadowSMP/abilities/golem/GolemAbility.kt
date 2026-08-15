package me.nickotato.shadowSMP.abilities.golem

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.LivingEntity
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.cos
import kotlin.math.sin

@Suppress("SameParameterValue")
class GolemAbility: Ability(60) {


    override fun execute(context: AbilityContext) {
        beginTask(context)
        applyResistance(context)
        playSound(context)
    }

    private fun beginTask(context: AbilityContext) {
        val radius = 10.0
        val nearbyEntities = context.world
            .getNearbyEntities(context.location, radius, radius, radius)
            .filterIsInstance<LivingEntity>()

        object : BukkitRunnable() {
            var t = 0
            override fun run() {
                if (t>= 40) {
                    cancel()
                    return
                }

                for (entity in nearbyEntities) {
                    if (entity == context.caster) continue
                    applySlowness(entity)
                    pullEntityTowardPlayer(context, entity)
                }

                playParticles(context, radius)

                t++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }

    private fun playParticles(context: AbilityContext, radius: Double) {
        val world = context.world
        val center = context.location.clone().add(0.0, 1.0, 0.0)
        val particleCount = 100

        for (i in 0 until particleCount) {
            val angle = 2 * Math.PI * i / particleCount
            val x = radius * cos(angle)
            val z = radius * sin(angle)
            val loc = center.clone().add(x, 0.0, z)

            world.spawnParticle(Particle.CLOUD, loc, 0, 0.0, 0.0, 0.0, 0.0)
        }
    }

    private fun playSound(context: AbilityContext) {
        context.world.playSound(context.location, Sound.BLOCK_ANVIL_BREAK, 1f, 1f)
    }

    private fun applyResistance(context: AbilityContext) {
        val resistance = PotionEffect(PotionEffectType.RESISTANCE, 10, 1)
        context.addPotionEffect(resistance)
    }

    private fun applySlowness(entity: LivingEntity) {
        val slowness = PotionEffect(PotionEffectType.SLOWNESS, 10 * 20, 7)
        entity.addPotionEffect(slowness)
    }

    private fun pullEntityTowardPlayer(context: AbilityContext, entity: LivingEntity) {
        val direction = context.location.toVector()
            .subtract(entity.location.toVector())
            .normalize()

        val distance = context.location.distance(entity.location)
        val strength = (0.25 - (distance / 40.0)).coerceIn(0.02, 0.12)

        entity.velocity = entity.velocity.add(direction.multiply(strength))
    }
}