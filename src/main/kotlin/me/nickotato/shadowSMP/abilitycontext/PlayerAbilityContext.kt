package me.nickotato.shadowSMP.abilitycontext

import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.World
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

class PlayerAbilityContext(
    val player: Player,
) : AbilityContext {
    override val caster: LivingEntity
        get() = player
    override val world: World
        get() = player.world
    override val location: Location
        get() = player.location

    override fun playSound(sound: Sound, volume: Float, pitch: Float) {
        player.playSound(player.location, sound, volume, pitch)
    }

    override fun sendMessage(message: Component) {
        player.sendMessage(message)
    }

    override fun sendActionBar(message: Component) {
        player.sendActionBar(message)
    }

    override val isValid: Boolean
        get() = !player.isDead && player.isOnline
}