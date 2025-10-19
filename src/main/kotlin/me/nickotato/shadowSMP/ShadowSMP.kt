package me.nickotato.shadowSMP

import me.nickotato.shadowSMP.commands.AbilityCommand
import me.nickotato.shadowSMP.commands.GiveCharmsCommand
import me.nickotato.shadowSMP.commands.GiveShadowItemsCommand
import me.nickotato.shadowSMP.commands.UltimateCommand
import me.nickotato.shadowSMP.commands.WithdrawCharmCommand
import me.nickotato.shadowSMP.listeners.item.ItemBurnListener
import me.nickotato.shadowSMP.listeners.item.ItemDamageListener
import me.nickotato.shadowSMP.listeners.player.PlayerFallListener
import me.nickotato.shadowSMP.listeners.player.PlayerJoinListener
import me.nickotato.shadowSMP.listeners.player.PlayerRightClickListener
import me.nickotato.shadowSMP.manager.GuiManager
import me.nickotato.shadowSMP.manager.ItemManager
import me.nickotato.shadowSMP.utils.ItemUtils
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

        getCommand("ability")?.setExecutor(AbilityCommand())
        getCommand("give_charms")?.setExecutor(GiveCharmsCommand())
        getCommand("withdraw_charm")?.setExecutor(WithdrawCharmCommand())
        getCommand("ultimate")?.setExecutor(UltimateCommand())
        getCommand("give_shadow_items")?.setExecutor(GiveShadowItemsCommand())

        ItemManager.register(ItemManager.CustomItemInfo("upgrader", unburnable = true))
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
