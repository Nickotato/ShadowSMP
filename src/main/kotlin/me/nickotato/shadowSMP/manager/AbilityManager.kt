package me.nickotato.shadowSMP.manager

import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.scheduler.BukkitRunnable
import java.util.UUID

object AbilityManager {
    val invulnerablePlayers  = mutableSetOf<UUID>()
    val trueDamagePlayers = mutableSetOf<UUID>()
    val locationHistory = mutableMapOf<UUID, ArrayDeque<Location>>()
    val tempNoFallPlayers = mutableSetOf<UUID>()


    fun beginTrackingLocations() {
        object : BukkitRunnable() {
            override fun run() {
                for (player in Bukkit.getOnlinePlayers()) {
                    val deque = locationHistory.getOrPut(player.uniqueId) { ArrayDeque() }
                    deque.addFirst(player.location.clone())
                    if (deque.size > 30) deque.removeLast()
                }
            }
        }.runTaskTimer(ShadowSMP.instance, 20L, 20L )
    }
}