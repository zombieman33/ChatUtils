package chatcontrolplus.chatcontrolplus.listeners;

import chatcontrolplus.chatcontrolplus.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class ClearChatListener implements Listener {

    private ChatUtils plugin;

    public ClearChatListener(ChatUtils plugin) {
        this.plugin = plugin;
    }
    public void clearChat(Player player, ChatColor color) {
        for (Player onlineP : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i < 200; i++) {
                onlineP.sendMessage(" ");
            }
        }
        String pName = player.getName();
        for (Player oP : Bukkit.getOnlinePlayers()) {
            oP.sendMessage(color + "" + ChatColor.STRIKETHROUGH + "                                           ");
            oP.sendMessage(color + plugin.getConfig().getString("clearChat.format").replace("%player%", pName));
            oP.sendMessage(color + "" + ChatColor.STRIKETHROUGH + "                                           ");
        }
    }
}
