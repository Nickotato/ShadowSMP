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
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class ConsecutiveHitListener: Listener {
    private val hitMap = ConcurrentHashMap<UUID, MutableMap<UUID, Int>>()

    @EventHandler
    fun onEntityHit(event: EntityDamageByEntityEvent) {
        val damager = event.damager
        val entity = event.entity

        if (damager !is Player) return
        if (PlayerManager.getPlayerData(damager).ghost != Ghost.ARACHNID) return
        if (entity !is LivingEntity) return

        val playerId = damager.uniqueId
        val entityId = entity.uniqueId

        val entityHits = hitMap.computeIfAbsent(playerId) { mutableMapOf() }
        val hits = entityHits.getOrDefault(entityId, 0) + 1
        entityHits[entityId] = hits

        if (hits >= 5) {
            entity.addPotionEffect(PotionEffect(PotionEffectType.POISON, 100, 1))
            entityHits[entityId] = 0
        }

        // Clean up dead entities
        entityHits.keys.removeIf { Bukkit.getEntity(it)?.isDead != false }
    }
}