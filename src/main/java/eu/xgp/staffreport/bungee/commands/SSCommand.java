package eu.xgp.staffreport.bungee.commands;

import eu.xgp.staffreport.bungee.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.HashSet;
import java.util.Set;

public class SSCommand extends Command implements TabExecutor {
    public SSCommand(String ss) {
        super(ss);
    }

    private TextComponent message;
    private static Plugin plugin = Main.getInstance();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) commandSender;
            if (p.hasPermission("staffreport.ss")) {
                if (args.length != 1) {
                    message = new TextComponent(ChatColor.RED + "Usage: /ss <player>");
                    p.sendMessage(message);
                    return;
                }
                ProxiedPlayer target = plugin.getProxy().getPlayer(args[0]);
                if (target == null) {
                    message = new TextComponent(ChatColor.RED + "Player '" + ChatColor.DARK_RED + args[0] + ChatColor.RED + "' is not online.");
                    p.sendMessage(message);
                    return;
                }
                ServerInfo pServer = p.getServer().getInfo();
                ServerInfo targetServer = target.getServer().getInfo();
                ServerInfo ssServer = plugin.getProxy().getServerInfo("SURVIVAL");
                if (ssServer != targetServer) {
                    target.connect(ssServer);
                }
                if (ssServer != pServer) {
                    p.connect(ssServer);
                }
                Title title = plugin.getProxy().createTitle();
                title.title(new TextComponent("Sei sotto controllo hack"));
                title.stay(100);
                title.fadeIn(20);
                title.fadeOut(20);
                target.sendTitle(title);
            } else {
                message = new TextComponent(ChatColor.RED + "Hey, you can't execute this command!");
                p.sendMessage(message);
                return;
            }
        } else {
            message = new TextComponent("Only players can execute this command.");
            commandSender.sendMessage(message);
            return;
        }
    }
    @Override
    public Iterable<String> onTabComplete(CommandSender commandSender, String[] args) {
        if (!commandSender.hasPermission("staffreport.ss")) return new HashSet<String>();
        Set<String> match = new HashSet<String>();
        if (args.length == 1) {
            String regex = args[0].toLowerCase();
            for (ProxiedPlayer p : plugin.getProxy().getPlayers()) {
                if(p.getName().toLowerCase().startsWith(regex))
                    match.add(p.getName());
            }
        }
        return match;
    }

}
