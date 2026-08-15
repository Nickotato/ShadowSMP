package me.nickotato.shadowSMP.abilities.mace

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

object MaceAbility2: Ability(120) {
    override fun execute(context: AbilityContext) {
        val durationSeconds = 10
        val speedLevel = 1

        context.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, durationSeconds * 20, 0, false, false))
        context.addPotionEffect(PotionEffect(PotionEffectType.SPEED, durationSeconds * 20, speedLevel, true, true))

        object : BukkitRunnable() {
            var ticksRun = 0

            init {
                Bukkit.getOnlinePlayers().forEach { other ->
                    if (other != context.caster) other.hidePlayer(ShadowSMP.instance, context.caster as Player)
                }
            }

            override fun run() {
                if (ticksRun >= durationSeconds * 20) {
                    Bukkit.getOnlinePlayers().forEach { other ->
                        if (other != context.caster) other.showPlayer(ShadowSMP.instance, context.caster as Player)
                    }
                    cancel()
                    return
                }

                context.world.spawnParticle(
                    Particle.LARGE_SMOKE,
                    context.location.add(0.0, 1.0, 0.0),
                    5, 0.2, 0.2, 0.2, 0.01
                )

                ticksRun++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)


        context.sendMessage(Component.text("§aYou are now invisible for 10 seconds"))
    }
}