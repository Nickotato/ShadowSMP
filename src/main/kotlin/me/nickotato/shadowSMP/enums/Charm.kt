package me.nickotato.shadowSMP.enums

import me.nickotato.shadowSMP.abilities.Ability
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class Charm(val ability: Ability?, val item: ItemStack) {
    FEATHER(null, ItemStack(Material.BOOK, 1))
}