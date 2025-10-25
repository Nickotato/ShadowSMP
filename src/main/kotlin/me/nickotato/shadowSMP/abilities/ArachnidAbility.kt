package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.utils.BlockUtils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class ArachnidAbility: Ability(60) {
    override fun execute(player: Player) {
        val centerBlock = player.location.block
        val nearbyBlocks = BlockUtils.getNearbyBlocks(centerBlock, 2)

        // Web particle effect at center
        player.world.spawnParticle(Particle.CLOUD, centerBlock.location.add(0.5, 0.5, 0.5), 30, 0.5, 1.0, 0.5, 0.02)
        player.world.playSound(player.location, Sound.ENTITY_SPIDER_AMBIENT, 1.0f, 1.0f)

        if (centerBlock.type == Material.AIR) centerBlock.type = Material.COBWEB

        for (block in nearbyBlocks) {
            if (block.type == Material.AIR) {
                block.type = Material.COBWEB
                player.world.spawnParticle(Particle.CLOUD, block.location.add(0.5, 0.5, 0.5), 20, 0.3, 0.5, 0.3, 0.02)
            }
        }

        val invisiblePlayers = mutableSetOf<Player>()
        for (target in Bukkit.getOnlinePlayers()) {
            if (target.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                player.showPlayer(ShadowSMP.instance, target)
                target.isGlowing = true
                invisiblePlayers.add(target)
            }
        }

        object : BukkitRunnable() {
            override fun run() {
                val iterator = invisiblePlayers.iterator()
                while (iterator.hasNext()) {
                    val target = iterator.next()
                    if (!target.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                        // Notify player visually & with a sound
                        player.world.spawnParticle(Particle.FLASH, target.location.add(0.0, 1.0, 0.0), 10, 0.3, 0.3, 0.3, 0.1)
                        player.world.playSound(target.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f)

                        target.isGlowing = false
                        player.hidePlayer(ShadowSMP.instance, target)
                        iterator.remove()
                    }
                }

                if (invisiblePlayers.isEmpty()) cancel()
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 10L)

        object : BukkitRunnable() {
            override fun run() {
                for (target in invisiblePlayers) {
                    target.isGlowing = false
                    player.hidePlayer(ShadowSMP.instance, target)
                }
                invisiblePlayers.clear()
            }
        }.runTaskLater(ShadowSMP.instance, 20L * 60)
    }
}