package eu.xgp.staffreport.spigot.utils;

import eu.xgp.staffreport.commons.utils.Utils;
import eu.xgp.staffreport.spigot.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class SpigotUtils implements Utils {
    private ConfigurationSection config;

    public SpigotUtils() {
        Main.getInstance().reloadConfig();
        config = Main.getInstance().getConfig().getConfigurationSection("settings.spigot");
    }

    public Location getSSLocation() {
        String name = config.getString("world.name");
        double x = config.getDouble("world.x");
        double y = config.getDouble("world.y");
        double z = config.getDouble("world.z");
        return new Location(Bukkit.getWorld(name), x, y, z);
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
