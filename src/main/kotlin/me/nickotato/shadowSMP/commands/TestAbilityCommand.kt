package me.nickotato.shadowSMP.commands

import me.nickotato.shadowSMP.abilities.Ability
import me.nickotato.shadowSMP.abilities.arachnid.ArachnidAbility
import me.nickotato.shadowSMP.abilities.arachnid.ArachnidUltimate
import me.nickotato.shadowSMP.abilities.banshee.BansheeAbility
import me.nickotato.shadowSMP.abilities.banshee.BansheeUltimate
import me.nickotato.shadowSMP.abilities.chronomancer.ChronomancerAbility
import me.nickotato.shadowSMP.abilities.chronomancer.ChronomancerUltimate
import me.nickotato.shadowSMP.abilities.deogen.DeogenAbility
import me.nickotato.shadowSMP.abilities.deogen.DeogenUltimate
import me.nickotato.shadowSMP.abilities.golem.GolemAbility
import me.nickotato.shadowSMP.abilities.golem.GolemUltimate
import me.nickotato.shadowSMP.abilities.ignis.IgnisAbility
import me.nickotato.shadowSMP.abilities.ignis.IgnisUltimate
import me.nickotato.shadowSMP.abilities.jinn.JinnAbility
import me.nickotato.shadowSMP.abilities.jinn.JinnUltimate
import me.nickotato.shadowSMP.abilities.oni.OniAbility
import me.nickotato.shadowSMP.abilities.oni.OniUltimate
import me.nickotato.shadowSMP.abilities.reaper.ReaperAbility
import me.nickotato.shadowSMP.abilities.reaper.ReaperUltimate
import me.nickotato.shadowSMP.abilities.spirit.SpiritAbility
import me.nickotato.shadowSMP.abilities.spirit.SpiritUltimate
import me.nickotato.shadowSMP.abilities.spriggan.SprigganAbility
import me.nickotato.shadowSMP.abilities.spriggan.SprigganUltimate
import me.nickotato.shadowSMP.abilities.timekeeper.TimeKeeperAbility
import me.nickotato.shadowSMP.abilities.timekeeper.TimeKeeperUltimate
import me.nickotato.shadowSMP.abilities.titan.TitanAbility
import me.nickotato.shadowSMP.abilities.titan.TitanUltimate
import me.nickotato.shadowSMP.abilitycontext.AbilityContext
import me.nickotato.shadowSMP.abilitycontext.PlayerAbilityContext
import net.kyori.adventure.text.Component
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Mob
import org.bukkit.entity.Player
import kotlin.math.cos
import kotlin.math.sin

class TestAbilityCommand : CommandExecutor {

    companion object {
        private const val TEST_TAG = "shadow_smp_ability_test"
    }

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {

        if (sender !is Player) {
            sender.sendMessage("§cThis command can only be used by a player.")
            return true
        }

        if (args.isEmpty()) {
            sendUsage(sender)
            return true
        }

        if (args[0].equals("clear", ignoreCase = true)) {
            clearTestEntities(sender)
            return true
        }

        if (args.size < 3) {
            sendUsage(sender)
            return true
        }

        val ghost = args[0].lowercase()

        val ultimate = args[1].toBooleanStrictOrNull()

        if (ultimate == null) {
            sender.sendMessage("§cUltimate must be §ftrue §cor §ffalse§c.")
            return true
        }

        val ability = getAbility(ghost, ultimate)

        if (ability == null) {
            sender.sendMessage("§cUnknown ghost: §f$ghost")
            return true
        }

        val casterType = args[2].lowercase()

        val casterContext = createCasterContext(
            sender,
            casterType
        )

        if (casterContext == null) {
            sender.sendMessage("§cUnknown caster: §f$casterType")
            sender.sendMessage(
                "§7Available casters: player, villager, zombie, skeleton, spider, creeper, enderman"
            )
            return true
        }

        val requiresTargets = requiresTargets(ghost, ultimate)

        val targetCount = if (requiresTargets) {
            if (args.size < 4) {
                sender.sendMessage(
                    "§cUsage: /testability $ghost $ultimate $casterType <targets>"
                )
                return true
            }

            val count = args[3].toIntOrNull()

            if (count == null || count < 0) {
                sender.sendMessage("§cTarget count must be a positive number.")
                return true
            }

            count
        } else {
            null
        }

        if (targetCount != null) {
            spawnTestTargets(sender, targetCount)
        }

        sender.sendMessage(
            "§7Testing §f$ghost §7as §f$casterType §7(ultimate=$ultimate)"
        )

        ability.execute(casterContext)

        return true
    }

