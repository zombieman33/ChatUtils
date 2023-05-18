package chatcontrolplus.chatcontrolplus.commands;

import chatcontrolplus.chatcontrolplus.ChatControlPlus;
import chatcontrolplus.chatcontrolplus.listeners.ClearChatListener;
import chatcontrolplus.chatcontrolplus.listeners.MuteChatListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteChatCommand implements CommandExecutor {
    private final ChatControlPlus plugin;

    public MuteChatCommand(ChatControlPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't run this command from the console");
            return true;
        }

        Player player = (Player) sender;
        if (player.hasPermission("chatcontrolplus.mutechat")) {
            MuteChatListener muteChat = new MuteChatListener(plugin);
            muteChat.muteChat();
        } else {
            player.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
        }
        return false;
    }
}