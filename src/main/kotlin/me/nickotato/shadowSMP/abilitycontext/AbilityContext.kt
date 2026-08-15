package me.nickotato.shadowSMP.abilitycontext

import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.potion.PotionEffect

interface AbilityContext {
    val caster: LivingEntity
    val world: World
        get() = caster.world
    val location: Location
        get() = caster.location

    fun damage(amount: Double) {
        caster.damage(amount)
    }
    fun playSound(sound: Sound, volume: Float, pitch: Float)
    fun sendMessage(message: Component)

    fun sendActionBar(message: Component)

    fun addPotionEffect(effect: PotionEffect) {
        caster.addPotionEffect(effect)
    }

    fun nearbyEntities(x: Double, y: Double, z: Double): List<Entity> =
        caster.getNearbyEntities(x, y, z)

    val isValid: Boolean
        get() = !caster.isDead
}