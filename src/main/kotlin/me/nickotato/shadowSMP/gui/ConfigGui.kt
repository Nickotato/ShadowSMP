package me.nickotato.shadowSMP.gui

import me.nickotato.shadowSMP.config.Settings
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class ConfigGui: Gui(Component.text("ShadowSMP Config"), 27) {
    init {
       reloadGui()
    }

    fun reloadGui() {
        val soulMaterial = if (Settings.soulsEnabled) Material.LIME_DYE else Material.RED_DYE
        val disableSouls = ItemStack(soulMaterial)
        val disableSoulsMeta = disableSouls.itemMeta
        disableSoulsMeta.displayName(Component.text("Souls Enabled: ${if (Settings.soulsEnabled) "true" else "false"}"))
        disableSouls.itemMeta = disableSoulsMeta
        setItem(13, disableSouls)
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true

        val slot = event.slot

        when (slot) {
            13 -> {
                Settings.toggleSoulsEnabled()
                reloadGui()
            }
        }
    }
}