package eu.xgp.staffreport.bungee.utils;

import eu.xgp.staffreport.bungee.Main;
import eu.xgp.staffreport.commons.utils.MessageUtils;
import net.md_5.bungee.config.Configuration;

public class BungeeMessageUtils implements MessageUtils {
    private Configuration config;

    public BungeeMessageUtils() {
        Main.getInstance().loadConfig();
        config = Main.getInstance().getConfig().getSection("messages.bungee");
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

    @Override
    public String ssUsageMessage() {
        return format(config.getString("ss_usage"));
    }


    @Override
    public String reportUsageMessage() {
        return format(config.getString("report_usage"));
    }

    @Override
    public String ssStatusMessage(String staff) {
        return format(config.getString("screenshare_status").replaceAll("%staff", staff));
    }

    public String reportedPlayerMessage(String reporter, String reported, String server, String reason) {
        return format(config.getString("reported_player").replaceAll("%reporter", reporter).replaceAll("%reported", reported).replaceAll("%reason", reason).replaceAll("%server", server));
    }

    public String serverSavedMessage() {
        return format(config.getString("server_saved"));
    }
}
