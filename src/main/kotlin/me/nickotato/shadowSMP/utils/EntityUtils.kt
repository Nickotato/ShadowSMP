package me.nickotato.shadowSMP.utils

import org.bukkit.block.BlockFace
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType

object EntityUtils {
    val immune = setOf(
        EntityType.VILLAGER,
        EntityType.PARROT,
        EntityType.WOLF,
        EntityType.GHAST,
        EntityType.CAT,
        EntityType.OCELOT,
        EntityType.ARMOR_STAND,
    )
    fun isOnGround(entity: Entity): Boolean {
        return entity.location.block.getRelative(BlockFace.DOWN).type.isSolid
    }

    fun isImmune(type: EntityType): Boolean {
        return (type in immune)
    }
}