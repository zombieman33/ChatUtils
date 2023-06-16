package chatcontrolplus.chatcontrolplus.commands;

import chatcontrolplus.chatcontrolplus.ChatUtils;
import chatcontrolplus.chatcontrolplus.utils.ColorUtil;
import chatcontrolplus.chatcontrolplus.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StaffChatCommand implements CommandExecutor, TabCompleter {
    private final ChatUtils plugin;

    public StaffChatCommand(ChatUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't run this command from the console");
            return true;
        }

        Player player = (Player) sender;

        if (args.length >= 1) {
            if (args[0] == null) {
                player.sendMessage(ChatColor.RED + "You need to add a message to send");
                return true;
            }
            if (player.hasPermission("chatutils.staffchat")) {
                String message = StringUtils.join(" ", args);
                String messageFormat = plugin.getConfig().getString("staffChat.format")
                        .replace("%player%", player.getName())
                        .replace("%message%", message);
                for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                    if (onlineStaff.hasPermission("chatutils.staffchat")) {
                        onlineStaff.sendMessage(ColorUtil.color(messageFormat));
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        Player player = (Player) sender;
        if (player.hasPermission("chatutils.staffchat")) {
            completions.add("<message>");
        }
        return completions;
    }
}