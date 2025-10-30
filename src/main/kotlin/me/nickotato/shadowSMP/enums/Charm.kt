package me.nickotato.shadowSMP.enums

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilities.charms.FrostGaleAbility
import me.nickotato.shadowSMP.abilities.charms.HermesBootsAbility
import me.nickotato.shadowSMP.items.CharmItem
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

enum class Charm(val ability: Ability?, val item: ItemStack, val displayName: String) {
    FEATHER(
        null,
        CharmItem.create(
            Component.text("§eFeather"),
            listOf(
                Component.text("§7Removes fall damage")
            )
        ),
        "Feather charm"
    ),
    ARES_BRACELET(
        null,
        CharmItem.create(
            Component.text("§eAres's Bracelet"),
            listOf(
                Component.text("§7Gives permanent strength I")
            )
        ),
        "Ares' Bracelet"
    ),
    FROST_GALE(
        FrostGaleAbility(),
        CharmItem.create(
            Component.text("§eFrost Gale"),
            listOf(
                Component.text("§7Fire a blast to slowdown enemies")
            )
        ),
        "Frost Gale"
    ),
    LUMBERJACK_AXE(
        null,
        CharmItem.create(
            Component.text("§eLumberjack Axe"),
            listOf(
                Component.text("§7Automatically chop down connected wood logs")
            )
        ),
        "Lumberjack Axe"
    ),
    CHRONOS_BAND(
        null,
        CharmItem.create(
            Component.text("§eChronos' Band"),
            listOf(
                Component.text("§7Reduces all cooldowns by 1/4th")
            )
        ),
        "Chronos' Band"
    ),
    HERMES_BOOTS(
        HermesBootsAbility(),
        CharmItem.create(
            Component.text("§eHermes' Boots"),
            listOf(
                Component.text("§7Gain temporary speed 5")
            )
        ),
        "Hermes' Boots"
    ),
    // Don't lose upgraders on death
    // Charm to see invisible players.
    // Mimic ability
    // Warm back in time to a previous position.
}