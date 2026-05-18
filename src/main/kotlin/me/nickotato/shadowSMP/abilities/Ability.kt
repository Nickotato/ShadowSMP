package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.enums.AbilityType
import me.nickotato.shadowSMP.enums.Charm
import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.events.AbilityReadyEvent
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.UUID
import kotlin.math.ceil

abstract class Ability(val cooldown: Int) {

    private val cooldownEnd = mutableMapOf<UUID, Long>()

    abstract fun execute(player: Player)

    fun activate(player: Player, type: AbilityType) {
        val remaining = getRemainingCooldown(player)

        if (remaining > 0) {
            val secondsLeft = ceil(remaining / 1000.0).toInt()
            player.sendMessage("§cAbility on cooldown for $secondsLeft seconds!")
            return
        }

        execute(player)

        val durationMs = (getEffectiveCooldown(player) * 1000).toLong()
        val endTime = System.currentTimeMillis() + durationMs

        cooldownEnd[player.uniqueId] = endTime

        scheduleReadyEvent(player, type, endTime)
    }

    fun resetCooldown(player: Player) {
        cooldownEnd.remove(player.uniqueId)
    }

    fun isOnCooldown(player: Player): Boolean {
        return getRemainingCooldown(player) > 0
    }

    private fun getEffectiveCooldown(player: Player): Double {
        val data = PlayerManager.getPlayerData(player)

        var multiplier = 1.0

        if (data.ghost == Ghost.CHRONOMANCER) {
            multiplier *= 0.75
        }

        if (data.charm == Charm.CHRONOS_BAND) {
            multiplier *= 0.75
        }

        if (player.inventory.contains(Material.DRAGON_EGG)) {
            multiplier *= 0.5
        }

        return cooldown * multiplier
    }

    private fun getRemainingCooldown(player: Player): Long {
        val end = cooldownEnd[player.uniqueId] ?: return 0
        return (end - System.currentTimeMillis()).coerceAtLeast(0)
    }

    private fun scheduleReadyEvent(player: Player, type: AbilityType, endTime: Long) {
        val delayTicks = ((endTime - System.currentTimeMillis()) / 50)
            .coerceAtLeast(0)

        Bukkit.getScheduler().runTaskLater(ShadowSMP.instance, Runnable {
            if (!player.isOnline) return@Runnable

            val storedEnd = cooldownEnd[player.uniqueId] ?: return@Runnable

            // only fire if this is still the same cooldown instance
            if (storedEnd == endTime) {
                Bukkit.getPluginManager().callEvent(
                    AbilityReadyEvent(player, this, type)
                )
            }
        }, delayTicks)
    }
}