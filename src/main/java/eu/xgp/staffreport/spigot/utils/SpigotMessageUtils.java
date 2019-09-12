package eu.xgp.staffreport.spigot.utils;

import eu.xgp.staffreport.commons.utils.MessageUtils;
import eu.xgp.staffreport.spigot.Main;
import org.bukkit.configuration.ConfigurationSection;

public class SpigotMessageUtils implements MessageUtils {
    private static ConfigurationSection config;

    public SpigotMessageUtils() {
        Main.getInstance().reloadConfig();
        config = Main.getInstance().getConfig().getConfigurationSection("messages.spigot");
    }

    @Override
    public String noPermMessage() {
        return format(config.getString("no_permission"));
    }

    @Override
    public String playerNotOnlineMessage(String notOnline) {
        return format(config.getString("player_not_online").replaceAll("%offline", notOnline));
    }

    @Override
    public String onlyPlayerMessage() {
        return format(config.getString("only_players"));
    }

    public String reportedPlayerMessage(String reporter, String reported, String reason, String world) {
        return format(config.getString("reported_player").replaceAll("%reporter", reporter).replaceAll("%reported", reported).replaceAll("%reason", reason).replaceAll("%world", world));
    }

    public String locSavedMessage() {
        return format(config.getString("location_saved"));
    }

    @Override
    public String ssUsageMessage() {
        return format(config.getString("ss_usage"));
    }

    @Override
    public String reportUsageMessage() {
        return format(config.getString("report_usage"));
    }

    @Override
    public String youReportedMessage(String reported, String reason) {
        return format(config.getString("you_reported")).replaceAll("%reported", reported).replaceAll("%reason", reason);
    }

    @Override
    public String ssStatusMessage(String staff) {
        return format(config.getString("screenshare_status")).replaceAll("%staff", staff);
    }

}
