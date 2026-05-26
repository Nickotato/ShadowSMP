package me.nickotato.shadowSMP.gui.config

import me.nickotato.shadowSMP.config.Settings
import me.nickotato.shadowSMP.enums.Ghost
import me.nickotato.shadowSMP.gui.Gui
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class ConfigureGhostsGui: Gui(Component.text("Configure Ghosts"), 27) {
    private val slotGhostMap = mutableMapOf<Int, Ghost>()

    init {
        refresh()
    }

    private fun refresh() {
        inventory.clear()
        slotGhostMap.clear()

        for ((index, ghost) in Ghost.entries.withIndex()) {

            slotGhostMap[index] = ghost

            val enabled = Settings.isGhostEnabled(ghost)
            val isEvent = Settings.isEventGhost(ghost)

            val material =
                if (enabled) Material.LIME_DYE
                else Material.GRAY_DYE

            val item = ItemStack(material)
            val meta = item.itemMeta

            meta.displayName(
                Component.text(
                    if (enabled)
                        "§a${ghost.name}"
                    else
                        "§c${ghost.name}"
                )
            )

            val lore = mutableListOf<Component>()

            lore.add(
                Component.text(
                    if (enabled)
                        "§7Status: §aEnabled"
                    else
                        "§7Status: §cDisabled"
                )
            )

            lore.add(
                Component.text(
                    if (isEvent)
                        "§7Type: §dEvent Ghost"
                    else
                        "§7Type: §fNormal Ghost"
                )
            )

            lore.add(Component.text(" "))
            lore.add(Component.text("§eLeft Click to toggle enabled"))
            lore.add(Component.text("§bRight Click to toggle event"))

            meta.lore(lore)

            item.itemMeta = meta

            setItem(index, item)
        }
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true

        val ghost = slotGhostMap[event.slot] ?: return
        val player = event.whoClicked as Player

        when (event.click) {

            ClickType.LEFT -> {
                val enabled = Settings.isGhostEnabled(ghost)
                Settings.setGhostEnabled(ghost, !enabled)

                player.sendMessage(
                    if (!enabled)
                        "§aEnabled ${ghost.name}"
                    else
                        "§cDisabled ${ghost.name}"
                )
            }

            ClickType.RIGHT -> {
                val eventGhost = Settings.isEventGhost(ghost)
                Settings.setEventGhost(ghost, !eventGhost)

                player.sendMessage(
                    if (!eventGhost)
                        "§d${ghost.name} is now an event ghost"
                    else
                        "§f${ghost.name} is no longer an event ghost"
                )
            }

            else -> return
        }

        refresh()
    }
}