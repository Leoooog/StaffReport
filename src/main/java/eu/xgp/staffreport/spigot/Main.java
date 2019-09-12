package eu.xgp.staffreport.spigot;

import eu.xgp.staffreport.commons.utils.NMSUtils;
import eu.xgp.staffreport.spigot.commands.ReportCommand;
import eu.xgp.staffreport.spigot.commands.SSCommand;
import eu.xgp.staffreport.spigot.commands.SSLocCommand;
import eu.xgp.staffreport.spigot.commands.StaffReportCommand;
import eu.xgp.staffreport.spigot.utils.SpigotMessageUtils;
import eu.xgp.staffreport.spigot.utils.SpigotUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    private static NMSUtils nmsUtils;

    public SpigotMessageUtils getMessageUtils() {
        return new SpigotMessageUtils();
    }

    public SpigotUtils getSpigotUtils() {
        return new SpigotUtils();
    }

    public static Main getInstance() {
        return instance;
    }

    public static NMSUtils getNmsUtils() {
        return nmsUtils;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        nmsUtils = new NMSUtils();
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "StaffReport enabled!");
        registerCommands();
    }

    private void registerCommands() {
        getCommand("report").setExecutor(new ReportCommand());
        getCommand("ss").setExecutor(new SSCommand());
        getCommand("staffreport").setExecutor(new StaffReportCommand());
        getCommand("sslocation").setExecutor(new SSLocCommand());
    }
}
