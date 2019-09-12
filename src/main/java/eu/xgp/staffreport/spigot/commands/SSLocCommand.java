package eu.xgp.staffreport.spigot.commands;

import eu.xgp.staffreport.spigot.Main;
import eu.xgp.staffreport.spigot.utils.SpigotMessageUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class SSLocCommand implements CommandExecutor, TabExecutor {
    private Plugin plugin = Main.getPlugin(Main.class);
    private SpigotMessageUtils msg;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        msg = Main.getInstance().getMessageUtils();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.hasPermission("staffreport.setloc")) {
                p.sendMessage(msg.noPermMessage());
                return false;
            }
            Location loc = p.getLocation();
            plugin.getConfig().set("settings.spigot.world.name", loc.getWorld().getName());
            plugin.getConfig().set("settings.spigot.world.x", loc.getX());
            plugin.getConfig().set("settings.spigot.world.y", loc.getY());
            plugin.getConfig().set("settings.spigot.world.z", loc.getZ());
            plugin.saveConfig();
            p.sendMessage(msg.locSavedMessage());
            return true;
        } else {
            sender.sendMessage(msg.onlyPlayerMessage());
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
