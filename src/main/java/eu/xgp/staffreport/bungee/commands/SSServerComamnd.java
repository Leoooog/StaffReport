package eu.xgp.staffreport.bungee.commands;

import eu.xgp.staffreport.bungee.Main;
import eu.xgp.staffreport.bungee.utils.BungeeMessageUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.config.Configuration;

import java.util.HashSet;

public class SSServerComamnd extends Command implements TabExecutor {
    private BungeeMessageUtils msg;
    private Main plugin = Main.getInstance();
    private Configuration config;

    public SSServerComamnd(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        msg = plugin.getMessageUtils();
        config = plugin.getConfig();
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (!p.hasPermission("staffreport.setserver")) {
                p.sendMessage(new TextComponent(msg.noPermMessage()));
                return;
            }
            config.set("settings.bungee.server", p.getServer().getInfo().getName());
            plugin.saveConfig(config);
            p.sendMessage(new TextComponent(msg.serverSavedMessage()));
        } else {
            sender.sendMessage(new TextComponent(msg.onlyPlayerMessage()));
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender commandSender, String[] strings) {
        return new HashSet<>();
    }
}
