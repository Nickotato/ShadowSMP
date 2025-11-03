package me.nickotato.shadowSMP.manager

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.enums.Charm
import me.nickotato.shadowSMP.enums.Ghost
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

object EffectManager {

    private data class EffectData(val type: PotionEffectType, val amplifier: Int)

    fun applyEffect(player: Player, type: PotionEffectType, amplifier: Int) {
        player.addPotionEffect(PotionEffect(type, 20 * 30, amplifier))
    }

    private fun shouldHaveEffect(player: Player): List<EffectData> {
        val effect = mutableListOf<EffectData>()
        val playerData = PlayerManager.getPlayerData(player)

        if (playerData.ghost == Ghost.BANSHEE) effect.add(EffectData(PotionEffectType.STRENGTH, 0))
        if (playerData.ghost == Ghost.GOLEM) effect.add(EffectData(PotionEffectType.RESISTANCE, 0))
        if (playerData.charm == Charm.ARES_BRACELET) effect.add(EffectData(PotionEffectType.STRENGTH, 0))
        if (playerData.ghost == Ghost.DEOGEN) effect.add(EffectData(PotionEffectType.SPEED, 0))

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

//                    applyReaperGlow(player)
//                    applyGodGlow(player)
                    applyGlow(player)
                }
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 20L * 3)
    }

    private fun applyGlow(player: Player) {
        val board = Bukkit.getScoreboardManager().mainScoreboard
        val playerData = PlayerManager.getPlayerData(player)

        val (teamName, color) = when (playerData.ghost) {
            Ghost.REAPER -> "glow_reaper" to NamedTextColor.BLACK
            Ghost.GOD -> "glow_god" to NamedTextColor.GOLD
            else -> null to null
        }

        // remove from both teams first
        listOf("glow_reaper", "glow_god").forEach { name ->
            board.getTeam(name)?.removeEntry(player.name)
        }

        if (teamName != null && color != null) {
            val team = board.getTeam(teamName) ?: board.registerNewTeam(teamName).apply {
                color(color)
            }
            if (!team.hasEntry(player.name)) team.addEntry(player.name)
            player.isGlowing = true
        } else {
            player.isGlowing = false
        }
    }

}

