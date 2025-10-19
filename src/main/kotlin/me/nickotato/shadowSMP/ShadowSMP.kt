package me.nickotato.shadowSMP

import me.nickotato.shadowSMP.commands.AbilityCommand
import me.nickotato.shadowSMP.listeners.player.PlayerJoinListener
import me.nickotato.shadowSMP.manager.GuiManager
import org.bukkit.plugin.java.JavaPlugin

class ShadowSMP : JavaPlugin() {

    override fun onEnable() {
        server.pluginManager.registerEvents(PlayerJoinListener(), this)
        server.pluginManager.registerEvents(GuiManager, this)

        getCommand("ability")?.setExecutor(AbilityCommand())
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
