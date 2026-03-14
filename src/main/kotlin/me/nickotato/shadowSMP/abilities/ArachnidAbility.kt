package me.nickotato.shadowSMP.abilities

import me.nickotato.shadowSMP.ShadowSMP
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class ArachnidAbility : Ability(60) {

    override fun execute(player: Player) {
        val centerBlock = player.location.block

        player.world.spawnParticle(Particle.CLOUD, centerBlock.location.add(0.5, 0.5, 0.5), 30, 0.5, 1.0, 0.5, 0.02)
        player.world.playSound(player.location, Sound.ENTITY_SPIDER_AMBIENT, 1.0f, 1.0f)

        val radius = 30.0
        val world = player.world
        val nearbyPlayers = world.getNearbyEntities(player.location, radius, radius, radius)
            .filterIsInstance<Player>()
//            .filter { it != player && it.hasPotionEffect(org.bukkit.potion.PotionEffectType.INVISIBILITY) }

        if (nearbyPlayers.isEmpty()) return

//        val scoreboard = player.scoreboard
//        val teamName = "arachnid_${player.name.take(10)}"
//
//        scoreboard.getTeam(teamName)?.unregister()
//
//        val team = scoreboard.registerNewTeam(teamName)
//        team.setCanSeeFriendlyInvisibles(true)
//        team.color(NamedTextColor.DARK_PURPLE)
//        team.prefix(Component.text("§5"))

        for (target in nearbyPlayers) {
//            team.addEntry(target.name)
//            player.showEntity(ShadowSMP.instance, target)
//            target.isGlowing = true
            target.addPotionEffect(PotionEffect(PotionEffectType.GLOWING, 20 * 30, 0))
            player.showEntity(ShadowSMP.instance, target)
        }

        object : BukkitRunnable() {
            override fun run() {
//                for (target in nearbyPlayers) {
//                    target.isGlowing = false
//                }
//                team.unregister()
                player.sendMessage("§7Your spider senses fade...")
            }
        }.runTaskLater(ShadowSMP.instance, 20L * 30)
    }
}
