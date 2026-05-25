package me.nickotato.shadowSMP.config

import me.nickotato.shadowSMP.data.SettingsDataStorage

object Settings {
    var soulsEnabled = true
    var upgradersNeeded = true
    var banOnSoulLimit = false
    var disableGhostOnSoulLimit = false

    var revenantCausesSlowness = false


    fun toggleSoulsEnabled() {
        soulsEnabled = !soulsEnabled
        save()
    }

    fun toggleUpgradersNeeded() {
        upgradersNeeded = !upgradersNeeded
        save()
    }

    fun toggleBanOnSoulLimit() {
        banOnSoulLimit = !banOnSoulLimit
        save()
    }

    fun toggleDisableGhostOnSoulLimit() {
        disableGhostOnSoulLimit = !disableGhostOnSoulLimit
        save()
    }

    fun toggleRevenantSlowness() {
        revenantCausesSlowness = !revenantCausesSlowness
        save()
    }

    private fun save() {
        SettingsDataStorage.saveSettings()
    }
}