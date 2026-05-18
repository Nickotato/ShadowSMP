package me.nickotato.shadowSMP.manager

import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

object ItemManager {
    data class CustomItemInfo (
        val type: String,
        val indestructible: Boolean = false,
        val unbreakable: Boolean = false,
    )

    private val items = mutableMapOf<String, CustomItemInfo>()

    fun register(item: CustomItemInfo) {
        items[item.type] = item
    }

    fun getInfo(type: String): CustomItemInfo? = items[type]

    fun isIndestructible(type: String): Boolean = items[type]?.indestructible == true

    fun startItemChecks() {
        object : BukkitRunnable() {
            override fun run() {
                for (player in Bukkit.getOnlinePlayers()) {
                    PlayerManager.updatePlayerMaxHP(player)
                }

            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 20L)
    }
}