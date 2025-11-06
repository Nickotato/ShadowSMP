package me.nickotato.shadowSMP.manager

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.enums.Ghost
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.scheduler.BukkitRunnable
import java.util.UUID

object AbilityManager {
    val invulnerablePlayers  = mutableSetOf<UUID>()
    val trueDamagePlayers = mutableSetOf<UUID>()
    val locationHistory = mutableMapOf<UUID, ArrayDeque<Location>>()
    val tempNoFallPlayers = mutableSetOf<UUID>()

    private val notifiedAbility = mutableMapOf<UUID, MutableSet<String>>()

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

    fun cooldownNotifier() {
        object : BukkitRunnable() {
            override fun run() {
                for (player in Bukkit.getOnlinePlayers()) {
                    val data = PlayerManager.getPlayerData(player)
                    val charm = data.charm
                    val notifiedSet = notifiedAbility.getOrPut(player.uniqueId) { mutableSetOf() }

                    // Ghost ability
                    if (!data.ghost.ability.isOnCooldown(player)) {
                        if ("ghostAbility" !in notifiedSet) {
                            player.sendActionBar(Component.text("§aAbility is ready!"))
                            if (data.ghost != Ghost.REVENANT) {
                                player.playSound(player.location, org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT, 1f, 1f)
                            }
                            notifiedSet.add("ghostAbility")
                        }
                    } else {
                        notifiedSet.remove("ghostAbility")
                    }

                    // Ghost ultimate
                    if (!data.ghost.ultimate.isOnCooldown(player)) {
                        if ("ghostUltimate" !in notifiedSet) {
                            player.sendActionBar(Component.text("§aUltimate is ready!"))
                            player.playSound(player.location, org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT, 1f, 1f)
                            notifiedSet.add("ghostUltimate")
                        }
                    } else {
                        notifiedSet.remove("ghostUltimate")
                    }

                    // Charm ability
                    if (charm?.ability != null && !charm.ability.isOnCooldown(player)) {
                        if ("charmAbility" !in notifiedSet) {
                            player.sendActionBar(Component.text("§aCharm ability is ready!"))
                            player.playSound(player.location, org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT, 1f, 1f)
                            notifiedSet.add("charmAbility")
                        }
                    } else {
                        notifiedSet.remove("charmAbility")
                    }
                }
            }
        }.runTaskTimer(ShadowSMP.instance, 20L, 10L)
    }

    fun beginRevenantFlightCheck() {
        object : BukkitRunnable(){
            override fun run() {
                for (player in Bukkit.getOnlinePlayers()) {
                    val data = PlayerManager.getPlayerData(player)
                    if (data.ghost != Ghost.REVENANT) {
                        if (player.gameMode == GameMode.SURVIVAL || player.gameMode == GameMode.ADVENTURE) player.allowFlight = false;
                        return
                    }

                    if (data.ghost.ability.isOnCooldown(player)) {
                        player.allowFlight = false
                    } else {
                        player.allowFlight = true
                    }
                }
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }
}