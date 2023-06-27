package chatcontrolplus.chatcontrolplus.commands;

import chatcontrolplus.chatcontrolplus.ChatUtils;
import chatcontrolplus.chatcontrolplus.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageCommand implements CommandExecutor, TabCompleter {
    private final ChatUtils plugin;

    public MessageCommand(ChatUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't run this command from the console");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("chatutils.message")) {
            boolean shouldSendMessage = plugin.getConfig().getBoolean("shouldSendMessage");
            if (shouldSendMessage) {
                if (args.length >= 1) {
                    String targetName = args[0];
                    Player target = Bukkit.getPlayerExact(targetName);
                    if (target != null) {
                        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                        String messageFormat = plugin.getConfig().getString("message.format")
                                .replace("%player-from%", player.getName())
                                .replace("%player-to%", targetName)
                                .replace("%message%", message);
                        if (target != player) {
                            target.sendMessage(ColorUtil.color(messageFormat));
                            player.sendMessage(ColorUtil.color(messageFormat));
                        } else {
                            player.sendMessage(ColorUtil.color(messageFormat));
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Player not found.");
                    }
                } else {
                    player.sendMessage(ChatColor.YELLOW + "/message <player> <message>");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Private Messages are disabled");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        Player player = (Player) sender;
        if (player.hasPermission("chatutils.message")) {
            if (args.length == 1) {
                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                    String onlinePlayerNames = onlinePlayers.getName();
                    completions.add(onlinePlayerNames);
                }
            } else if (args.length == 2) {
                completions.add("<message>");
            }
        }
        return completions;
    }
}
