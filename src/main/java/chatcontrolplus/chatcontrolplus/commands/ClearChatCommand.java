package chatcontrolplus.chatcontrolplus.commands;

import chatcontrolplus.chatcontrolplus.ChatControlPlus;
import chatcontrolplus.chatcontrolplus.listeners.ClearChatListener;
import chatcontrolplus.chatcontrolplus.utils.ColorUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ClearChatCommand implements CommandExecutor {
    private final ChatControlPlus plugin;

    public ClearChatCommand(ChatControlPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't run this command from the console");
            return true;
        }

        Player player = (Player) sender;
        if (player.hasPermission("chatcontrolplus.clearchat")) {
            ClearChatListener clearChatListener = new ClearChatListener(plugin);
            clearChatListener.clearChat(player, ChatColor.AQUA);
        } else {
            player.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
        }
        return false;
    }
}