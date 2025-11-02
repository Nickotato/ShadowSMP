@file:Suppress("SameParameterValue")

package me.nickotato.shadowSMP.abilities.chronomancer

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class ChronomancerUltimate : Ability(120) { // 5 min cooldown
    override fun execute(player: Player) {
        if (!player.isOnline) return

        val world = player.world
        val center = player.location
        val radius = 5.0
        val hits = 3
        val delayBetweenHits = 5L // ticks (0.25 sec per echo)

        // Get weapon in main hand
        val weapon = player.inventory.itemInMainHand

        // Calculate base weapon damage based on type
        val weaponDamage = when (weapon.type) {
            Material.WOODEN_SWORD -> 4.0
            Material.STONE_SWORD -> 5.0
            Material.IRON_SWORD -> 6.0
            Material.DIAMOND_SWORD -> 7.0
            Material.NETHERITE_SWORD -> 8.0
            Material.WOODEN_AXE -> 7.0
            Material.STONE_AXE -> 9.0
            Material.IRON_AXE -> 9.0
            Material.DIAMOND_AXE -> 9.0
            Material.NETHERITE_AXE -> 10.0
            else -> player.getAttribute(Attribute.ATTACK_DAMAGE)?.baseValue ?: 1.0
        }

        // Add Sharpness bonus
        val sharpnessLevel = weapon.getEnchantmentLevel(Enchantment.SHARPNESS)
        val totalDamage = weaponDamage + sharpnessLevel * 1.25

        // Show AoE pulse for players
        spawnAoEPulse(center, radius, 3.0, 40)

        // Start initial visual effects
        spawnSpiral(center, Particle.SOUL_FIRE_FLAME, 2.0, 2.0, 25)
        spawnSpiral(center, Particle.END_ROD, 2.0, 2.0, 25)
        world.playSound(center, Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1.5f, 1.0f)

        // Schedule repeated hits
        object : BukkitRunnable() {
            var count = 0
            override fun run() {
                if (count >= hits) {
                    cancel()
                    return
                }

                val targets = world.getNearbyEntities(center, radius, radius, radius)
                    .filterIsInstance<LivingEntity>()
                    .filter { it != player }

                for (target in targets) {
                    target.damage(totalDamage, player)
                    spawnEchoParticles(target.location)
                    world.playSound(target.location, Sound.ENTITY_PLAYER_HURT, 1.0f, 1.2f)
                }

                count++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, delayBetweenHits)
    }


    // Particle spirals for start
    private fun spawnSpiral(center: Location, particle: Particle, radius: Double, height: Double, steps: Int) {
        val world = center.world ?: return
        for (i in 0 until steps) {
            val angle = 2 * PI * i / steps
            val x = center.x + cos(angle) * radius
            val y = center.y + (i.toDouble() / steps) * height
            val z = center.z + sin(angle) * radius
            world.spawnParticle(particle, x, y, z, 1, 0.0, 0.0, 0.0, 0.0)
        }
    }

    // Particles on each echo hit
    private fun spawnEchoParticles(location: Location) {
        val world = location.world ?: return
        val dust1 = Particle.DustOptions(Color.fromRGB(100, 149, 237), 1.5f)
        val dust2 = Particle.DustOptions(Color.fromRGB(147, 112, 219), 1.5f)

        world.spawnParticle(Particle.DUST, location, 20, 0.5, 1.0, 0.5, dust1)
        world.spawnParticle(Particle.DUST, location, 20, 0.5, 1.0, 0.5, dust2)
        world.spawnParticle(Particle.SOUL_FIRE_FLAME, location, 10, 0.3, 0.7, 0.3, 0.02)
        world.spawnParticle(Particle.END_ROD, location, 10, 0.2, 0.5, 0.2, 0.01)
    }

    // Show 3D AoE pulse
    private fun spawnAoEPulse(center: Location, radius: Double, height: Double, steps: Int) {
        val world = center.world ?: return
        for (hStep in 0..height.toInt()) {
            val y = center.y + hStep
            for (i in 0 until steps) {
                val angle = 2 * PI * i / steps
                val x = center.x + cos(angle) * radius
                val z = center.z + sin(angle) * radius
                world.spawnParticle(Particle.END_ROD, x, y, z, 1, 0.0, 0.0, 0.0, 0.0)
                world.spawnParticle(Particle.SOUL_FIRE_FLAME, x, y, z, 1, 0.05, 0.05, 0.05, 0.02)
            }
        }
    }
}
