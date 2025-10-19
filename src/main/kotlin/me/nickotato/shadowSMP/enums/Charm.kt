package me.nickotato.shadowSMP.enums

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.items.CharmItem
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class Charm(val ability: Ability?, val item: ItemStack) {
    FEATHER(
        null,
        CharmItem.create(
            Component.text("Feather"),
            listOf(
                Component.text("&7Grants speed boost")
            )
        )
    )
}