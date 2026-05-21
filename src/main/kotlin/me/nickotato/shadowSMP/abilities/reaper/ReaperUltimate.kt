package me.nickotato.shadowSMP.abilities.reaper

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.manager.AbilityManager
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class ReaperUltimate : Ability(240) {

    override fun execute(player: Player) {

        AbilityManager.trueDamagePlayers.add(player.uniqueId)

        player.sendMessage("§4§oYour next hit against a player will deal true damage... §7unless you wait 10 seconds")
        player.world.playSound(player.location, Sound.ENTITY_WITHER_AMBIENT, 1f, 1.3f)

        val particleTask = object : BukkitRunnable() {
            override fun run() {

                if (!AbilityManager.trueDamagePlayers.contains(player.uniqueId) || !player.isOnline) {
                    cancel()
                    return
                }

                val loc = player.location.add(0.0, 1.0, 0.0)

                player.world.spawnParticle(
                    Particle.SOUL,
                    loc,
                    8,
                    0.35,
                    0.5,
                    0.35,
                    0.01
                )

                player.world.spawnParticle(
                    Particle.LARGE_SMOKE,
                    loc,
                    4,
                    0.25,
                    0.4,
                    0.25,
                    0.01
                )
            }
        }

        particleTask.runTaskTimer(ShadowSMP.instance, 0L, 5L)

        object : BukkitRunnable() {
            override fun run() {

                if (AbilityManager.trueDamagePlayers.contains(player.uniqueId)) {

                    AbilityManager.trueDamagePlayers.remove(player.uniqueId)

                    player.sendMessage("§7§oha ha missed it")

                    player.world.spawnParticle(
                        Particle.SOUL_FIRE_FLAME,
                        player.location.add(0.0, 1.0, 0.0),
                        25,
                        0.4,
                        0.6,
                        0.4,
                        0.03
                    )

                    player.world.playSound(
                        player.location,
                        Sound.BLOCK_FIRE_EXTINGUISH,
                        1f,
                        0.7f
                    )
                }

                particleTask.cancel()
            }
        }.runTaskLater(ShadowSMP.instance, 20L * 10)
    }
}