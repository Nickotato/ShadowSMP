package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.config.Settings
import me.nickotato.shadowSMP.enums.Charm
import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.manager.AbilityManager
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.Bukkit
import org.bukkit.damage.DamageSource
import org.bukkit.damage.DamageType
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.UUID

class PlayerFallListener: Listener {
    private val lastOnGround = mutableMapOf<UUID, Boolean>()

    @EventHandler
    fun onPlayerFall(event: EntityDamageEvent) {
        if (event.cause != EntityDamageEvent.DamageCause.FALL) return

        val entity = event.entity
        if (entity !is Player) return
        val data = PlayerManager.getPlayerData(entity)

        if (AbilityManager.tempNoFallPlayers.contains(entity.uniqueId)) {
            event.isCancelled = true
            AbilityManager.tempNoFallPlayers.remove(entity.uniqueId)
            return
        }

        if (data.ghost == Ghost.REVENANT || data.charm == Charm.FEATHER) {
            if (data.ghost == Ghost.REVENANT && data.charm != Charm.FEATHER && Settings.revenantCausesSlowness) {
                entity.addPotionEffect(PotionEffect(PotionEffectType.SLOWNESS, 20*5, 4))
            }
            event.isCancelled = true
            return
        }

    }

    @EventHandler
    fun checkRevenantMove(event: PlayerMoveEvent) {
        val player = event.player
        val playerAsEntity = player as Entity
        val uuid = player.uniqueId
        val data = PlayerManager.getPlayerData(player)

        val isRevenant = data.ghost == Ghost.REVENANT
        if (!isRevenant) return

        if (AbilityManager.tempNoFallPlayers.contains(uuid)) return

        val wasOnGround = lastOnGround[uuid] ?: playerAsEntity.isOnGround
        val isOnGround = playerAsEntity.isOnGround
        val fallDistance = player.fallDistance

        println("[FALL-DEBUG] wasOnGround=$wasOnGround isOnGround=$isOnGround fallDistance=$fallDistance")

        // update tracking FIRST
        lastOnGround[uuid] = isOnGround

        // landing detection
        if (!wasOnGround && isOnGround) {
            println("[FALL-DEBUG] LANDING DETECTED")

            if (fallDistance >= 6.0f) {
                println("[FALL-DEBUG] Triggering fake FALL event")

                val fallEvent = EntityDamageEvent(
                    player,
                    EntityDamageEvent.DamageCause.FALL,
                    DamageSource.builder(DamageType.FALL).build(),
                    0.0
                )

                Bukkit.getPluginManager().callEvent(fallEvent)
            } else {
                println("[FALL-DEBUG] Fall too small: $fallDistance")
            }
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        lastOnGround.remove(event.player.uniqueId)
    }
}