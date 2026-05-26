package me.nickotato.shadowSMP.config

import me.nickotato.shadowSMP.data.SettingsDataStorage
import me.nickotato.shadowSMP.enums.Ghost

object Settings {
    var soulsEnabled = true
    var upgradersNeeded = true
    var banOnSoulLimit = false
    var disableGhostOnSoulLimit = false
    var revenantCausesSlowness = false


    var disabledGhosts = mutableSetOf<Ghost>()
    var eventGhosts = mutableSetOf(
        Ghost.JINN,
        Ghost.ONI
    )

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

    fun isGhostEnabled(ghost: Ghost): Boolean {
        return ghost !in disabledGhosts
    }

    fun isEventGhost(ghost: Ghost): Boolean {
        return ghost in eventGhosts
    }

    fun setGhostEnabled(ghost: Ghost, enabled: Boolean) {
        if (enabled) {
            disabledGhosts.remove(ghost)
        } else {
            disabledGhosts.add(ghost)
        }

        save()
    }

    fun setEventGhost(ghost:Ghost, event: Boolean) {
        if (event) {
            eventGhosts.add(ghost)
        } else {
            eventGhosts.remove(ghost)
        }

        save()
    }

    private fun save() {
        SettingsDataStorage.saveSettings()
    }

    //Disabling and Enabling ghosts to be rolled and in general.
}