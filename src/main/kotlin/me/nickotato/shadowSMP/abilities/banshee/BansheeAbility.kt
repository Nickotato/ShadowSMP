package me.nickotato.shadowSMP.abilities.banshee

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin

class BansheeAbility: Ability(90) {
    override fun execute(context: AbilityContext) {
        val strength = PotionEffect(PotionEffectType.STRENGTH, 10 * 20, 1)
        val saturation = PotionEffect(PotionEffectType.SATURATION, 10 * 20, 1)
        context.addPotionEffect(strength)
        context.addPotionEffect(saturation)

        val world = context.world
        world.playSound(context.location, Sound.ENTITY_WITHER_SHOOT, 1.5f, 1.3f)
        world.playSound(context.location, Sound.ENTITY_GHAST_WARN, 0.8f, 0.7f)
        world.playSound(context.location, Sound.ENTITY_ENDER_DRAGON_GROWL, 1.8f, -3.0f)

        object : BukkitRunnable() {
            var tick = 0
            override fun run() {
                if (tick >= 20 * 10) {
                    cancel()
                    return
                }
                mainParticles(context, tick)
                tick++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }

    fun mainParticles(context: AbilityContext, tick: Int) {
        val location = context.location.clone().add(0.0, 1.0, 0.0)

        val radiusOuter = tick / 4.0
        val yawOuter = Math.toRadians(tick * 50.0)

        val xOuter = cos(yawOuter) * radiusOuter
        val zOuter = sin(yawOuter) * radiusOuter
        val offsetOuter = Vector(xOuter, 0.0, zOuter)

        val dustRedBlack = Particle.DustOptions(Color.fromRGB(255, 0, 0), 2f) // approximate
        context.world.spawnParticle(Particle.DUST, location.clone().add(offsetOuter), 40, 0.1, 0.2, 0.1, dustRedBlack)

        context.world.spawnParticle(Particle.END_ROD, location.clone().add(offsetOuter), 2, 0.3, 0.3, 0.3) //2 End Rod Particles used to be 50 count instead of 2.
        context.world.spawnParticle(Particle.SOUL, location.clone().add(offsetOuter), 10, 0.2, 0.8, 0.2)

        val radiusInner = tick / 8.0
        val yawInner = Math.toRadians(tick * 80.0)

        val xInner = cos(yawInner) * radiusInner
        val zInner = sin(yawInner) * radiusInner
        val offsetInner = Vector(xInner, 0.0, zInner)

        val dustBlueRed = Particle.DustOptions(Color.fromRGB(0, 0, 255), 2f) // approximate
        context.world.spawnParticle(Particle.DUST, location.clone().add(offsetInner), 20, 0.1, 0.2, 0.1, dustBlueRed)

        context.world.spawnParticle(Particle.END_ROD, location.clone().add(offsetInner), 2, 0.3, 0.3, 0.3)
        context.world.spawnParticle(Particle.SOUL, location.clone().add(offsetInner), 10, 0.2, 0.8, 0.2)

        for (i in 0 until 36) {
            val yaw = Math.toRadians(i * 20.0)
            val offsetSphere = Vector(cos(yaw) * 7, 0.0, sin(yaw) * 7)
            val dustBlackRed = Particle.DustOptions(Color.fromRGB(0, 0, 0), 2f)
            context.world.spawnParticle(Particle.DUST, location.clone().add(offsetSphere), 1, 0.3, 0.3, 0.3, dustBlackRed)

            val tinyOffset = Vector(cos(yaw) * 0.5, 0.0, sin(yaw) * 0.5)
            context.world.spawnParticle(Particle.END_ROD, location.clone().add(tinyOffset), 0)
        }
    }
}