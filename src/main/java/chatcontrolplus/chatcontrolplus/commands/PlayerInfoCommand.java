package chatcontrolplus.chatcontrolplus.commands;

import chatcontrolplus.chatcontrolplus.ChatUtils;
import chatcontrolplus.chatcontrolplus.listeners.PlayerInfo;
import chatcontrolplus.chatcontrolplus.utils.ColorUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;
import java.util.UUID;

public class PlayerInfoCommand implements CommandExecutor {
    private final ChatUtils plugin;

    public PlayerInfoCommand(ChatUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't run this command from the console");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("chatutils.player")) {
            PlayerInfo playerInfo = new PlayerInfo(plugin);
            if (args.length == 0) {
                playerInfo.playerInfo(player, player);
            } else if (args.length == 1) {
                String targetName = args[0];
                Player target = Bukkit.getPlayerExact(targetName);
                if (target != null) {
                    playerInfo.playerInfo(target, player);
                } else {
                    player.sendMessage(ChatColor.RED + targetName + " is not online.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /players [name]");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
        }

        return true;
    }


}
