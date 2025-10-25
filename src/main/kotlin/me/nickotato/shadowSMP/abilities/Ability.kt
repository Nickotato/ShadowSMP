package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.enums.Charm
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.UUID
import kotlin.math.ceil

abstract class Ability(val cooldown: Int) {
    private val lastUsed = mutableMapOf<UUID, Long>()

    abstract fun execute(player: Player)

    fun activate(player: Player) {
        val data = PlayerManager.getPlayerData(player)
        val now = System.currentTimeMillis()
        val last = lastUsed[player.uniqueId] ?: 0
        val hasDragonEgg = player.inventory.contains(Material.DRAGON_EGG)

        var multiplier = 1.0
        if (data.charm == Charm.CHRONOS_BAND) multiplier *= 0.75
        if (hasDragonEgg) multiplier *= 0.5

        val effectiveCooldown = cooldown * multiplier

        val remaining = (effectiveCooldown * 1000) - (now - last)

        if (remaining > 0) {
            val secondsLeft = ceil(remaining / 1000.0).toInt()
            player.sendMessage("§cAbility on cooldown for $secondsLeft seconds!")
            return
        }

        execute(player)

        lastUsed[player.uniqueId] = now
    }

    fun resetCooldown(player: Player) {
        lastUsed.remove(player.uniqueId)
    }

    fun isOnCooldown(player: Player): Boolean {
        val now = System.currentTimeMillis()
        val last = lastUsed[player.uniqueId] ?: return false

        val data = PlayerManager.getPlayerData(player)

        // Recalculate effective cooldown like in activate()
        var multiplier = 1.0
        if (data.charm == Charm.CHRONOS_BAND) multiplier *= 0.25
        if (player.inventory.contains(Material.DRAGON_EGG)) multiplier *= 0.5

        val effectiveCooldown = cooldown * multiplier
        val elapsed = now - last

        return elapsed < effectiveCooldown * 1000
    }
}