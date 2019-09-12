package eu.xgp.staffreport.spigot.commands;

import eu.xgp.staffreport.spigot.Main;
import eu.xgp.staffreport.spigot.utils.SpigotMessageUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class StaffReportCommand implements CommandExecutor, TabExecutor {
    private SpigotMessageUtils msg;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        msg = Main.getInstance().getMessageUtils();
        if (!sender.hasPermission("staffreport.help")) {
            sender.sendMessage(msg.noPermMessage());
            return false;
        }
        TextComponent clickAuthor = new TextComponent("§f§o§lLeog_04");
        TextComponent message = new TextComponent("§2-----[§5StaffReport by §o§l");
        clickAuthor.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://t.me/Leog_04"));
        message.addExtra(clickAuthor);
        message.addExtra("§2]-----\n§6/report <player> <reason> §f-> §breports a player\n§6/ss <player> §f-> §btips player to screenshare world/server\n§6/staffreport §f-> §bshows this menu");
        sender.spigot().sendMessage(message);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
