package me.nickotato.shadowSMP.utils

import org.bukkit.block.BlockFace
import org.bukkit.entity.Entity

object EntityUtils {
    fun isOnGround(entity: Entity): Boolean {
        return entity.location.block.getRelative(BlockFace.DOWN).type.isSolid
    }
}