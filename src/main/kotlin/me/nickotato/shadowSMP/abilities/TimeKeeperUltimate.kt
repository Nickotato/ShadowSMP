package me.nickotato.shadowSMP.abilities

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.sin

class TimeKeeperUltimate : Ability(120) {

    override fun execute(player: Player) {
        val location = player.location
        val plugin = ShadowSMP.instance

        // Play initial sounds
        player.world.playSound(location, "block.bell.resonate", 2f, -1.5f)
        player.world.playSound(location, "item.trident.throw", 4f, -10f)

        // Run the particle + sound loop asynchronously
        object : BukkitRunnable() {
            var loopCount = 0
            val totalLoops = 15

            override fun run() {
                if (loopCount >= totalLoops) {
                    // After loops, apply tick changes
                    applyTickChanges(location, player)
                    cancel()
                    return
                }

                val r = loopCount * 0.5

                // Play incremental sounds
                location.world?.playSound(location, "block.glass.break", 0.5f, -2f + loopCount)
                location.world?.playSound(location, "item.trident.throw", 0.3f, -3f + loopCount)
                location.world?.playSound(location, "item.trident.riptide_1", 0.3f, -3f + loopCount / 3f)

                // Spawn circular particles
                for (i in 0 until 36) {
                    val yaw = i * 10.0
                    val x = r * cos(toRadians(yaw))
                    val z = r * sin(toRadians(yaw))
                    val particleLocation = location.clone().add(x, 0.0, z)

                    // End rod particles
                    location.world?.spawnParticle(
                        Particle.END_ROD, particleLocation, 15,
                        0.3, 0.3, 0.3, 0.1
                    )

                    // Dust color transition (approximation using DUST_COLOR_TRANSITION)
                    location.world?.spawnParticle(
                        Particle.DUST_COLOR_TRANSITION, particleLocation, 8,
                        0.3, 0.3, 0.3, 0.1,
                        Particle.DustTransition(Color.fromRGB(150, 0, 10),
                            Color.fromRGB(255, 25, 50), 2f)
                    )
                }

                loopCount++
            }
        }.runTaskTimer(plugin, 0L, 2L) // 2 ticks per loop
    }

    private fun applyTickChanges(location: Location, player: Player) {
        val nearbyPlayers = location.world?.getNearbyPlayers(location, 7.0) ?: return

        for (p in nearbyPlayers) {
            if (p != player) {
                temporaryTickChanger(p, 2f)
            } else {
                temporaryTickChanger(p, 4f)
            }
        }
    }

    private fun tickChanger(player: Player, tps: Float, frozen: Boolean = false) {
        try {
            val packet = PacketContainer(PacketType.Play.Server.TICKING_STATE)
            packet.float.write(0, tps)
            packet.booleans.write(0, frozen)
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet)
        } catch (e: Exception) {
            ShadowSMP.instance.logger.warning("TickChanger error: ${e.message}")
        }
    }


    private fun temporaryTickChanger(player: Player, tps: Float, frozen: Boolean = false) {
        tickChanger(player, tps, frozen)

        // Reset after 10 seconds (200 ticks)
        object : BukkitRunnable() {
            override fun run() {
                tickChanger(player, 20f, false)
            }
        }.runTaskLater(ShadowSMP.instance, 200L)
    }
}
