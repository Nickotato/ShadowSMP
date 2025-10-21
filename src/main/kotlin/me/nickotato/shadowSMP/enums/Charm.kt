package me.nickotato.shadowSMP.enums

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.items.CharmItem
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

enum class Charm(val ability: Ability?, val item: ItemStack) {
    FEATHER(
        null,
        CharmItem.create(
            Component.text("§eFeather"),
            listOf(
                Component.text("§7Removes fall damage")
            )
        )
    )
    // Thing that fire a projectile that slows the person it hits
    // Reduces cooldowns by 1/4th
    // Auto break trees
    // Don't lose upgraders on death
}