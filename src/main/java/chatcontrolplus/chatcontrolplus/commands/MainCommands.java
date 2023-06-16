package chatcontrolplus.chatcontrolplus.commands;

import chatcontrolplus.chatcontrolplus.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainCommands implements CommandExecutor, TabCompleter {
    private final ChatUtils plugin;

    public MainCommands(ChatUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't use this command in the console");
            return true;
        }

        Player player = (Player) sender;

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (player.hasPermission("chatutils.reload")) {
                    long startTime = System.currentTimeMillis();
                    plugin.reloadConfig();
                    long endTime = System.currentTimeMillis();
                    long time = endTime - startTime;
                    player.sendMessage(ChatColor.GREEN + "You successfully reloaded the config file in (" + time + "ms)");
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        Player player = (Player) sender;
        if (args.length == 1) {
            if (player.hasPermission("chatutils.reload")) {
                completions.add("reload");
            }
        }
        return completions;
    }
}