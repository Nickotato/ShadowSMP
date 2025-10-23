package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin

class BansheeAbility: Ability(90) {
    override fun execute(player: Player) {
        val strength = PotionEffect(PotionEffectType.STRENGTH, 10 * 20, 1)
        val saturation = PotionEffect(PotionEffectType.SATURATION, 10 * 20, 1)
        player.addPotionEffect(strength)
        player.addPotionEffect(saturation)

        val world = player.world
        world.playSound(player.location, Sound.ENTITY_WITHER_SHOOT, 1.5f, 1.3f)
        world.playSound(player.location, Sound.ENTITY_GHAST_WARN, 0.8f, 0.7f)
        world.playSound(player.location, Sound.ENTITY_ENDER_DRAGON_GROWL, 1.8f, -3.0f)

        object : BukkitRunnable() {
            var tick = 0
            override fun run() {
                if (tick >= 20 * 10) {
                    cancel()
                    return
                }
                mainParticles(player, tick)
                tick++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }

    fun mainParticles(player: Player, tick: Int) {
        val location = player.location.clone().add(0.0, 1.0, 0.0) // adjust height if needed

        // Outer loop radius
        val radiusOuter = tick / 4.0
        val yawOuter = Math.toRadians(tick * 50.0)

        val xOuter = cos(yawOuter) * radiusOuter
        val zOuter = sin(yawOuter) * radiusOuter
        val offsetOuter = Vector(xOuter, 0.0, zOuter)

        // Dust transition: red -> black
        val dustRedBlack = Particle.DustOptions(Color.fromRGB(255, 0, 0), 2f) // approximate
        player.world.spawnParticle(Particle.DUST, location.clone().add(offsetOuter), 40, 0.1, 0.2, 0.1, dustRedBlack)

        // End rod & soul
        player.world.spawnParticle(Particle.END_ROD, location.clone().add(offsetOuter), 50, 0.3, 0.3, 0.3)
        player.world.spawnParticle(Particle.SOUL, location.clone().add(offsetOuter), 10, 0.2, 0.8, 0.2)

        // Inner loop radius
        val radiusInner = tick / 8.0
        val yawInner = Math.toRadians(tick * 80.0)

        val xInner = cos(yawInner) * radiusInner
        val zInner = sin(yawInner) * radiusInner
        val offsetInner = Vector(xInner, 0.0, zInner)

        // Dust transition: blue -> red
        val dustBlueRed = Particle.DustOptions(Color.fromRGB(0, 0, 255), 2f) // approximate
        player.world.spawnParticle(Particle.DUST, location.clone().add(offsetInner), 20, 0.1, 0.2, 0.1, dustBlueRed)

        // End rod & soul
        player.world.spawnParticle(Particle.END_ROD, location.clone().add(offsetInner), 50, 0.3, 0.3, 0.3)
        player.world.spawnParticle(Particle.SOUL, location.clone().add(offsetInner), 10, 0.2, 0.8, 0.2)

        // Innermost spherical particles
        for (i in 0 until 36) {
            val yaw = Math.toRadians(i * 20.0)
            val offsetSphere = Vector(cos(yaw) * 7, 0.0, sin(yaw) * 7)
            val dustBlackRed = Particle.DustOptions(Color.fromRGB(0, 0, 0), 2f)
            player.world.spawnParticle(Particle.DUST, location.clone().add(offsetSphere), 1, 0.3, 0.3, 0.3, dustBlackRed)

            val tinyOffset = Vector(cos(yaw) * 0.5, 0.0, sin(yaw) * 0.5)
            player.world.spawnParticle(Particle.END_ROD, location.clone().add(tinyOffset), 0)
        }
    }
}