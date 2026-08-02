package me.nickotato.shadowSMP.abilities.charms

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.UUID

class ApollosReachAbility : Ability(120) {

    private val originalEntityRange = mutableMapOf<UUID, Double>()
    private val originalBlockRange = mutableMapOf<UUID, Double>()

    override fun execute(player: Player) {
        val uuid = player.uniqueId

        val entityAttr = player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE) ?: return
        val blockAttr = player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE) ?: return

        originalEntityRange.putIfAbsent(uuid, entityAttr.baseValue)
        originalBlockRange.putIfAbsent(uuid, blockAttr.baseValue)

        entityAttr.baseValue = 6.0
        blockAttr.baseValue = 6.0

        startParticles(player)

        object : BukkitRunnable() {
            override fun run() {
                val p = Bukkit.getPlayer(uuid) ?: return

                val e = p.getAttribute(Attribute.ENTITY_INTERACTION_RANGE)
                val b = p.getAttribute(Attribute.BLOCK_INTERACTION_RANGE)

                if (e != null && b != null) {
                    e.baseValue = originalEntityRange[uuid] ?: e.defaultValue
                    b.baseValue = originalBlockRange[uuid] ?: b.defaultValue
                }

                originalEntityRange.remove(uuid)
                originalBlockRange.remove(uuid)
            }
        }.runTaskLater(ShadowSMP.instance, 20L * 30)
    }

    private fun startParticles(player: Player) {
        object : BukkitRunnable() {
            var ticks = 0

            override fun run() {
                if (ticks++ > 40 || !player.isOnline) {
                    cancel()
                    return
                }

                val loc = player.location.add(0.0, 1.0, 0.0)

                player.world.spawnParticle(
                    Particle.END_ROD,
                    loc,
                    6,
                    0.35, 0.5, 0.35,
                    0.02
                )

                player.world.spawnParticle(
                    Particle.PORTAL,
                    loc,
                    10,
                    0.4, 0.6, 0.4,
                    0.05
                )
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 2L)
    }
}