    private fun getAbility(
        ghost: String,
        ultimate: Boolean
    ): Ability? {
        if (ultimate) {
            return when (ghost) {
                "jinn" -> JinnUltimate()
                "arachnid" -> ArachnidUltimate()
                "banshee" -> BansheeUltimate()
                "chronomancer" -> ChronomancerUltimate()
                "deogen" -> DeogenUltimate()
                "golem" -> GolemUltimate()
                "ignis" -> IgnisUltimate()
                "oni" -> OniUltimate()
                "reaper" -> ReaperUltimate()
                "spirit" -> SpiritUltimate()
                "spriggan" -> SprigganUltimate()
                "timekeeper" -> TimeKeeperUltimate()
                "titan" -> TitanUltimate()
                else -> null
            }

        }
        return when (ghost) {
            "jinn" -> JinnAbility()
            "arachnid" -> ArachnidAbility()
            "banshee" -> BansheeAbility()
            "chronomancer" -> ChronomancerAbility()
            "deogen" -> DeogenAbility()
            "golem" -> GolemAbility()
            "ignis" -> IgnisAbility()
            "oni" -> OniAbility()
            "reaper" -> ReaperAbility()
            "spirit" -> SpiritAbility()
            "spriggan" -> SprigganAbility()
            "timekeeper" -> TimeKeeperAbility()
            "titan" -> TitanAbility()
            else -> null
        }
    }

    private fun createCasterContext(
        player: Player,
        casterType: String
    ): AbilityContext? {

        if (casterType == "player") {
            return PlayerAbilityContext(player)
        }

        val entityType = when (casterType) {
            "villager" -> EntityType.VILLAGER
            "zombie" -> EntityType.ZOMBIE
            "skeleton" -> EntityType.SKELETON
            "spider" -> EntityType.SPIDER
            "creeper" -> EntityType.CREEPER
            "enderman" -> EntityType.ENDERMAN
            else -> return null
        }

        val entity = player.world.spawnEntity(
            player.location.clone().add(0.0, 0.0, 2.0),
            entityType
        )

        if (entity !is LivingEntity) {
            entity.remove()
            return null
        }

        entity.addScoreboardTag(TEST_TAG)

        return object : AbilityContext {

            override val caster: LivingEntity = entity

            override fun playSound(
                sound: org.bukkit.Sound,
                volume: Float,
                pitch: Float
            ) {
                entity.world.playSound(
                    entity.location,
                    sound,
                    volume,
                    pitch
                )
            }

            override fun sendMessage(
                message: Component
            ) {
                // Non-player entities can't receive messages.
            }

            override fun sendActionBar(
                message: Component
            ) {
                // Non-player entities can't receive action bars.
            }
        }
    }

    private fun spawnTestTargets(
        player: Player,
        amount: Int
    ) {
        val world = player.world
        val radius = 5.0

        repeat(amount) { index ->

            val angle = (2.0 * Math.PI * index) / amount

            val x = cos(angle) * radius
            val z = sin(angle) * radius

            val location = player.location.clone().add(x, 0.0, z)

            val highest = world.getHighestBlockAt(
                location.blockX,
                location.blockZ
            )

            val spawnLocation = highest.location
                .add(0.5, 1.0, 0.5)

            val entity = world.spawnEntity(
                spawnLocation,
                EntityType.HUSK
            )

            if (entity is LivingEntity) {
                entity.addScoreboardTag(TEST_TAG)

                if (entity is Mob) {
                    entity.isAware = false
                }
            }
        }
    }

    private fun clearTestEntities(player: Player) {
        var removed = 0

        player.world.entities
            .filter { TEST_TAG in it.scoreboardTags }
            .forEach {
                it.remove()
                removed++
            }

        player.sendMessage("§aRemoved §f$removed §atest entities.")
    }

    private fun sendUsage(player: Player) {
        player.sendMessage("§c/testability <ghost> <ultimate> <caster> [params]")
        player.sendMessage("")
        player.sendMessage("§7Casters:")
        player.sendMessage(
            "§fplayer, villager, zombie, skeleton, spider, creeper, enderman"
        )
        player.sendMessage("")
        player.sendMessage("§7Examples:")
        player.sendMessage("§f/testability jinn false player 10")
        player.sendMessage("§f/testability jinn false villager 10")
        player.sendMessage("§f/testability jinn true zombie 10")
        player.sendMessage("§f/testability clear")
    }

    private fun requiresTargets(
        ghost: String,
        ultimate: Boolean
    ): Boolean {

        if (!ultimate) {
            return when (ghost) {
                "ignis" -> true
                "jinn" -> true
                else -> false
            }
        }

        return when (ghost) {
            "jinn",
            "arachnid",
            "chronomancer",
            "golem",
            "ignis",
            "oni",
            "timekeeper",
            "titan" -> true

            else -> false
        }
    }
}