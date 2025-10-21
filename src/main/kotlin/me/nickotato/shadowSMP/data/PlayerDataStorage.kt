package me.nickotato.shadowSMP.data

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.enums.Charm
import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.manager.PlayerManager
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.UUID

object PlayerDataStorage {
    private var dataFolder = File(ShadowSMP.instance.dataFolder, "playerdata")

    init {
        if (!dataFolder.exists()) dataFolder.mkdirs()
    }

    fun savePlayerData(data: PlayerData) {
        val file = File(dataFolder, "${data.uuid}.yml")
        val config = YamlConfiguration()

        config.set("uuid", data.uuid.toString())
        config.set("name", data.name)
        config.set("ghost",data.ghost.name)
        config.set("charm", data.charm?.name)
        config.set("souls", data.souls)
        config.set("isUpgraded", data.isUpgraded)

        config.save(file)
    }

    fun loadPlayerData(uuid: UUID): PlayerData? {
        val file = File(dataFolder, "$uuid.yml")
        if (!file.exists()) return null

        val config = YamlConfiguration.loadConfiguration(file)
        val player = Bukkit.getPlayer(uuid) ?: return null
        val name = player.name

        val ghostName = config.getString("ghost") ?: return null
        val ghost = Ghost.valueOf(ghostName)
        val charmName = config.getString("charm")
        val charm = if (charmName != null) Charm.valueOf(charmName) else null
        val souls = config.getInt("souls", 0)
        val isUpgraded = config.getBoolean("isUpgraded")

        return PlayerData(uuid, name, souls, isUpgraded, ghost, charm)
    }

    fun saveAll() {
        PlayerManager.players.values.forEach { savePlayerData(it) }
    }
}