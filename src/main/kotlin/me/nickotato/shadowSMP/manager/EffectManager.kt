package me.nickotato.shadowSMP.manager

import me.nickotato.shadowSMP.data.PlayerData
import me.nickotato.shadowSMP.enums.Charm
import me.nickotato.shadowSMP.enums.Ghost
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team

object EffectManager {
    data class EffectData(
        val type: PotionEffectType,
        val amplifier: Int
    )

    fun getDesiredEffects(player: Player): List<EffectData> {
        val data = PlayerManager.getPlayerData(player)
        return getDesiredEffects(data)
    }

    fun getDesiredEffects(data: PlayerData): List<EffectData> {
        val effects = mutableListOf<EffectData>()

        when (data.ghost) {
            Ghost.BANSHEE -> {
                effects.add(
                    EffectData(
                        PotionEffectType.STRENGTH,
                        0
                    )
                )
            }

            Ghost.GOLEM -> {
                effects.add(
                    EffectData(
                        PotionEffectType.RESISTANCE,
                        0
                    )
                )
            }

            Ghost.DEOGEN -> {
                effects.add(
                    EffectData(
                        PotionEffectType.SPEED,
                        0
                    )
                )
            }

            Ghost.REAPER -> {
                effects.add(
                    EffectData(
                        PotionEffectType.GLOWING,
                        0
                    )
                )
            }

            Ghost.GOD -> {
                effects.add(
                    EffectData(PotionEffectType.GLOWING, 0)
                )
            }

            else -> {}
        }

        when (data.charm) {
            Charm.ARES_BRACELET -> {
                effects.add(
                    EffectData(
                        PotionEffectType.STRENGTH,
                        0
                    )
                )
            }
            else -> {}
        }

        return effects
    }

    private fun removeFromGlowTeams(
        board: Scoreboard,
        player: Player
    ) {

        board.getTeam("glow_reaper")?.removeEntry(player.name)
        board.getTeam("glow_god")?.removeEntry(player.name)
    }

    private fun getOrCreateTeam(
        board: Scoreboard,
        teamName: String,
        color: NamedTextColor
    ): Team? {

        val team = board.getTeam(teamName)

        if (team != null) {
            return team
        }

        return try {

            board.registerNewTeam(teamName).apply {
                this.color(color)
            }

        } catch (_: IllegalArgumentException) {

            // Another plugin/task may have created it simultaneously
            board.getTeam(teamName)
        }
    }


    private fun addPlayerToGlowTeam(player: Player) {
        val board = Bukkit.getScoreboardManager().mainScoreboard
        val data = PlayerManager.getPlayerData(player)

        val glowInfo = when (data.ghost) {
            Ghost.REAPER -> Pair("glow_reaper", NamedTextColor.BLACK)
            Ghost.GOD -> Pair("glow_god", NamedTextColor.GOLD)
            else -> null
        }

        if (glowInfo == null) {
            removeFromGlowTeams(board, player)
            return
        }

        val (teamName, color) = glowInfo
        val team = getOrCreateTeam(board, teamName, color) ?: return

        if (!team.hasEntry(player.name)) {
            team.addEntry(player.name)
        }
    }
    fun applyPassiveEffects(player: Player) {

        addPlayerToGlowTeam(player)

        val effects = getDesiredEffects(player)

        for (effect in effects) {
            player.addPotionEffect(
                PotionEffect(
                    effect.type,
                    -1,   // "infinite"
                    effect.amplifier,
                    true,
                    false,
                    true
                )
            )
        }
    }

}