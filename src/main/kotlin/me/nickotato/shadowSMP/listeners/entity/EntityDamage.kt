package me.nickotato.shadowSMP.listeners.entity

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.enums.Charm
import me.nickotato.shadowSMP.manager.PlayerManager
import net.kyori.adventure.text.Component
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.floor

class EntityDamage: Listener {
    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        val victim = event.entity
        val attacker = event.damager

        if (attacker is Player) {
            val attackerData = PlayerManager.getPlayerData(attacker)
            if (attackerData.charm == Charm.VITAL_BAND && victim is LivingEntity) {
                object : BukkitRunnable() {
                    override fun run() {
                        attacker.sendActionBar(Component.text("§c${floor(victim.health)} / ${victim.getAttribute(Attribute.MAX_HEALTH)?.value ?: "§7Unknown"}"))
                    }
                }.runTaskLater(ShadowSMP.instance, 1L)

            }
        }
    }
}