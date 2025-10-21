package me.nickotato.shadowSMP

import me.nickotato.shadowSMP.commands.AbilityCommand
import me.nickotato.shadowSMP.commands.GiveCharmsCommand
import me.nickotato.shadowSMP.commands.GiveShadowItemsCommand
import me.nickotato.shadowSMP.commands.ManageCommand
import me.nickotato.shadowSMP.commands.ResetCooldownCommand
import me.nickotato.shadowSMP.commands.SoulsCommand
import me.nickotato.shadowSMP.commands.UltimateCommand
import me.nickotato.shadowSMP.commands.WithdrawCharmCommand
import me.nickotato.shadowSMP.data.PlayerDataStorage
import me.nickotato.shadowSMP.listeners.item.ItemBurnListener
import me.nickotato.shadowSMP.listeners.item.ItemDamageListener
import me.nickotato.shadowSMP.listeners.player.PlayerDeathListener
import me.nickotato.shadowSMP.listeners.player.PlayerFallListener
import me.nickotato.shadowSMP.listeners.player.PlayerJoinListener
import me.nickotato.shadowSMP.listeners.player.PlayerRightClickListener
import me.nickotato.shadowSMP.manager.EffectManager
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

        getCommand("ability")?.setExecutor(AbilityCommand())
        getCommand("give_charms")?.setExecutor(GiveCharmsCommand())
        getCommand("withdraw_charm")?.setExecutor(WithdrawCharmCommand())
        getCommand("ultimate")?.setExecutor(UltimateCommand())
        getCommand("give_shadow_items")?.setExecutor(GiveShadowItemsCommand())
        getCommand("reset_cooldown")?.setExecutor(ResetCooldownCommand())
        getCommand("manage")?.setExecutor(ManageCommand())
        getCommand("souls")?.setExecutor(SoulsCommand())

        ItemManager.register(ItemManager.CustomItemInfo("upgrader", indestructible = true))
        ItemManager.register(ItemManager.CustomItemInfo("haunted_dice", indestructible = true))
        ItemManager.register(ItemManager.CustomItemInfo("soul", indestructible = true))

        EffectManager.startEffectLoop()
    }

    override fun onDisable() {
        PlayerDataStorage.saveAll()
    }
}
