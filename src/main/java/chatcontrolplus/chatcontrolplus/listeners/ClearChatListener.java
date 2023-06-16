package chatcontrolplus.chatcontrolplus.listeners;

import chatcontrolplus.chatcontrolplus.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class ClearChatListener implements Listener {

    private ChatUtils plugin;

    public ClearChatListener(ChatUtils plugin) {
        this.plugin = plugin;
    }
    public void clearChat(Player player, ChatColor color) {
        for (int i = 0; i < 200; i++) {
            plugin.getServer().broadcastMessage(" ");
        }
        String pName = player.getName();
        plugin.getServer().broadcastMessage(color + "" + ChatColor.STRIKETHROUGH + "                                           ");
        plugin.getServer().broadcastMessage(color + pName + " cleared the chat");
        plugin.getServer().broadcastMessage(color + "" + ChatColor.STRIKETHROUGH + "                                           ");
    }
}
