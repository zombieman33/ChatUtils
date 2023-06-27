package chatcontrolplus.chatcontrolplus.listeners;

import chatcontrolplus.chatcontrolplus.ChatUtils;
import chatcontrolplus.chatcontrolplus.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.util.UUID;

public class MuteChatListener implements Listener {

    private ChatUtils plugin;

    public MuteChatListener(ChatUtils plugin) {
        this.plugin = plugin;
    }

    public void muteChat() {
        boolean isMuted = plugin.getConfig().getBoolean("isChatNotMuted", false);
        if (isMuted) {
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                onlinePlayers.sendMessage(ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "                                            ");
                onlinePlayers.sendMessage(ChatColor.GREEN + "The chat is now unmuted");
                onlinePlayers.sendMessage(ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "                                            ");
            }
            plugin.getConfig().set("isChatNotMuted", false);
            plugin.saveConfig();
        } else {
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                onlinePlayers.sendMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "                                            ");
                onlinePlayers.sendMessage(ChatColor.RED + "The chat is now muted");
                onlinePlayers.sendMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "                                            ");
            }
            plugin.getConfig().set("isChatNotMuted", true);
            plugin.saveConfig();
        }
    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String pName = player.getName();
        String message = event.getMessage();
        boolean isMuted = plugin.getConfig().getBoolean("isChatNotMuted", false);
        if (isMuted && !player.hasPermission("chatutils.bypass")) {
            event.setCancelled(true);
            for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                File playerDataFile = new File(plugin.getDataFolder(), "playerData.yml");
                FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
                UUID pUUID = onlineStaff.getUniqueId();
                boolean wantsToSeeMessages = playerDataConfig.getBoolean("messageSpy.player." + pUUID + ".ms", false);
                if (wantsToSeeMessages) {
                    onlineStaff.sendMessage(ColorUtil.color("&a" + pName + " said: '&c" + message + "&a' while the server is muted."));
                }
            }
            player.sendMessage(ChatColor.RED + "The chat is currently muted");
        }
    }
}
