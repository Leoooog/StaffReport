package eu.xgp.staffreport.bungee.utils;

import eu.xgp.staffreport.bungee.Main;
import eu.xgp.staffreport.commons.utils.Utils;
import net.md_5.bungee.config.Configuration;

public class BungeeUtils implements Utils {
    private Configuration config;

    public BungeeUtils() {
        Main.getInstance().loadConfig();
        config = Main.getInstance().getConfig().getSection("settings.bungee");
    }

    public String getServer() {
        return config.getString("server");
    }

    @Override
    public boolean isTitleEnabled() {
        return config.getBoolean("title.enabled");
    }

    @Override
    public boolean isSubtitleEnabled() {
        return config.getBoolean("subtitle.enabled");
    }

    @Override
    public String getTitle(String staff) {
        return format(config.getString("title.text").replaceAll("%staff", staff));
    }

    @Override
    public String getSubtitle(String staff) {
        return format(config.getString("subtitle.text").replaceAll("%staff", staff));
    }

    @Override
    public int getTitleStay() {
        return config.getInt("title.stay");
    }

    @Override
    public int getTitleFadeIn() {
        return config.getInt("title.fadein");
    }

    @Override
    public int getTitleFadeOut() {
        return config.getInt("title.fadeout");
    }

    @Override
    public int getSubtitleStay() {
        return config.getInt("subtitle.stay");
    }

    @Override
    public int getSubtitleFadeIn() {
        return config.getInt("subtitle.fadein");
    }

    @Override
    public int getSubtitleFadeOut() {
        return config.getInt("subtitle.fadeout");
    }
}
