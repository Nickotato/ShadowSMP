package me.nickotato.shadowSMP.manager

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.enums.Ghost
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

object EffectManager {

    private data class EffectData(val type: PotionEffectType, val amplifier: Int)

    fun applyEffect(player: Player, type: PotionEffectType, amplifier: Int) {
        player.addPotionEffect(PotionEffect(type, 20 * 60, amplifier))
    }

    private fun shouldHaveEffect(player: Player): List<EffectData> {
        val effect = mutableListOf<EffectData>()
        val playerData = PlayerManager.getPlayerData(player)

        if (playerData.ghost == Ghost.BANSHEE) effect.add(EffectData(PotionEffectType.STRENGTH, 0))
        if (playerData.ghost == Ghost.GOLEM) effect.add(EffectData(PotionEffectType.RESISTANCE, 0))

        return effect
    }

    fun startEffectLoop() {
        object : BukkitRunnable() {
            override fun run() {
                for (player in Bukkit.getOnlinePlayers()) {
                    val effects = shouldHaveEffect(player)

                    for (effect in effects) {
                        applyEffect(player, effect.type, effect.amplifier)
                    }
                }
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 20L * 30)
    }
}