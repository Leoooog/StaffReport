package eu.xgp.staffreport.bungee.commands;

import eu.xgp.staffreport.bungee.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.HashSet;
import java.util.Set;

public class ReportCommand extends Command implements TabExecutor {
    public ReportCommand(String cmd) {
        super(cmd);
    }

    private TextComponent message;
    private static Plugin plugin = Main.getInstance();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) commandSender;
            if (commandSender.hasPermission("staffreport.report")) {
                if (args.length != 2) {
                    message = new TextComponent(ChatColor.RED + "Usage: /report <player> <reason>");
                    p.sendMessage(message);
                    return;
                }
                ProxiedPlayer target = plugin.getProxy().getPlayer(args[0]);
                if (target == null) {
                    message = new TextComponent(ChatColor.RED + "Player '" + ChatColor.DARK_RED + args[0] + ChatColor.RED + "' not found.");
                    p.sendMessage(message);
                    return;
                }
                message = new TextComponent(ChatColor.DARK_GREEN + "-------[" + ChatColor.DARK_PURPLE + "StaffReport" + ChatColor.DARK_GREEN + "]-------" + "\n");
                TextComponent clickmessage = new TextComponent(ChatColor.RED + "Teleport " + args[0] + "\n");
                clickmessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ss " + args[0]));
                clickmessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§oSend " + target.getName() + " to screenshare server").create()));
                message.addExtra(ChatColor.AQUA + "Server: " + ChatColor.YELLOW + target.getServer().getInfo().getName() + "\n");
                message.addExtra(ChatColor.AQUA + "Player: " + ChatColor.YELLOW + args[0] + "\n");
                message.addExtra(ChatColor.AQUA + "Reason: " + ChatColor.YELLOW + args[1] + "\n");
                message.addExtra(clickmessage);
                message.addExtra(ChatColor.DARK_GREEN + "-------[" + ChatColor.DARK_PURPLE + "StaffReport" + ChatColor.DARK_GREEN + "]-------");
                for (ProxiedPlayer staff : plugin.getProxy().getPlayers()) {
                    if (staff.hasPermission("staffreport.see")) {
                        staff.sendMessage(message);
                    }
                }

            } else {
                message = new TextComponent(ChatColor.RED + "Hey, you can't execute this command!");
                p.sendMessage(message);
                return;
            }
        } else {
            message = new TextComponent("Only players can execute this command");
            commandSender.sendMessage(message);
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender commandSender, String[] args) {
        if (!commandSender.hasPermission("staffreport.report")) return new HashSet<String>();
        Set<String> match = new HashSet<String>();
        if (args.length == 1) {
            String regex = args[0].toLowerCase();
            for (ProxiedPlayer p : plugin.getProxy().getPlayers()) {
                if (p.getName().toLowerCase().startsWith(regex))
                    match.add(p.getName());
            }
        }
        return match;
    }
}
