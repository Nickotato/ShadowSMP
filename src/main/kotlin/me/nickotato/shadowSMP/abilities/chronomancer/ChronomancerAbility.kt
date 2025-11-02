package me.nickotato.shadowSMP.abilities.chronomancer

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.manager.AbilityManager
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.Location
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI

class ChronomancerAbility: Ability(80) {

    override fun execute(player: Player) {
        if (!player.isOnline) return

        val deque = AbilityManager.locationHistory[player.uniqueId]
        if (deque == null || deque.size < 30) {
            player.sendMessage("§cNo where to teleport to...")
            return
        }

        val start = player.location
        val target = deque.last()

        // Starting sound and particles
        playStartEffects(start)

        // Teleport the player
        player.teleport(target)

        // Ending sound and particles
        playEndEffects(target)

        // Particle trail from start to end
        spawnTrail(start, target)
    }

    private fun playStartEffects(loc: Location) {
        val world = loc.world ?: return
        world.playSound(loc, Sound.BLOCK_BEACON_ACTIVATE, 1.5f, 0.8f)
        world.playSound(loc, Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1f, 0.7f)

        spawnSpiral(loc, Particle.SOUL_FIRE_FLAME, 1.8, 2.2, 25)
        spawnSpiral(loc, Particle.END_ROD, 2.0, 2.0, 30)
    }

    private fun playEndEffects(loc: Location) {
        val world = loc.world ?: return
        world.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1.2f, 1f)
        world.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.5f)
        world.playSound(loc, Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1f, 1.2f)

        spawnBurst(loc)
    }

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

    private fun spawnBurst(center: Location) {
        val world = center.world ?: return
        val dust1 = Particle.DustOptions(Color.fromRGB(100, 149, 237), 2f) // Cornflower blue
        val dust2 = Particle.DustOptions(Color.fromRGB(147, 112, 219), 2f) // Medium purple

        // Big burst
        world.spawnParticle(Particle.DUST, center, 60, 1.5, 1.5, 1.5, dust1)
        world.spawnParticle(Particle.DUST, center, 60, 1.5, 1.5, 1.5, dust2)

        // Magical sparkles
        world.spawnParticle(Particle.WITCH, center, 120, 1.0, 1.0, 1.0, 0.05)
        world.spawnParticle(Particle.SOUL_FIRE_FLAME, center, 70, 0.5, 1.0, 0.5, 0.05)
        world.spawnParticle(Particle.END_ROD, center, 100, 0.7, 1.0, 0.7, 0.02)
    }

    private fun spawnTrail(start: Location, end: Location) {
        val world = start.world ?: return
        val steps = 40
        val dx = (end.x - start.x) / steps
        val dy = (end.y - start.y) / steps
        val dz = (end.z - start.z) / steps

        for (i in 0..steps) {
            val x = start.x + dx * i
            val y = start.y + dy * i
            val z = start.z + dz * i

            val loc = Location(world, x, y, z)
            world.spawnParticle(Particle.SOUL_FIRE_FLAME, loc, 1, 0.1, 0.1, 0.1, 0.02)
            world.spawnParticle(Particle.END_ROD, loc, 1, 0.05, 0.05, 0.05, 0.01)
            if (i % 5 == 0) {
                world.spawnParticle(Particle.WITCH, loc, 1, 0.2, 0.2, 0.2, 0.01)
            }
        }
    }
}
