package me.nickotato.shadowSMP.abilities.god

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework
import org.bukkit.inventory.meta.FireworkMeta
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

class GodAbility: Ability(90) {

    override fun execute(context: AbilityContext) {
        val plugin = ShadowSMP.instance

        object : BukkitRunnable() {
            var ticks = 0
            override fun run() {
                if (ticks > 100) {
                    cancel()
                    return
                }
                context.world.spawnParticle(
                    Particle.END_ROD,
                    context.location.add(0.0, 1.0, 0.0),
                    50, 1.0, 1.0, 1.0, 0.1
                )
                context.world.spawnParticle(
                    Particle.SOUL_FIRE_FLAME,
                    context.location.add(0.0, 1.0, 0.0),
                    30, 1.0, 1.0, 1.0, 0.05
                )
                ticks++
            }
        }.runTaskTimer(plugin, 0L, 2L)

        repeat(10) {
            val firework = context.world.spawnEntity(context.location, EntityType.FIREWORK_ROCKET) as Firework
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

        context.world.playSound(context.location, Sound.ENTITY_ENDER_DRAGON_GROWL, 5f, 1f)
        context.world.playSound(context.location, Sound.ENTITY_WITHER_SPAWN, 5f, 1f)

        context.addPotionEffect(org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.SPEED, 200, 4))
        context.addPotionEffect(org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.RESISTANCE, 200, 4))
        context.addPotionEffect(org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.JUMP_BOOST, 200, 4))

        object : BukkitRunnable() {
            var meteors = 0
            override fun run() {
                if (meteors > 20) {
                    cancel()
                    return
                }
                val loc = context.location.clone().add(
                    Random.nextDouble(-10.0, 10.0),
                    20.0,
                    Random.nextDouble(-10.0, 10.0)
                )
                context.world.spawnParticle(Particle.FLAME, loc, 20, 0.5, 0.5, 0.5, 0.1)
                context.world.strikeLightningEffect(loc)
                meteors++
            }
        }.runTaskTimer(plugin, 0L, 5L)
    }
}
