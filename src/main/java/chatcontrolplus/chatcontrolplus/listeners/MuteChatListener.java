package chatcontrolplus.chatcontrolplus.listeners;

import chatcontrolplus.chatcontrolplus.ChatControlPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MuteChatListener implements Listener {

    private ChatControlPlus plugin;

    public MuteChatListener(ChatControlPlus plugin) {
        this.plugin = plugin;
    }

    public void muteChat() {
        boolean isMuted = plugin.getConfig().getBoolean("isChatNotMuted", false);
        if (isMuted) {
            plugin.getServer().broadcastMessage(ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "                                            ");
            plugin.getServer().broadcastMessage(ChatColor.GREEN + "The chat is now unmuted");
            plugin.getServer().broadcastMessage(ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "                                            ");
            plugin.getConfig().set("isChatNotMuted", false);
            plugin.saveConfig();
        } else {
            plugin.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "                                            ");
            plugin.getServer().broadcastMessage(ChatColor.RED + "The chat is now muted");
            plugin.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "                                            ");
            plugin.getConfig().set("isChatNotMuted", true);
            plugin.saveConfig();
        }
    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        boolean isMuted = plugin.getConfig().getBoolean("isChatNotMuted", false);
        if (isMuted && !player.hasPermission("chatcontrolplus.bypass")) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "The chat is currently muted");
        }
    }
}
