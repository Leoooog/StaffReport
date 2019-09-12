package eu.xgp.staffreport.bungee.commands;

import eu.xgp.staffreport.bungee.Main;
import eu.xgp.staffreport.bungee.utils.BungeeMessageUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.HashSet;

public class StaffReportCommand extends Command implements TabExecutor {
    private BungeeMessageUtils msg;

    public StaffReportCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        msg = Main.getInstance().getMessageUtils();
        if (!sender.hasPermission("staffreport.help")) {
            sender.sendMessage(new TextComponent(msg.noPermMessage()));
            return;
        }
        TextComponent clickAuthor = new TextComponent("§f§o§lLeog_04");
        TextComponent message = new TextComponent("§2-----[§5StaffReport by §o§l");
        clickAuthor.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://t.me/Leog_04"));
        message.addExtra(clickAuthor);
        message.addExtra("§2]-----\n§6/report <player> <reason> §f-> §breports a player\n§6/ss <player> §f-> §btips player to screenshare world/server\n§6/staffreport §f-> §bshows this menu");
        sender.sendMessage(message);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender commandSender, String[] strings) {
        return new HashSet<>();
    }
}
