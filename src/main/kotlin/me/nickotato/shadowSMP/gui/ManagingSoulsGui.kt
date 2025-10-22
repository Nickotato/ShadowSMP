package me.nickotato.shadowSMP.gui

import me.nickotato.shadowSMP.manager.PlayerManager
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class ManagingSoulsGui(val target: Player): Gui(Component.text("Setting souls of ${target.name}"), 27){
    init {
        //10, 11, 12
        // 14, 15,16
        val changeAmounts = arrayOf(1, 3, 5)
        for ((index, num) in changeAmounts.withIndex()) {
            val value = -num
            val item = ItemStack(Material.RED_DYE, 1)
            val meta = item.itemMeta
            meta.displayName(Component.text("§4$value"))
            item.itemMeta = meta
            setItem(12 - index, item)
        }
        for ((index, num) in changeAmounts.withIndex()) {
            val value = num
            val item = ItemStack(Material.GREEN_DYE, 1)
            val meta = item.itemMeta
            meta.displayName(Component.text("§2+$value"))
            item.itemMeta = meta
            setItem(14 + index, item)
        }

    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true

        val slot = event.slot
        val player = event.whoClicked

        val targetData = PlayerManager.getPlayerData(target)
        var souls = targetData.souls

        when (slot) {
            10 -> souls -=5
            11 -> souls -= 3
            12 -> souls--
            14 -> souls++
            15 -> souls += 3
            16 -> souls += 5
        }

        targetData.souls = souls
        player.sendMessage(Component.text("Changed ${target.name}'s soul amount to: ${targetData.souls}"))
    }
}