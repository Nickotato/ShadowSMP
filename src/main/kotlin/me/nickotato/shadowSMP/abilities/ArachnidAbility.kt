package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.ShadowSMP
//import me.nickotato.shadowSMP.utils.BlockUtils
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class ArachnidAbility : Ability(60) {

    override fun execute(player: Player) {
        val centerBlock = player.location.block
//        val nearbyBlocks = BlockUtils.getNearbyBlocks(centerBlock, 2)

        // Web particle effect at center
        player.world.spawnParticle(Particle.CLOUD, centerBlock.location.add(0.5, 0.5, 0.5), 30, 0.5, 1.0, 0.5, 0.02)
        player.world.playSound(player.location, Sound.ENTITY_SPIDER_AMBIENT, 1.0f, 1.0f)

        if (centerBlock.type == Material.AIR) centerBlock.type = Material.COBWEB

//        for (block in nearbyBlocks) {
//            if (block.type == Material.AIR) {
//                block.type = Material.COBWEB
//                player.world.spawnParticle(Particle.CLOUD, block.location.add(0.5, 0.5, 0.5), 20, 0.3, 0.5, 0.3, 0.02)
//            }
//        }

        // REMOVED MAKING THE PLAYER GENERATE A WEB CAGE AROUND THEM.

        val radius = 30.0
        val world = player.world
        val nearbyPlayers = world.getNearbyEntities(player.location, radius, radius, radius)
            .filterIsInstance<Player>()
            .filter { it != player && it.hasPotionEffect(org.bukkit.potion.PotionEffectType.INVISIBILITY) }

        if (nearbyPlayers.isEmpty()) return

        val scoreboard = Bukkit.getScoreboardManager().mainScoreboard
        val teamName = "arachnid_${player.name.take(10)}"

        // Cleanup existing team if it somehow exists
        scoreboard.getTeam(teamName)?.unregister()

        val team = scoreboard.registerNewTeam(teamName)
        team.setCanSeeFriendlyInvisibles(true)
        team.color(NamedTextColor.DARK_PURPLE)
        team.prefix(Component.text("§5"))

        // Make targets glow only for this player
        for (target in nearbyPlayers) {
            team.addEntry(target.name)
            player.showEntity(ShadowSMP.instance, target)
            target.isGlowing = true
        }

        object : BukkitRunnable() {
            override fun run() {
                for (target in nearbyPlayers) {
                    target.isGlowing = false
                }
                team.unregister()
                player.sendMessage("${NamedTextColor.GRAY}Your spider senses fade...")
            }
        }.runTaskLater(ShadowSMP.instance, 20L * 30)
    }
}
