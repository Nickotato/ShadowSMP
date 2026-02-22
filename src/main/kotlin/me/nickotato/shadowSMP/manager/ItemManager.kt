package me.nickotato.shadowSMP.manager

import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
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
                applySpookyObsidianGlow()
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 20L)
    }

    fun applySpookyObsidianGlow() {
        for (player in Bukkit.getOnlinePlayers()) {
            if (hasSpookyObsidian(player)) {
                if (!player.hasPotionEffect(PotionEffectType.GLOWING)) {
                    player.addPotionEffect(
                        PotionEffect(PotionEffectType.GLOWING, 30 * 20, 0, false, false)
                    )
                }
            }
        }
    }


    fun hasSpookyObsidian(player: Player): Boolean {
        val key = NamespacedKey(ShadowSMP.instance, "spooky_obsidian")

        return player.inventory.contents.any { item ->
            item?.itemMeta?.persistentDataContainer?.has(key, PersistentDataType.BYTE) == true
        }
    }

}