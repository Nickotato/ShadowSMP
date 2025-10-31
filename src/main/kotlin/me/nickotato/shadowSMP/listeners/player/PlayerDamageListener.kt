package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.manager.AbilityManager
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import kotlin.random.Random

class PlayerDamageListener: Listener {
    @EventHandler
    fun onEntityDamage(event: EntityDamageByEntityEvent) {
        val player = event.entity
        if (player !is Player) return
        val data = PlayerManager.getPlayerData(player)
        val damager = event.damager

        if (AbilityManager.invulnerablePlayers.contains(player.uniqueId)) {
            event.isCancelled = true
            // Play a sound
            if (damager !is Player) return
            damager.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 0.5f, 1f)
        }

        if (data.ghost == Ghost.JINN) {
            if (Random.nextDouble() <= 0.10) { // 0.10 = 10%
                event.isCancelled = true
                player.world.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 1f, 1f)
            }
        }

        if (damager is Player && AbilityManager.trueDamagePlayers.contains(damager.uniqueId)) {
            event.isCancelled = true

            val damage = damager.getAttribute(Attribute.ATTACK_DAMAGE)?.value ?: 5.0
            val targetHealth = player.health
            player.health = (targetHealth - damage).coerceAtLeast(0.0)

            player.world.spawnParticle(Particle.CRIT, player.location, 15, 0.5, 0.5, 0.5, 0.05)
            player.world.playSound(player.location, Sound.ENTITY_PLAYER_ATTACK_CRIT, 1f, 1f)

            AbilityManager.trueDamagePlayers.remove(damager.uniqueId)
        }
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        val player = event.entity
        if (player !is  Player) return
        val data = PlayerManager.getPlayerData(player)

        if (data.ghost == Ghost.IGNIS) {
            if (
                event.cause == EntityDamageEvent.DamageCause.FIRE ||
                event.cause == EntityDamageEvent.DamageCause.FIRE_TICK ||
                event.cause == EntityDamageEvent.DamageCause.LAVA
            ) {
                event.isCancelled = true
                player.heal(0.05)
            }
        }
    }
}