package me.nickotato.shadowSMP.gui

import me.nickotato.shadowSMP.config.Settings
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class ConfigGui : Gui(Component.text("ShadowSMP Config"), 27) {

    companion object {
        private const val SLOT_SOULS_ENABLED = 11
        private const val SLOT_UPGRADER_NEEDED = 12
        private const val SLOT_BAN_ON_SOUL_LIMIT = 13
        private const val SLOT_DISABLE_GHOST = 14
        private const val SLOT_REVENANT_SLOWNESS = 15
    }

    init {
        reloadGui()
    }

    fun reloadGui() {

        inventory.clear()

        makeToggleItem(SLOT_SOULS_ENABLED, Settings.soulsEnabled, "Souls Enabled")
        makeToggleItem(SLOT_UPGRADER_NEEDED, Settings.upgradersNeeded, "Upgraders Needed")
        makeToggleItem(SLOT_BAN_ON_SOUL_LIMIT, Settings.banOnSoulLimit, "Ban On Soul Limit")
        makeToggleItem(SLOT_DISABLE_GHOST, Settings.disableGhostOnSoulLimit,"Disable Ghost On Soul Limit")
        makeToggleItem(SLOT_REVENANT_SLOWNESS, Settings.revenantCausesSlowness, "Revenant Causes Slowness")
    }

    private fun makeToggleItem(slot: Int, value: Boolean, label: String) {
        val material = if (value) Material.LIME_DYE else Material.RED_DYE
        val item = ItemStack(material)
        val meta = item.itemMeta
        meta.displayName(Component.text("$label: $value"))

        item.itemMeta = meta
        setItem(slot, item)
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true

        when (event.slot) {

            SLOT_SOULS_ENABLED -> {
                Settings.toggleSoulsEnabled()
                reloadGui()
            }

            SLOT_UPGRADER_NEEDED -> {
                Settings.toggleUpgradersNeeded()
                reloadGui()
            }

            SLOT_BAN_ON_SOUL_LIMIT -> {
                Settings.toggleBanOnSoulLimit()
                reloadGui()
            }

            SLOT_DISABLE_GHOST -> {
                Settings.toggleDisableGhostOnSoulLimit()
                reloadGui()
            }

            SLOT_REVENANT_SLOWNESS -> {
                Settings.toggleRevenantSlowness()
                reloadGui()
            }
        }
    }
}