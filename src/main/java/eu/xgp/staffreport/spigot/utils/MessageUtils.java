package eu.xgp.staffreport.spigot.utils;

import eu.xgp.staffreport.spigot.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageUtils {
    private static FileConfiguration config = Main.getInstance().getConfig();
    private static ConfigurationSection messageSection = config.getConfigurationSection("messages.spigot");

    public String noPermMessage() {
        return format(messageSection.getString("no_permission"));
    }

    public String playerNotOnlineMessage(String notOnline) {
        return format(messageSection.getString("player_not_online")).replaceAll("%offline", notOnline);
    }

    public String onlyPlayerMessage() {
        return format(messageSection.getString("only_player"));
    }

    public String reportedPlayerMessage(String reporter, String reported, String reason, String world) {
        return format(messageSection.getString("reported_player").replaceAll("%reporter", reporter).replaceAll("%reported", reported).replaceAll("%reason", reason).replaceAll("%world", world));
    }

    public String ssUsageMessage() {
        return format(messageSection.getString("ss_usage"));
    }

    public String reportUsageMessage() {
        return format(messageSection.getString("report_usage"));
    }

    private String format(String s) {
        return ChatColor.translateAlternateColorCodes('&', s).replaceAll("%n", "\n");
    }
}
