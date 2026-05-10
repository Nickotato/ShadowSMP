package me.nickotato.shadowSMP.config

object Settings {
    var soulsEnabled = true
    var upgradersNeeded = true
    var banOnSoulLimit = false
    var disableGhostOnSoulLimit = false


    fun toggleSoulsEnabled() {
        soulsEnabled = !soulsEnabled
    }

    fun toggleUpgradersNeeded() {
        upgradersNeeded = !upgradersNeeded
    }

    fun toggleBanOnSoulLimit() {
        banOnSoulLimit = !banOnSoulLimit
    }

    fun toggleDisableGhostOnSoulLimit() {
        disableGhostOnSoulLimit = !disableGhostOnSoulLimit
    }

}