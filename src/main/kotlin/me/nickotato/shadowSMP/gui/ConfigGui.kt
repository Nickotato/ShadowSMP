package me.nickotato.shadowSMP.gui

import me.nickotato.shadowSMP.config.Settings
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class ConfigGui : Gui(Component.text("ShadowSMP Config"), 27) {

    init {
        reloadGui()
    }

    fun reloadGui() {

        inventory.clear()

        val soulsMaterial =
            if (Settings.soulsEnabled) Material.LIME_DYE else Material.RED_DYE

        val soulsItem = ItemStack(soulsMaterial)
        val soulsMeta = soulsItem.itemMeta
        soulsMeta.displayName(
            Component.text("Souls Enabled: ${Settings.soulsEnabled}")
        )
        soulsItem.itemMeta = soulsMeta

        setItem(10, soulsItem)

        val upgradersMaterial =
            if (Settings.upgradersNeeded) Material.LIME_DYE else Material.RED_DYE

        val upgradersItem = ItemStack(upgradersMaterial)
        val upgradersMeta = upgradersItem.itemMeta
        upgradersMeta.displayName(
            Component.text("Upgraders Needed: ${Settings.upgradersNeeded}")
        )
        upgradersItem.itemMeta = upgradersMeta

        setItem(12, upgradersItem)

        val banMaterial =
            if (Settings.banOnSoulLimit) Material.LIME_DYE else Material.RED_DYE

        val banItem = ItemStack(banMaterial)
        val banMeta = banItem.itemMeta
        banMeta.displayName(
            Component.text("Ban On Soul Limit: ${Settings.banOnSoulLimit}")
        )
        banItem.itemMeta = banMeta

        setItem(14, banItem)

        val ghostMaterial =
            if (Settings.disableGhostOnSoulLimit) Material.LIME_DYE else Material.RED_DYE

        val ghostItem = ItemStack(ghostMaterial)
        val ghostMeta = ghostItem.itemMeta
        ghostMeta.displayName(
            Component.text("Disable Ghost On Soul Limit: ${Settings.disableGhostOnSoulLimit}")
        )
        ghostItem.itemMeta = ghostMeta

        setItem(16, ghostItem)
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true

        when (event.slot) {

            10 -> {
                Settings.toggleSoulsEnabled()
                reloadGui()
            }

            12 -> {
                Settings.toggleUpgradersNeeded()
                reloadGui()
            }

            14 -> {
                Settings.toggleBanOnSoulLimit()
                reloadGui()
            }

            16 -> {
                Settings.toggleDisableGhostOnSoulLimit()
                reloadGui()
            }
        }
    }
}