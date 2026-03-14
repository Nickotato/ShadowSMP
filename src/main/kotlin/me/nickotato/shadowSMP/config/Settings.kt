package me.nickotato.shadowSMP.config

object Settings {
    var soulsEnabled = true
    var upgradersNeeded = true

    fun toggleSoulsEnabled() {
        soulsEnabled = !soulsEnabled
    }

    fun toggleUpgradersNeeded() {
        upgradersNeeded = !upgradersNeeded
    }

}