package me.nickotato.shadowSMP.data

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.config.Settings
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object SettingsDataStorage {
    private var dataFolder = File(ShadowSMP.instance.dataFolder, "settings")

    init {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }
    }

    fun saveSettings() {
        val file = File(dataFolder, "settings.yml")
        val config = YamlConfiguration()

        config.set("soulsEnabled", Settings.soulsEnabled)
        config.set("upgradersNeeded", Settings.upgradersNeeded)
        config.set("banOnSoulLimit", Settings.banOnSoulLimit)
        config.set("disableGhostOnSoulLimit", Settings.disableGhostOnSoulLimit)
        config.set("revenantCausesSlowness", Settings.revenantCausesSlowness)
        config.save(file)
    }

    fun loadSettings() {
        val file = File(dataFolder, "settings.yml")
        if (!file.exists()) return

        val config = YamlConfiguration.loadConfiguration(file)
        val soulsEnabled = config.getBoolean("soulsEnabled")
        val upgradersNeeded = config.getBoolean("upgradersNeeded")
        val banOnSoulLimit = config.getBoolean("banOnSoulLimit")
        val disableGhostOnSoulLimit = config.getBoolean("disableGhostOnSoulLimit")
        val revenantCausesSlowness = config.getBoolean("revenantCausesSlowness")

        Settings.soulsEnabled = soulsEnabled
        Settings.upgradersNeeded = upgradersNeeded
        Settings.banOnSoulLimit = banOnSoulLimit
        Settings.disableGhostOnSoulLimit = disableGhostOnSoulLimit
        Settings.revenantCausesSlowness = revenantCausesSlowness
    }
}