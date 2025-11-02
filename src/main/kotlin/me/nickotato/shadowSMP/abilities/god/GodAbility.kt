package me.nickotato.shadowSMP.abilities.god

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.FireworkMeta
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

class GodAbility: Ability(90) {

    override fun execute(player: Player) {
        val plugin = ShadowSMP.instance

        // Step 1: Godly Aura (particles + glow)
        object : BukkitRunnable() {
            var ticks = 0
            override fun run() {
                if (ticks > 100) { // 5 seconds of aura
                    cancel()
                    return
                }
                player.world.spawnParticle(
                    Particle.END_ROD,
                    player.location.add(0.0, 1.0, 0.0),
                    50, 1.0, 1.0, 1.0, 0.1
                )
                player.world.spawnParticle(
                    Particle.SOUL_FIRE_FLAME,
                    player.location.add(0.0, 1.0, 0.0),
                    30, 1.0, 1.0, 1.0, 0.05
                )
                ticks++
            }
        }.runTaskTimer(plugin, 0L, 2L)

        // Step 2: Spawn random firework chaos
        repeat(10) {
            val firework = player.world.spawnEntity(player.location, EntityType.FIREWORK_ROCKET) as Firework
            val meta: FireworkMeta = firework.fireworkMeta
            meta.addEffect(
                FireworkEffect.builder()
                    .withColor(Color.AQUA, Color.PURPLE, Color.FUCHSIA)
                    .withFade(Color.WHITE)
                    .with(FireworkEffect.Type.BALL_LARGE)
                    .trail(true)
                    .flicker(true)
                    .build()
            )
            meta.power = 2
            firework.fireworkMeta = meta
        }

        // Step 3: Sound and shockwave
        player.world.playSound(player.location, Sound.ENTITY_ENDER_DRAGON_GROWL, 5f, 1f)
        player.world.playSound(player.location, Sound.ENTITY_WITHER_SPAWN, 5f, 1f)

        // Step 4: Optional overpowered effects
        player.addPotionEffect(org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.SPEED, 200, 4))
        player.addPotionEffect(org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.RESISTANCE, 200, 4))
        player.addPotionEffect(org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.JUMP_BOOST, 200, 4))

        // Step 5: Meteor rain (crazy visual)
        object : BukkitRunnable() {
            var meteors = 0
            override fun run() {
                if (meteors > 20) {
                    cancel()
                    return
                }
                val loc = player.location.clone().add(
                    Random.nextDouble(-10.0, 10.0),
                    20.0,
                    Random.nextDouble(-10.0, 10.0)
                )
                player.world.spawnParticle(Particle.FLAME, loc, 20, 0.5, 0.5, 0.5, 0.1)
                player.world.strikeLightningEffect(loc)
                meteors++
            }
        }.runTaskTimer(plugin, 0L, 5L)
    }
}
