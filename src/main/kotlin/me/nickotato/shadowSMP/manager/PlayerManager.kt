package me.nickotato.shadowSMP.manager

import io.papermc.paper.ban.BanListType
import me.nickotato.shadowSMP.data.PlayerData
import me.nickotato.shadowSMP.data.PlayerDataStorage
import me.nickotato.shadowSMP.enums.Ghost
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.time.Instant
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

    fun addNewPlayerFromData(data: PlayerData) {
        players[data.uuid] = data
    }

    fun getPlayerData(player: Player): PlayerData {
        return players[player.uniqueId] ?: run {
            addNewPlayer(player)
            players[player.uniqueId]!!
        }
    }

    fun changePlayerSouls(player: Player, amount: Int) {
        val playerData = getPlayerData(player)
        if (playerData.souls + amount > 5) return
        else if (playerData.souls + amount < -5) {
            banPlayer(player, "ran out of souls")
            return
        }
        playerData.souls += amount
    }

    fun banPlayer(player: Player, reason: String) {
        player.kick(Component.text("§4You have been banned!\n§7Reason: $reason"), PlayerKickEvent.Cause.BANNED)
        val banList = Bukkit.getBanList(BanListType.PROFILE)
        banList.addBan(player.playerProfile, "Reason: $reason", null as Instant?, "ShadowSmp")
        Bukkit.broadcast(Component.text("§c${player.name} was banned for $reason"))
    }

    fun getOfflinePlayerHead(player: OfflinePlayer): ItemStack {
        val head = ItemStack(Material.PLAYER_HEAD)
        val meta = head.itemMeta as SkullMeta

        // Modern Paper 1.20+ approach:
        val profile = player.playerProfile //?: Bukkit.createProfile(player.name ?: "Unknown")
        meta.playerProfile = profile
        meta.displayName(Component.text(player.name ?: "Unknown"))

        head.itemMeta = meta
        return head
    }

    fun revivePlayer(playerName: String) {
        val bannedPlayer = Bukkit.getOfflinePlayer(playerName)
        Bukkit.getServer().getBanList(BanListType.PROFILE).pardon(bannedPlayer.playerProfile)

        val uuid = bannedPlayer.uniqueId
        val playerData = PlayerDataStorage.loadPlayerData(uuid) ?: PlayerData(
            uuid = uuid,
            name = playerName,
            souls = -3,
            isUpgraded = false,
            ghost = Ghost.REVENANT,
            charm = null,
        )

        playerData.souls = -3

        PlayerDataStorage.savePlayerData(playerData)
        // Need to send a message that the user had been revived.
    }
}