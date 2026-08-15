package me.nickotato.shadowSMP.abilities.deogen

import me.nickotato.shadowSMP.abilities.Ability
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import me.nickotato.shadowSMP.manager.AbilityManager
import me.nickotato.shadowSMP.utils.EntityUtils
import net.kyori.adventure.text.Component

class DeogenUltimate: Ability(90) {

    private val hitCount = 20
    private val hitInterval = 5L
    private val damagePerHit = 12.0

    override fun execute(context: AbilityContext) {
        val target = getNearestEntity(context)
        if (target == null) {
            context.sendMessage(Component.text("§cNo target nearby!"))
            return
        }

        context.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, (hitCount * hitInterval + 20).toInt(), 1, false, false, true))
        if (target is LivingEntity) {
            target.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, (hitCount * hitInterval + 20).toInt(), 1, false, false, true))
        }

        AbilityManager.tempNoFallPlayers.add(context.caster.uniqueId)

        object : BukkitRunnable() {
            var hits = 0

            override fun run() {
                if (hits >= hitCount || !target.isValid || !context.isValid) {
                    this.cancel()
                    return
                }

                context.caster.teleport(context.location.clone().apply { direction = target.location.toVector().subtract(context.location.toVector()) })
                if (target is LivingEntity) {
                    target.teleport(target.location.clone().apply { direction = context.location.toVector().subtract(target.location.toVector()) })
                }

                val offsetX = (Math.random() - 0.5) * 1.5
                val offsetZ = (Math.random() - 0.5) * 1.5
                val targetLocation: Location = target.location.clone().add(offsetX, 0.0, offsetZ)
                context.caster.teleport(targetLocation)

                if (target is LivingEntity) {
                    target.damage(damagePerHit, context.caster)
                    target.velocity = target.velocity.setY(0.5)
                }

                context.world.playSound(context.location, Sound.ENTITY_PLAYER_ATTACK_STRONG, 1.0f, 1.0f)
                target.world.playSound(target.location, Sound.ENTITY_PLAYER_HURT, 1.0f, 1.0f)

                hits++
            }
        }.runTaskTimer(ShadowSMP.instance, 0L, hitInterval)
    }

    private fun getNearestEntity(context: AbilityContext): Entity? {
        return context.nearbyEntities(10.0, 5.0, 10.0)
            .asSequence()
            .filterIsInstance<LivingEntity>()
            .filter { it != context.caster }
            .filter { it !is Player || it.gameMode != org.bukkit.GameMode.SPECTATOR }
            .filter { !EntityUtils.isImmune(it.type) }
            .minByOrNull { it.location.distanceSquared(context.location) }
    }
}
