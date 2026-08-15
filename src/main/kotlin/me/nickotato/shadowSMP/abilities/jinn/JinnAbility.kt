package me.nickotato.shadowSMP.abilities.jinn

import me.nickotato.shadowSMP.ShadowSMP
import me.nickotato.shadowSMP.abilities.Ability
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.World
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin

class JinnAbility : Ability(90) {

    companion object {
        const val CHARGE_TICKS = 20 * 3
        const val RANGE = 10.0
        const val DAMAGE_PER_ENTITY = 2.0
    }

    override fun execute(player: Player) {
        val world = player.world

        world.playSound(player.location, Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1.5f, 0.6f)
        world.playSound(player.location, Sound.BLOCK_BEACON_POWER_SELECT, 1.2f, 0.5f)

        player.velocity = Vector(0.0, 0.35, 0.0)

        object : BukkitRunnable() {
            var tick = 0

            override fun run() {
                if (!player.isOnline || player.isDead) {
                    cancel()
                    return
                }

                player.velocity = Vector(0.0, 0.08, 0.0)

                playActivationParticles(player, tick)

                if (tick % 20 == 0) {
                    world.playSound(player.location, Sound.BLOCK_BEACON_AMBIENT, 1.0f, 0.7f + (tick / CHARGE_TICKS.toFloat()) * 0.5f)
                }

                tick++

                if (tick >= CHARGE_TICKS) {
                    cancel()
                    activate(player)
                }
            }

        }.runTaskTimer(ShadowSMP.instance, 0L, 1L)
    }

    private fun playActivationParticles(player: Player, tick: Int) {
        val world = player.world
        val center = player.location.clone().add(0.0, 1.0, 0.0)
        val progress = tick.toDouble() / CHARGE_TICKS

        playOuterRing(world, center, tick, progress)
        playSoulParticles(world, center)
        playInnerRing(world, center, tick, progress)
        playRisingParticles(world, center)
    }

    private fun playOuterRing( world: World, center: Location, tick: Int, progress: Double) {
        val radius = 1.0 + progress * 5.0
        val rotation = Math.toRadians(tick * 8.0)

        val dust = Particle.DustOptions(
            Color.fromRGB(0, 255, 255),
            2.0f
        )

        for (i in 0 until 36) {
            val angle = Math.toRadians(i * 10.0) + rotation

            val x = cos(angle) * radius
            val z = sin(angle) * radius

            val point = center.clone().add(x, 0.0, z)

            world.spawnParticle(
                Particle.DUST,
                point,
                2,
                0.05,
                0.05,
                0.05,
                dust
            )

            world.spawnParticle(
                Particle.END_ROD,
                point,
                1,
                0.0,
                0.0,
                0.0,
                0.0
            )
        }
    }

    private fun playSoulParticles(world: World, center: Location) {
        world.spawnParticle(
            Particle.SOUL,
            center,
            15,
            0.8,
            1.2,
            0.8,
            0.03
        )
    }


    private fun playInnerRing(world: World, center: Location, tick: Int, progress: Double) {
        val radius = 0.8 + progress * 2.0
        val rotation = Math.toRadians(-tick * 12.0)

        val dust = Particle.DustOptions(
            Color.fromRGB(80, 180, 255),
            1.5f
        )

        for (i in 0 until 24) {
            val angle = Math.toRadians(i * 15.0) + rotation

            val x = cos(angle) * radius
            val z = sin(angle) * radius

            world.spawnParticle(
                Particle.DUST,
                center.clone().add(x, 0.0, z),
                2,
                0.03,
                0.03,
                0.03,
                dust
            )
        }
    }

    private fun playRisingParticles(world: World, center: Location) {
        world.spawnParticle(
            Particle.END_ROD,
            center.clone().subtract(0.0, 1.0, 0.0),
            8,
            0.4,
            0.1,
            0.4,
            0.03
        )
    }

    private fun activate(player: Player) {
        val world = player.world

       playActivateSoundsAndParticles(player, world)

        val entities = player.getNearbyEntities(
            RANGE,
            RANGE,
            RANGE
        ).filterIsInstance<LivingEntity>()
            .filter { it != player }

        if (entities.isEmpty()) {
            return
        }

        /*
         * 2 damage per affected entity.
         *
         * The player receives 1/4 of the TOTAL damage dealt.
         *
         * Example:
         *  - 1 entity = 2 damage dealt -> player takes 0.5
         *  - 3 entities = 6 damage dealt -> player takes 1.5
         *  - 5 entities = 10 damage dealt -> player takes 2.5
         */

        val damage = DAMAGE_PER_ENTITY * entities.size
        val recoilDamage = damage / 4.0

        for (entity in entities) {
            val direction = entity.location.toVector()
                .subtract(player.location.toVector())
                .normalize()

            val distance = player.location.distance(entity.location)

            for (i in 0..distance.toInt()) {
                val point = player.location.clone()
                    .add(direction.clone().multiply(i.toDouble()))
                    .add(0.0, 1.0, 0.0)

                world.spawnParticle(
                    Particle.DUST,
                    point,
                    4,
                    0.05,
                    0.05,
                    0.05,
                    Particle.DustOptions(
                        Color.AQUA,
                        1.5f
                    )
                )

                world.spawnParticle(
                    Particle.END_ROD,
                    point,
                    1,
                    0.05,
                    0.05,
                    0.05,
                    0.0
                )
            }

            entity.velocity = Vector(0.0, 0.0, 0.0)

            entity.damage(damage, player)

            playImpactParticles(world, entity)

        }

        player.damage(recoilDamage)
    }

    private fun playActivateSoundsAndParticles(player: Player, world: World) {
        world.playSound(
            player.location,
            Sound.ENTITY_GENERIC_EXPLODE,
            1.5f,
            1.2f
        )

        world.playSound(
            player.location,
            Sound.ENTITY_ENDER_DRAGON_SHOOT,
            1.5f,
            0.8f
        )

        world.playSound(
            player.location,
            Sound.BLOCK_BEACON_ACTIVATE,
            1.5f,
            0.6f
        )

        world.spawnParticle(
            Particle.EXPLOSION_EMITTER,
            player.location.clone().add(0.0, 1.0, 0.0),
            1
        )

        world.spawnParticle(
            Particle.END_ROD,
            player.location.clone().add(0.0, 1.0, 0.0),
            100,
            2.0,
            2.0,
            2.0,
            0.1
        )

        world.spawnParticle(
            Particle.SOUL,
            player.location.clone().add(0.0, 1.0, 0.0),
            100,
            2.0,
            2.0,
            2.0,
            0.05
        )
    }

    private fun playImpactParticles(world: World, entity: LivingEntity) {
        world.spawnParticle(
            Particle.SOUL,
            entity.location.clone().add(0.0, 1.0, 0.0),
            30,
            0.5,
            0.8,
            0.5,
            0.05
        )

        world.spawnParticle(
            Particle.END_ROD,
            entity.location.clone().add(0.0, 1.0, 0.0),
            15,
            0.4,
            0.7,
            0.4,
            0.05
        )
    }

}
