package me.nickotato.shadowSMP

import me.nickotato.shadowSMP.commands.*
import me.nickotato.shadowSMP.data.PlayerDataStorage
import me.nickotato.shadowSMP.listeners.entity.*
import me.nickotato.shadowSMP.listeners.ghost.SprigganListener
import me.nickotato.shadowSMP.listeners.item.*
import me.nickotato.shadowSMP.listeners.player.*
import me.nickotato.shadowSMP.manager.AbilityManager
import me.nickotato.shadowSMP.manager.GuiManager
import me.nickotato.shadowSMP.manager.ItemManager
import org.bukkit.plugin.java.JavaPlugin

class ShadowSMP : JavaPlugin() {

    companion object {
        lateinit var instance: ShadowSMP
            private set
    }

    override fun onEnable() {

        instance = this

        server.pluginManager.registerEvents(PlayerJoinListener(), this)
        server.pluginManager.registerEvents(GuiManager, this)
        server.pluginManager.registerEvents(PlayerRightClickListener(), this)
        server.pluginManager.registerEvents(ItemBurnListener(), this)
        server.pluginManager.registerEvents(PlayerFallListener(), this)
        server.pluginManager.registerEvents(ItemDamageListener(), this)
        server.pluginManager.registerEvents(PlayerDeathListener(), this)
        server.pluginManager.registerEvents(PlayerGlideListener(), this)
        server.pluginManager.registerEvents(PlayerBreakListener(), this)
        server.pluginManager.registerEvents(ConsecutiveHitListener(), this)
        server.pluginManager.registerEvents(PlayerDamageListener(), this)
        server.pluginManager.registerEvents(PlayerJumpListener(), this)
        server.pluginManager.registerEvents(PlayerKnockbackListener(), this)
        server.pluginManager.registerEvents(EntityDamage(), this)
        server.pluginManager.registerEvents(PlayerPlaceListener(), this)
        server.pluginManager.registerEvents(EffectListener(), this)
        server.pluginManager.registerEvents(PlayerDataChangeListener(), this)
        server.pluginManager.registerEvents(AbilityReadyListener(), this)

        server.pluginManager.registerEvents(SprigganListener(), this)

        getCommand("ability")?.setExecutor(AbilityCommand())
        getCommand("give_charms")?.setExecutor(GiveCharmsCommand())
        getCommand("withdraw_charm")?.setExecutor(WithdrawCharmCommand())
        getCommand("ultimate")?.setExecutor(UltimateCommand())
        getCommand("give_shadow_items")?.setExecutor(GiveShadowItemsCommand())
        getCommand("reset_cooldown")?.setExecutor(ResetCooldownCommand())
        getCommand("manage")?.setExecutor(ManageCommand())
        getCommand("souls")?.setExecutor(SoulsCommand())
        getCommand("withdraw_soul")?.setExecutor(WithdrawSoulCommand())
        getCommand("charm_ability")?.setExecutor(CharmAbilityCommand())
        getCommand("withdraw_upgrader")?.setExecutor(WithdrawUpgrader())
        getCommand("check_working")?.setExecutor(CheckWorking())
        getCommand("restarting_in")?.setExecutor(RestartingInCommand())
        getCommand("ghost")?.setExecutor(GhostCommand())
        getCommand("config")?.setExecutor(ConfigCommand())

        ItemManager.register(ItemManager.CustomItemInfo("upgrader", indestructible = true))
        ItemManager.register(ItemManager.CustomItemInfo("haunted_dice", indestructible = true))
        ItemManager.register(ItemManager.CustomItemInfo("soul", indestructible = true))
        ItemManager.register(ItemManager.CustomItemInfo("revive_book", indestructible = true))

        AbilityManager.beginTrackingLocations()

        ItemManager.startItemChecks()
    }

    override fun onDisable() {
        PlayerDataStorage.saveAll()
    }
}