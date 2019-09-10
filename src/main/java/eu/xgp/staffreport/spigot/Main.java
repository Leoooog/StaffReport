package eu.xgp.staffreport.spigot;

import eu.xgp.staffreport.spigot.utils.MessageUtils;
import eu.xgp.staffreport.spigot.utils.NMSUtils;
import eu.xgp.staffreport.spigot.commands.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    private static NMSUtils nmsUtils;
    private static MessageUtils messageUtils;

    public static MessageUtils getMessageUtils() {
        return messageUtils;
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
        messageUtils = new MessageUtils();
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "StaffReport enabled!");
        registerCommands();
    }

    private void registerCommands() {
        getCommand("report").setExecutor(new ReportCommand());
        getCommand("ss").setExecutor(new SSCommand());
    }
}
