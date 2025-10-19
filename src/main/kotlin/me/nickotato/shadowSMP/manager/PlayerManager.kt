package me.nickotato.shadowSMP.manager

import me.nickotato.shadowSMP.data.PlayerData
import me.nickotato.shadowSMP.enums.Ghost
import org.bukkit.entity.Player
import java.util.UUID

object PlayerManager {
    val players = mutableMapOf<UUID, PlayerData>()

    fun addNewPlayer(player: Player) {
        players[player.uniqueId] = PlayerData(
            player.uniqueId,
            player.name,
            0,
            false,
            Ghost.entries.random(),
            null,
        )
    }

    fun getPlayerData(player: Player): PlayerData {
        return players[player.uniqueId] ?: run {
            addNewPlayer(player)
            players[player.uniqueId]!!
        }
    }
}