package me.nickotato.shadowSMP.utils

import org.bukkit.block.Block

object BlockUtils {
    fun getNearbyBlocks(center: Block, radius: Int): List<Block> {
        val blocks = mutableListOf<Block>()
        val loc = center.location

        for (x in -radius..radius) {
            for (y in -radius..radius) {
                for (z in -radius..radius) {
                    val block = loc.clone().add(x.toDouble(), y.toDouble(), z.toDouble()).block
                    blocks.add(block)
                }
            }
        }

        return blocks
    }
}