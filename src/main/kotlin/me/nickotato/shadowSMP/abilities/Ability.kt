package me.nickotato.shadowSMP.abilities

import org.bukkit.entity.Player
import java.util.UUID
import kotlin.math.ceil

abstract class Ability(val cooldown: Int, val isUltimate: Boolean = false) {
    private val lastUsed = mutableMapOf<UUID, Long>()

    abstract fun execute(player: Player)

    fun activate(player: Player, isUpgraded: Boolean = false) {

        if (isUltimate && !isUpgraded) {
            player.sendMessage("§cYou must be upgraded to use your ultimate!")
            return
        }

        val now = System.currentTimeMillis()
        val last = lastUsed[player.uniqueId] ?: 0
        val remaining = cooldown * 1000 - (now - last)

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
}