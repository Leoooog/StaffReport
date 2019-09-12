package eu.xgp.staffreport.bungee.commands;

import eu.xgp.staffreport.bungee.Main;
import eu.xgp.staffreport.bungee.utils.BungeeMessageUtils;
import eu.xgp.staffreport.bungee.utils.BungeeUtils;
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

    private BungeeMessageUtils msg;
    private static Plugin plugin = Main.getInstance();
    private BungeeUtils utils;

    @Override
    public void execute(CommandSender sender, String[] args) {
        utils = Main.getInstance().getBungeeUtils();
        msg = Main.getInstance().getMessageUtils();
        TextComponent message = new TextComponent();
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (p.hasPermission("staffreport.ss")) {
                if (args.length != 1) {
                    message.setText(msg.ssUsageMessage());
                    p.sendMessage(message);
                    return;
                }
                if (!Main.getInstance().getConfig().contains("settings.bungee.server")) {
                    p.sendMessage(new TextComponent("§cServer is not set."));
                    return;
                }
                ProxiedPlayer target = plugin.getProxy().getPlayer(args[0]);
                if (target == null) {
                    message.setText(msg.playerNotOnlineMessage(args[0]));
                    p.sendMessage(message);
                    return;
                }
                ServerInfo pServer = p.getServer().getInfo();
                ServerInfo targetServer = target.getServer().getInfo();
                ServerInfo ssServer = plugin.getProxy().getServerInfo(utils.getServer());
                if (ssServer != targetServer) {
                    target.connect(ssServer);
                }
                if (ssServer != pServer) {
                    p.connect(ssServer);
                }
                Title title = plugin.getProxy().createTitle();
                Title subtitle = plugin.getProxy().createTitle();
                if (utils.isTitleEnabled()) {
                    title.title(new TextComponent(utils.getTitle(p.getDisplayName())));
                    title.stay(utils.getTitleStay());
                    title.fadeIn(utils.getTitleFadeIn());
                    title.fadeOut(utils.getTitleFadeOut());
                    target.sendTitle(title);
                }

                if (utils.isSubtitleEnabled()) {
                    subtitle.subTitle(new TextComponent(utils.getSubtitle(p.getDisplayName())));
                    subtitle.stay(utils.getSubtitleStay());
                    subtitle.fadeIn(utils.getSubtitleFadeIn());
                    subtitle.fadeOut(utils.getSubtitleFadeOut());
                    target.sendTitle(subtitle);
                }
                target.sendMessage(new TextComponent(msg.ssStatusMessage(p.getDisplayName())));
            } else {
                message.setText(msg.noPermMessage());
                p.sendMessage(message);
                return;
            }
        } else {
            String s = msg.onlyPlayerMessage();
            message.setText(s);
            sender.sendMessage(message);
            return;
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (!sender.hasPermission("staffreport.ss")) return new HashSet<>();
        Set<String> match = new HashSet<>();
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
