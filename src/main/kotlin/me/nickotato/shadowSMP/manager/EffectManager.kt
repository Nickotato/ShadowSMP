//package me.nickotato.shadowSMP.manager
//
//import me.nickotato.shadowSMP.ShadowSMP
//import me.nickotato.shadowSMP.enums.Charm
//import me.nickotato.shadowSMP.enums.Ghost
//import net.kyori.adventure.text.format.NamedTextColor
//import org.bukkit.Bukkit
//import org.bukkit.entity.Player
//import org.bukkit.potion.PotionEffect
//import org.bukkit.potion.PotionEffectType
//import org.bukkit.scheduler.BukkitRunnable
//import org.bukkit.scheduler.BukkitTask
//
//object EffectManager {
//
//    private var task: BukkitTask? = null
//
//    private data class EffectData(val type: PotionEffectType, val amplifier: Int)
//
//    fun applyEffect(player: Player, type: PotionEffectType, amplifier: Int) {
//        val current = player.getPotionEffect(type)
//
//        // If player already has a stronger or equal effect, skip
//        if (current != null && current.amplifier >= amplifier && current.duration > 40) {
//            return
//        }
//
//
//        player.addPotionEffect(PotionEffect(type, 20 * 45, amplifier))
//    }
//
//    private fun shouldHaveEffect(player: Player): List<EffectData> {
//        val effects = mutableListOf<EffectData>()
//        val playerData = PlayerManager.getPlayerData(player)
//
////        Bukkit.getLogger().info("[EffectManager] ${player.name} ghost=${playerData.ghost} charm=${playerData.charm}") // remove after debugging
//
//        if (playerData.ghost == Ghost.BANSHEE) effects.add(EffectData(PotionEffectType.STRENGTH, 0))
//        if (playerData.ghost == Ghost.GOLEM) effects.add(EffectData(PotionEffectType.RESISTANCE, 0))
//        if (playerData.charm == Charm.ARES_BRACELET) effects.add(EffectData(PotionEffectType.STRENGTH, 0))
//        if (playerData.ghost == Ghost.DEOGEN) effects.add(EffectData(PotionEffectType.SPEED, 0))
//
//        return effects
//    }
//
//    fun startEffectLoop() {
//        task?.cancel() // cancel any existing task first
//        task = object : BukkitRunnable() {
//            override fun run() {
//                try {
//                    val players = Bukkit.getOnlinePlayers().toList()
//                    for (player in players) {
//                        try {
//                            val effects = shouldHaveEffect(player)
//                            for (effect in effects) {
//                                applyEffect(player, effect.type, effect.amplifier)
//                            }
//                            applyGlow(player)
//                        } catch (t: Throwable) {
//                            Bukkit.getLogger().severe("Effect error for ${player.name}")
//                            t.printStackTrace()
//                        }
//                    }
//                } catch (t: Throwable) {
//                    Bukkit.getLogger().severe("EFFECT LOOP CRASHED — restarting")
//                    t.printStackTrace()
//                    this.cancel()
//                    startEffectLoop()
//                }
//            }
//        }.runTaskTimer(ShadowSMP.instance, 0L, 20L * 3)
//    }
//
//
//    private fun applyGlow(player: Player) {
//        val board = Bukkit.getScoreboardManager().mainScoreboard
//
//        val playerData = PlayerManager.getPlayerData(player)
//
//        val (teamName, color) = when (playerData.ghost) {
//            Ghost.REAPER -> "glow_reaper" to NamedTextColor.BLACK
//            Ghost.GOD -> "glow_god" to NamedTextColor.GOLD
//            else -> null to null
//        }
//
//        try {
//            // Always remove from both teams first (safe cleanup)
//            board.getTeam("glow_reaper")?.removeEntry(player.name)
//            board.getTeam("glow_god")?.removeEntry(player.name)
//
//            if (teamName == null || color == null) {
//                player.isGlowing = false
//                return
//            }
//
//            // Get or create team safely
//            var team = board.getTeam(teamName)
//
//            if (team == null) {
//                team = try {
//                    board.registerNewTeam(teamName).apply {
//                        this.color(color)
//                    }
//                } catch (e: IllegalArgumentException) {
//                    // Team already exists or invalid state — fallback safely
//                    board.getTeam(teamName)
//                }
//            }
//
//            // If still null, abort safely
//            if (team == null) {
//                player.isGlowing = false
//                return
//            }
//
//            if (!team.hasEntry(player.name)) {
//                team.addEntry(player.name)
//            }
//
//            player.isGlowing = true
//
//        } catch (e: Exception) {
//            Bukkit.getLogger().warning("applyGlow failed for ${player.name}")
//            e.printStackTrace()
//
//            // Fail-safe: don't leave glow stuck on
//            player.isGlowing = false
//        }
//    }
//}
//

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
import org.bukkit.scheduler.BukkitTask
import org.bukkit.scoreboard.Team

