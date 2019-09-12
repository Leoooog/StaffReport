package eu.xgp.staffreport.spigot.commands;

import eu.xgp.staffreport.spigot.Main;
import eu.xgp.staffreport.spigot.utils.SpigotMessageUtils;
import eu.xgp.staffreport.spigot.utils.SpigotUtils;
import org.bukkit.Bukkit;
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
    private SpigotMessageUtils msg;
    private SpigotUtils utils;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        msg = Main.getInstance().getMessageUtils();
        utils = Main.getInstance().getSpigotUtils();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("staffreport.ss")) {
                if (args.length != 1) {
                    message = msg.ssUsageMessage();
                    p.sendMessage(message);
                    return false;
                }
                if (!Main.getInstance().getConfig().contains("settings.spigot.world")) {
                    p.sendMessage("§cLocation is not set.");
                    return false;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    message = msg.playerNotOnlineMessage(args[0]);
                    p.sendMessage(message);
                    return false;
                }
                target.teleport(utils.getSSLocation());
                if (utils.isTitleEnabled()) {
                    plugin.getNmsUtils().sendTitle(target, "TITLE", utils.getTitle(p.getDisplayName()), utils.getTitleStay(), utils.getTitleFadeIn(), utils.getTitleFadeOut());
                }
                if (utils.isSubtitleEnabled()) {
                    plugin.getNmsUtils().sendTitle(target, "SUBTITLE", utils.getSubtitle(p.getDisplayName()), utils.getSubtitleStay(), utils.getSubtitleFadeIn(), utils.getSubtitleFadeOut());
                }
                target.sendMessage(msg.ssStatusMessage(p.getDisplayName()));
                return true;
            } else {
                message = msg.noPermMessage();
                p.sendMessage(message);
                return false;
            }
        } else {
            message = msg.onlyPlayerMessage();
            sender.sendMessage(message);
            return false;
        }
    }


    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("staffreport.ss")) return new ArrayList<>();
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
