package chatcontrolplus.chatcontrolplus.commands;

import chatcontrolplus.chatcontrolplus.ChatUtils;
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

public class SudoCommand implements CommandExecutor, TabCompleter {
    private final ChatUtils plugin;

    public SudoCommand(ChatUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't run this command from the console");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("chatutils.sudo")) {
            boolean shouldBeAbleToSudo = plugin.getConfig().getBoolean("shouldBeAbleToSudo");
            if (shouldBeAbleToSudo) {
                if (args.length >= 2) {
                    if (args[0] == null) {
                        player.sendMessage(ChatColor.RED + "You need to specify a player to sudo");
                        return true;
                    }
                    String targetName = args[0];
                    Player target = Bukkit.getPlayerExact(targetName);

                    // Join arguments starting from index 1
                    String commandToRun = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

                    if (targetName.equalsIgnoreCase("*")) {
                        if (commandToRun.startsWith("/")) {
                            String format = commandToRun.replace("/", "");
                            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                onlinePlayer.performCommand(format);
                            }
                            player.sendMessage(ChatColor.GREEN + "You made everyone run: " + format);
                        } else {
                            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                onlinePlayer.chat(commandToRun);
                            }
                        }
                    } else {
                        if (commandToRun.startsWith("/")) {
                            String format = commandToRun.replace("/", "");
                            if (target != null) {
                                target.performCommand(format);
                                player.sendMessage(ChatColor.GREEN + "You made " + targetName + " run: " + commandToRun);
                            } else {
                                player.sendMessage(ChatColor.RED + "Player not found.");
                            }
                        } else {
                            if (target != null) {
                                target.chat(commandToRun);
                            } else {
                                player.sendMessage(ChatColor.RED + "Player not found.");
                            }
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Invalid command format. Usage: /sudo <player> /<command> or /sudo <player> <message>");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Sudo isn't enabled!");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        Player player = (Player) sender;
        if (player.hasPermission("chatutils.sudo")) {
            if (args.length == 1) {
                completions.add("*");
                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                    String onlinePlayerNames = onlinePlayers.getName();
                    completions.add(onlinePlayerNames);
                }
            } else if (args.length == 2) {
                completions.add("<command>");
                completions.add("<message>");
            }
        }
        return completions;
    }
}