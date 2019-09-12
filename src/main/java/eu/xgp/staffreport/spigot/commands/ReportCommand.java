package eu.xgp.staffreport.spigot.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eu.xgp.staffreport.spigot.Main;
import eu.xgp.staffreport.spigot.utils.SpigotMessageUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReportCommand implements CommandExecutor, TabExecutor {
    private String message;
    private SpigotMessageUtils msg;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        msg = Main.getInstance().getMessageUtils();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (sender.hasPermission("staffreport.report")) {
                if (args.length != 2) {
                    message = msg.reportUsageMessage();
                    p.sendMessage(message);
                    return false;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    message = msg.playerNotOnlineMessage(args[0]);
                    p.sendMessage(message);
                    return false;
                }
                String toFormat = msg.reportedPlayerMessage(p.getName(), target.getName(), args[1], p.getLocation().getWorld().getName());
                List<String> lines = Arrays.asList(toFormat.split("\n"));
                TextComponent messageText = new TextComponent();
                TextComponent clickMessage = new TextComponent();
                for (String line : lines) {
                    if (line.contains("{") && line.contains("}")) {
                        String toParse = line.split("\\{")[1].split("}")[0];
                        System.out.println(toParse);
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
                for (Player staff : Bukkit.getOnlinePlayers()) {
                    if (staff.hasPermission("staffreport.see")) {
                        staff.spigot().sendMessage(messageText);
                    }
                }
                p.sendMessage(msg.youReportedMessage(target.getName(), args[1]));
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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("staffreport.report")) return new ArrayList<>();
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
