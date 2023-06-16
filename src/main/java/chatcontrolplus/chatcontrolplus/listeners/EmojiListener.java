package chatcontrolplus.chatcontrolplus.listeners;

import chatcontrolplus.chatcontrolplus.ChatUtils;
import chatcontrolplus.chatcontrolplus.utils.ColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmojiListener implements Listener {
    private ChatUtils plugin;

    public EmojiListener(ChatUtils plugin) {
        this.plugin = plugin;
    }

    Map<String, String> emojiMap = new HashMap<>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        message = replaceEmojis(message);
        event.setMessage(ColorUtil.color(message));
    }

    private String replaceEmojis(String message) {
        List<String> emojiList = plugin.getConfig().getStringList("emojis");
        for (String emojiEntry : emojiList) {
            String[] parts = emojiEntry.split(", ");
            if (parts.length == 2) {
                String keyword = parts[0];
                String emoji = parts[1];
                emojiMap.put(keyword, emoji);
            }
        }

        for (Map.Entry<String, String> entry : emojiMap.entrySet()) {
            String keyword = entry.getKey();
            String emoji = entry.getValue();
            message = message.replace(keyword, emoji);
        }
        return message;
    }
}