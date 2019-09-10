package eu.xgp.staffreport.spigot.commands;

import eu.xgp.staffreport.spigot.Main;
import eu.xgp.staffreport.spigot.utils.MessageUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SSCommand implements CommandExecutor, TabExecutor {
    private String message;
    private static Main plugin = Main.getInstance();
    private static MessageUtils msg = Main.getMessageUtils();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (p.hasPermission("staffreport.ss")) {
                if (args.length != 1) {
                    message = msg.ssUsageMessage();
                    p.sendMessage(message);
                    return false;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    message = msg.playerNotOnlineMessage(args[0]);
                    p.sendMessage(message);
                    return false;
                }
                World w = Bukkit.getWorld("world");
                plugin.getNmsUtils().sendTitle(target, "TITLE", "Sei sotto controllo", 100, 20, 20);

                return true;
            } else {
                message = msg.noPermMessage();
                p.sendMessage(message);
                return false;
            }
        } else {
            message = msg.onlyPlayerMessage();
            commandSender.sendMessage(message);
            return false;
        }
    }


    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.hasPermission("staffreport.ss")) return new ArrayList<>();
        List<String> match = new ArrayList<>();
        if (args.length == 1) {
            String regex = args[0].toLowerCase();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().toLowerCase().startsWith(regex))
                    match.add(p.getName());
            }
        }
        return match;
    }
}
