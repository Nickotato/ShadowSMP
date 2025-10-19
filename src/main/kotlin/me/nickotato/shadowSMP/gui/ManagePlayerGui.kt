package me.nickotato.shadowSMP.gui

import me.nickotato.shadowSMP.manager.PlayerManager
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class ManagePlayerGui(target: Player): Gui(Component.text("Managing ${target.name}"), 27) {
    init {
        val targetData = PlayerManager.getPlayerData(target)

        val ghost = ItemStack(Material.BOOK, 1)
        val ghostMeta = ghost.itemMeta
        ghostMeta.displayName(Component.text("§d${targetData.ghost.name}"))
        ghost.itemMeta = ghostMeta
        setItem(10, ghost)

        val charm = ItemStack(Material.BOOK, 1)
        val charmMeta = charm.itemMeta
        val charmData = targetData.charm
        if (charmData == null) {
            charmMeta.displayName(Component.text("They have no charm"))
        } else {
            charmMeta.displayName(Component.text("§d${charmData.name}"))
        }
        charm.itemMeta = charmMeta
        setItem(12, charm)

        val isUpgraded = targetData.isUpgraded
        val upgraded: ItemStack = if (isUpgraded) ItemStack(Material.LIME_DYE, 1)
        else ItemStack(Material.RED_DYE, 1)

        val upgradedMeta = upgraded.itemMeta
        val displayName = if (isUpgraded) "§aUpgraded" else "§cNot Upgraded"
        upgradedMeta.displayName(Component.text(displayName))
        upgraded.itemMeta = upgradedMeta
        setItem(14, upgraded)

        val soulAmount = targetData.souls
        val souls = ItemStack(Material.ENCHANTED_BOOK, 1)
        val soulMeta = souls.itemMeta
        soulMeta.displayName(Component.text("§d$soulAmount souls"))
        souls.itemMeta = soulMeta
        setItem(16, souls)
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
    }
}