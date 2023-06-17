package chatcontrolplus.chatcontrolplus.commands;

import chatcontrolplus.chatcontrolplus.ChatUtils;
import chatcontrolplus.chatcontrolplus.utils.ColorUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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

public class CommandBroadcast implements CommandExecutor, TabCompleter {
    private final ChatUtils plugin;

    public CommandBroadcast(ChatUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't run this command from the console");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("chatutils.commandbroadcast")) {
            boolean shouldShowCommandBroadcast = plugin.getConfig().getBoolean("shouldShowCommandBroadcast");
            if (shouldShowCommandBroadcast) {
                if (args.length >= 1) {
                    if (args[0] == null) {
                        player.sendMessage(ChatColor.RED + "You need to add a command to broadcast");
                        return true;
                    }
                    String commands = String.join(" ", Arrays.copyOfRange(args, 0, args.length));
                    if (commands.startsWith("/")) {
                        String commandFormat = plugin.getConfig().getString("commandBroadcast.format")
                                .replace("%player%", player.getName())
                                .replace("%command%", commands);
                        TextComponent commandMessage = new TextComponent(ColorUtil.color(commandFormat));
                        commandMessage.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, commands));
                        commandMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                new ComponentBuilder("Click To Suggest: " + commands).color(net.md_5.bungee.api.ChatColor.GRAY).italic(true).create()));
                        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                            boolean shouldShowEmptyLine = plugin.getConfig().getBoolean("commandBroadcast.shouldShowEmptyLine");
                            if (shouldShowEmptyLine) {
                                onlinePlayers.sendMessage(" ");
                                onlinePlayers.spigot().sendMessage(commandMessage);
                                onlinePlayers.sendMessage(" ");
                            } else {
                                onlinePlayers.spigot().sendMessage(commandMessage);
                            }
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You need to suggest a command not a message.");
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "Command Broadcast is disabled!");
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
        if (player.hasPermission("chatutils.commandbroadcast")) {
            completions.add("<command>");
        }
        return completions;
    }
}