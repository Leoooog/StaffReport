package eu.xgp.staffreport.bungee;

import eu.xgp.staffreport.bungee.commands.ReportCommand;
import eu.xgp.staffreport.bungee.commands.SSCommand;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;

public class Main extends Plugin {
    private static Main instance;
    private static Configuration config;

    public static Main getInstance() {
        return instance;
    }

    public static Configuration getConfig() {
        return config;
    }

    @Override
    public void onEnable() {
        instance = this;
        if (loadConfig())
            registerCommands();
        getLogger().info("StaffReport has loaded!");
    }

    private void registerCommands() {
        getProxy().getPluginManager().registerCommand(this, new ReportCommand("report"));
        getProxy().getPluginManager().registerCommand(this, new SSCommand("ss"));
    }

    private boolean loadConfig() {
        try {
            if (!getDataFolder().exists())
                getDataFolder().mkdir();
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                InputStream in = getResourceAsStream("config.yml");
                Files.copy(in, file.toPath());
            }
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            getLogger().info("config loaded");
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Cannot load config.yml, disabling plugin... Commands will not be available");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void onDisable() {
        getProxy().getPluginManager().unregisterCommand(new ReportCommand("report"));
        getProxy().getPluginManager().unregisterCommand(new SSCommand("ss"));
    }
}
