package me.nickotato.shadowSMP.abilities.arachnid

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import net.kyori.adventure.text.Component
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class ArachnidAbility : Ability(60) {

    override fun execute(context: AbilityContext) {
        val world = context.world
        val centerBlock = context.location.block

        world.spawnParticle(Particle.CLOUD, centerBlock.location.add(0.5, 0.5, 0.5), 30, 0.5, 1.0, 0.5, 0.02)
        world.playSound(context.location, Sound.ENTITY_SPIDER_AMBIENT, 1.0f, 1.0f)

        val radius = 30.0

        val nearbyPlayers = world.getNearbyEntities(context.location, radius, radius, radius)
            .filterIsInstance<Player>()

        if (nearbyPlayers.isEmpty()) return


        for (target in nearbyPlayers) {
            target.addPotionEffect(PotionEffect(PotionEffectType.GLOWING, 20 * 30, 0))
//            player.showEntity(ShadowSMP.instance, target)
        }

        object : BukkitRunnable() {
            override fun run() {
                context.sendMessage(Component.text("§7Your spider senses fade..."))
            }
        }.runTaskLater(ShadowSMP.instance, 20L * 30)
    }
}