object EffectManager {

    private var task: BukkitTask? = null

    private const val EFFECT_DURATION = 20 * 30 // 30 seconds
    private const val LOOP_INTERVAL = 20L * 2   // every 2 seconds

    private data class EffectData(
        val type: PotionEffectType,
        val amplifier: Int
    )

    fun startEffectLoop() {
        stopEffectLoop()

        task = object : BukkitRunnable() {
            override fun run() {

                try {

                    for (player in Bukkit.getOnlinePlayers()) {

                        try {
                            updatePlayerEffects(player)
                            updateGlow(player)

                        } catch (t: Throwable) {
                            Bukkit.getLogger().severe(
                                "[EffectManager] Failed updating ${player.name}"
                            )
                            t.printStackTrace()
                        }
                    }

                } catch (t: Throwable) {

                    Bukkit.getLogger().severe(
                        "[EffectManager] EFFECT LOOP CRASHED — restarting"
                    )

                    t.printStackTrace()

                    cancel()

                    Bukkit.getScheduler().runTaskLater(
                        ShadowSMP.instance,
                        Runnable {
                            startEffectLoop()
                        },
                        20L
                    )
                }
            }
        }.runTaskTimer(
            ShadowSMP.instance,
            0L,
            LOOP_INTERVAL
        )
    }

    fun stopEffectLoop() {
        task?.cancel()
        task = null
    }

    private fun updatePlayerEffects(player: Player) {

        if (!player.isOnline || player.isDead) {
            return
        }

        val desiredEffects = getDesiredEffects(player)

        for (effect in desiredEffects) {
            applyOrRefresh(player, effect)
        }
    }

    private fun getDesiredEffects(player: Player): List<EffectData> {

        val data = PlayerManager.getPlayerData(player)

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

            else -> {}
        }

        if (data.charm == Charm.ARES_BRACELET) {
            effects.add(
                EffectData(
                    PotionEffectType.STRENGTH,
                    0
                )
            )
        }

        return effects
    }

    private fun applyOrRefresh(
        player: Player,
        effect: EffectData
    ) {

        player.addPotionEffect(
            PotionEffect(
                effect.type,
                EFFECT_DURATION,
                effect.amplifier,
                true,
                false,
                true
            )
        )
    }

    private fun updateGlow(player: Player) {

        val board = Bukkit.getScoreboardManager()?.mainScoreboard ?: return
        val data = PlayerManager.getPlayerData(player)

        val glowInfo = when (data.ghost) {
            Ghost.REAPER -> Pair("glow_reaper", NamedTextColor.BLACK)
            Ghost.GOD -> Pair("glow_god", NamedTextColor.GOLD)
            else -> null
        }

        try {

            removeFromGlowTeams(board, player)

            if (glowInfo == null) {
                player.isGlowing = false
                return
            }

            val (teamName, color) = glowInfo

            val team = getOrCreateTeam(board, teamName, color) ?: run {
                player.isGlowing = false
                return
            }

            if (!team.hasEntry(player.name)) {
                team.addEntry(player.name)
            }

            player.isGlowing = true

        } catch (e: Exception) {

            Bukkit.getLogger().warning("[EffectManager] Glow update failed for ${player.name}")
            e.printStackTrace()

            player.isGlowing = false
        }
    }

    private fun removeFromGlowTeams(
        board: org.bukkit.scoreboard.Scoreboard,
        player: Player
    ) {

        board.getTeam("glow_reaper")?.removeEntry(player.name)
        board.getTeam("glow_god")?.removeEntry(player.name)
    }

    private fun getOrCreateTeam(
        board: org.bukkit.scoreboard.Scoreboard,
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

    fun applyPassiveEffects(player: Player) {

        val effects = getDesiredEffects(player)

//        for (effect in PotionEffectType.values()) {
//            player.removePotionEffect(effect)
//        }

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
//        Int.MAX_VALUE
    }
}