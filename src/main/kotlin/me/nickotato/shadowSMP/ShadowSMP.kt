package me.nickotato.shadowSMP

import me.nickotato.shadowSMP.commands.AbilityCommand
import me.nickotato.shadowSMP.commands.GiveCharmsCommand
import me.nickotato.shadowSMP.commands.UltimateCommand
import me.nickotato.shadowSMP.commands.WithdrawCharmCommand
import me.nickotato.shadowSMP.listeners.player.PlayerJoinListener
import me.nickotato.shadowSMP.listeners.player.PlayerRightClickListener
import me.nickotato.shadowSMP.manager.GuiManager
import org.bukkit.plugin.java.JavaPlugin

class ShadowSMP : JavaPlugin() {

    override fun onEnable() {
        server.pluginManager.registerEvents(PlayerJoinListener(), this)
        server.pluginManager.registerEvents(GuiManager, this)
        server.pluginManager.registerEvents(PlayerRightClickListener(), this)

        getCommand("ability")?.setExecutor(AbilityCommand())
        getCommand("give_charms")?.setExecutor(GiveCharmsCommand())
        getCommand("withdraw_charm")?.setExecutor(WithdrawCharmCommand())
        getCommand("ultimate")?.setExecutor(UltimateCommand())
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
