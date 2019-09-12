package eu.xgp.staffreport.bungee;

import eu.xgp.staffreport.bungee.commands.ReportCommand;
import eu.xgp.staffreport.bungee.commands.SSCommand;
import eu.xgp.staffreport.bungee.commands.SSServerComamnd;
import eu.xgp.staffreport.bungee.commands.StaffReportCommand;
import eu.xgp.staffreport.bungee.utils.BungeeMessageUtils;
import eu.xgp.staffreport.bungee.utils.BungeeUtils;
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
    private Configuration config;

    public BungeeMessageUtils getMessageUtils() {
        return new BungeeMessageUtils();
    }

    public static Main getInstance() {
        return instance;
    }

    public Configuration getConfig() {
        return config;
    }

    public BungeeUtils getBungeeUtils() {
        return new BungeeUtils();
    }

    @Override
    public void onEnable() {
        instance = this;
        if (loadConfig()) {
            registerCommands();
            getLogger().info("StaffReport config loaded");
        }
        getLogger().info("StaffReport has loaded!");
    }

    private void registerCommands() {
        getProxy().getPluginManager().registerCommand(this, new ReportCommand("report"));
        getProxy().getPluginManager().registerCommand(this, new SSCommand("ss"));
        getProxy().getPluginManager().registerCommand(this, new StaffReportCommand("staffreport"));
        getProxy().getPluginManager().registerCommand(this, new SSServerComamnd("ssserver"));
    }

    public void saveConfig(Configuration con) {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(con, new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean loadConfig() {
        try {
            if (!getDataFolder().exists())
                getDataFolder().mkdir();
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                InputStream in = getResourceAsStream("config.yml");
                Files.copy(in, file.toPath());
            }
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Cannot load config.yml, disabling commands...");
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
