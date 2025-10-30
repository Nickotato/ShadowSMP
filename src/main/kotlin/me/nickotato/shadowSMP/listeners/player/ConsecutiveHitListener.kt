package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.Bukkit
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class ConsecutiveHitListener : Listener {
    private val hitMap = ConcurrentHashMap<UUID, MutableMap<UUID, Int>>()

    @EventHandler
    fun onEntityHit(event: EntityDamageByEntityEvent) {
        val damager = event.damager as? Player ?: return
        val entity = event.entity as? LivingEntity ?: return

        val ghostType = PlayerManager.getPlayerData(damager).ghost
        if (ghostType != Ghost.ARACHNID && ghostType != Ghost.SPIRIT) return

        val playerId = damager.uniqueId
        val entityId = entity.uniqueId
        val entityHits = hitMap.computeIfAbsent(playerId) { mutableMapOf() }

        val hits = entityHits.getOrDefault(entityId, 0) + 1
        entityHits[entityId] = hits

        when (ghostType) {
            Ghost.ARACHNID -> {
                if (hits >= 5) {
                    entity.addPotionEffect(PotionEffect(PotionEffectType.POISON, 100, 1))
                    entityHits[entityId] = 0
                }
            }
            Ghost.SPIRIT -> {
                if (hits >= 20) {
                    entity.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, 100, 0))
                    entityHits[entityId] = 0
                }
            }
            else -> {}
        }

        // Remove invalid or dead targets
        entityHits.keys.removeIf { Bukkit.getEntity(it)?.isDead != false }
    }
}
