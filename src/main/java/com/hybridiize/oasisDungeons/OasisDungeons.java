package com.hybridiize.oasisDungeons; // This must match the folder structure

import com.hybridiize.oasisDungeons.commands.AdminCommand;
import com.hybridiize.oasisDungeons.managers.DungeonTemplateManager;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public final class OasisDungeons extends JavaPlugin {

    private static OasisDungeons instance;
    private static Logger logger;
    private DungeonTemplateManager dungeonTemplateManager;

    @Override
    public void onEnable() {
        instance = this;
        logger = this.getLogger();

        // Plugin startup logic
        logger.info("OasisDungeons is enabling...");

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // Initialize managers
        dungeonTemplateManager = new DungeonTemplateManager(this);
        dungeonTemplateManager.loadTemplates();

        AdminCommand adminCommandExecutor = new AdminCommand(this);
        getCommand("odadmin").setExecutor(adminCommandExecutor);
        getCommand("odadmin").setTabCompleter(adminCommandExecutor);
        logger.info("Registered admin commands.");

        // TODO: Register event listeners
        // TODO: Initialize managers (DungeonTemplateManager, DungeonInstanceManager, etc.)

        logger.info("OasisDungeons has been enabled successfully!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("OasisDungeons is disabling...");

        // TODO: Save any pending data
        // TODO: Clean up resources

        logger.info("OasisDungeons has been disabled.");
    }

    public static OasisDungeons getInstance() {
        return instance;
    }

    // You can add a static getter for the logger if you find it convenient
    public static Logger getPluginLogger() {
        return logger;
    }

    public DungeonTemplateManager getDungeonTemplateManager() {
        return dungeonTemplateManager;
    }
}