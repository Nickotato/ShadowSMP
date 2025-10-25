package me.nickotato.shadowSMP.listeners.player

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.enums.Charm
import me.nickotato.shadowSMP.manager.PlayerManager
import me.nickotato.shadowSMP.utils.BlockUtils
import org.bukkit.Tag
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.scheduler.BukkitRunnable

class PlayerBreakListener: Listener {
    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        val player = event.player
        val data = PlayerManager.getPlayerData(player)
        if (data.charm != Charm.LUMBERJACK_AXE) return

        val block = event.block
        val blockType = block.type

        if (Tag.LOGS.isTagged(blockType)) {
            breakLogsAround(block)
        }
    }

    fun breakLogsAround(block: Block, visited: MutableSet<Block> = mutableSetOf()) {
        if (block in visited) return
        visited.add(block)

        val nearbyBlocks = BlockUtils.getNearbyBlocks(block, 1)
        for (nearbyBlock in nearbyBlocks) {
            if (Tag.LOGS.isTagged(nearbyBlock.type)) {
                nearbyBlock.breakNaturally()
                object : BukkitRunnable() {
                    override fun run() {
                        breakLogsAround(nearbyBlock, visited)
                    }
                }.runTaskLater(ShadowSMP.instance, 2)

            }
        }
    }


}