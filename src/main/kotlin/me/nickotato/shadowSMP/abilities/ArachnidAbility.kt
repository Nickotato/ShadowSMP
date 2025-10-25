package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.utils.BlockUtils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class ArachnidAbility: Ability(60) {
    override fun execute(player: Player) {
        val centerBlock = player.location.block
        val nearbyBlocks = BlockUtils.getNearbyBlocks(centerBlock, 2)

        if (centerBlock.type == Material.AIR) centerBlock.type = Material.COBWEB

        for (block in nearbyBlocks) {
            if (block.type == Material.AIR) {
                block.type = Material.COBWEB
            }
        }

        for (target in Bukkit.getOnlinePlayers()) {
            val isInvisible = target.hasPotionEffect(PotionEffectType.INVISIBILITY)
            if (isInvisible) {
                player.showPlayer(ShadowSMP.instance, target)
            }
        }

        object : BukkitRunnable() {
            override fun run() {
                for (target in Bukkit.getOnlinePlayers()) {
                    val isInvisible = target.hasPotionEffect(PotionEffectType.INVISIBILITY)
                    if (isInvisible) {
                        player.hidePlayer(ShadowSMP.instance, target)
                    }
                }
            }
        }.runTaskLater(ShadowSMP.instance, 20L * 60)
    }
}