package eu.xgp.staffreport.bungee.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eu.xgp.staffreport.bungee.Main;
import eu.xgp.staffreport.bungee.utils.BungeeMessageUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReportCommand extends Command implements TabExecutor {
    public ReportCommand(String cmd) {
        super(cmd);
    }

    private TextComponent message;
    private static Plugin plugin = Main.getInstance();
    private BungeeMessageUtils msg;

    @Override
    public void execute(CommandSender sender, String[] args) {
        msg = Main.getInstance().getMessageUtils();
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (sender.hasPermission("staffreport.report")) {
                if (args.length != 2) {
                    message = new TextComponent(msg.reportUsageMessage());
                    p.sendMessage(message);
                    return;
                }
                ProxiedPlayer target = plugin.getProxy().getPlayer(args[0]);
                if (target == null) {
                    message = new TextComponent(msg.playerNotOnlineMessage(args[0]));
                    p.sendMessage(message);
                    return;
                }
                String toFormat = msg.reportedPlayerMessage(p.getName(), target.getName(), p.getServer().getInfo().getName(), args[1]);
                List<String> lines = Arrays.asList(toFormat.split("\n"));
                TextComponent messageText = new TextComponent();
                TextComponent clickMessage = new TextComponent();
                for (String line : lines) {
                    if (line.contains("{") && line.contains("}")) {
                        String toParse = line.split("\\{")[1].split("}")[0];
                        JsonParser parser = new JsonParser();
                        JsonObject obj = parser.parse("{" + toParse + "}").getAsJsonObject();
                        try {
                            String click = obj.get("click").getAsString();
                            String hover = obj.get("hover").getAsString();
                            String text = obj.get("text").getAsString();
                            clickMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, click));
                            clickMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create()));
                            clickMessage.setText("\n" + text);
                            messageText.addExtra(clickMessage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (line != "" || line != null) {
                        if (lines.indexOf(line) != 0)
                            messageText.addExtra("\n");
                        messageText.addExtra(line);
                    }
                }
                for (ProxiedPlayer staff : plugin.getProxy().getPlayers()) {
                    if (staff.hasPermission("staffreport.see")) {
                        staff.sendMessage(messageText);
                    }
                }
                message = new TextComponent(msg.youReportedMessage(target.getName(), args[1]));
                p.sendMessage(message);

            } else {
                message = new TextComponent(msg.noPermMessage());
                p.sendMessage(message);
                return;
            }
        } else {
            message = new TextComponent(msg.onlyPlayerMessage());
            sender.sendMessage(message);
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (!sender.hasPermission("staffreport.report")) return new HashSet<>();
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
