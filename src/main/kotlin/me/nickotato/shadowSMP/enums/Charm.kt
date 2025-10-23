package me.nickotato.shadowSMP.enums

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilities.RevenantAbility
import me.nickotato.shadowSMP.abilities.charms.FrostGaleAbility
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
    ),
    ARES_BRACELET(
        null,
        CharmItem.create(
            Component.text("§eAres's Bracelet"),
            listOf(
                Component.text("§7Gives permanent strength I")
            )
        )
    ),
    FROST_GALE(
        FrostGaleAbility(),
        CharmItem.create(
            Component.text("§eFrost Gale"),
            listOf(
                Component.text("§7Fire a blast to slowdown enemies")
            )
        )
    ),
    // Thing that fire a projectile that slows the person it hits
    // Reduces cooldowns by 1/4th
    // Auto break trees
    // Don't lose upgraders on death
}