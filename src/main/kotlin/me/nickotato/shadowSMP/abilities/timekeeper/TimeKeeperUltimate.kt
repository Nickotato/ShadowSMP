package me.nickotato.shadowSMP.abilities.timekeeper

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
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

        player.world.playSound(location, "block.bell.resonate", 2f, -1.5f)
        player.world.playSound(location, "item.trident.throw", 4f, -10f)

        object : BukkitRunnable() {
            var loopCount = 0
            val totalLoops = 15

            override fun run() {
                if (loopCount >= totalLoops) {
                    applyTickChanges(location, player)
                    cancel()
                    return
                }

                val r = loopCount * 0.5

                location.world?.playSound(location, "block.glass.break", 0.5f, -2f + loopCount)
                location.world?.playSound(location, "item.trident.throw", 0.3f, -3f + loopCount)
                location.world?.playSound(location, "item.trident.riptide_1", 0.3f, -3f + loopCount / 3f)

                for (i in 0 until 36) {
                    val yaw = i * 10.0
                    val x = r * cos(toRadians(yaw))
                    val z = r * sin(toRadians(yaw))
                    val particleLocation = location.clone().add(x, 0.0, z)

                    location.world?.spawnParticle(
                        Particle.END_ROD, particleLocation, 15,
                        0.3, 0.3, 0.3, 0.1
                    )

                    location.world?.spawnParticle(
                        Particle.DUST_COLOR_TRANSITION, particleLocation, 8,
                        0.3, 0.3, 0.3, 0.1,
                        Particle.DustTransition(Color.fromRGB(150, 0, 10),
                            Color.fromRGB(255, 25, 50), 2f)
                    )
                }

                loopCount++
            }
        }.runTaskTimer(plugin, 0L, 2L)
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

        object : BukkitRunnable() {
            override fun run() {
                tickChanger(player, 20f, false)
            }
        }.runTaskLater(ShadowSMP.instance, 200L)
    }
}
