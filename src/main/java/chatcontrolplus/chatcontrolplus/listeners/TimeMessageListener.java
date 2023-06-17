package chatcontrolplus.chatcontrolplus.listeners;


import chatcontrolplus.chatcontrolplus.ChatUtils;
import chatcontrolplus.chatcontrolplus.utils.ColorUtil;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeMessageListener implements Listener {

    private ChatUtils plugin;
    private SimpleDateFormat dateFormat;

    public TimeMessageListener(ChatUtils plugin) {
        this.plugin = plugin;
        this.dateFormat = new SimpleDateFormat("HH:mm");
        String timeZone = plugin.getConfig().getString("timeZone");
        this.dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
    }

    @EventHandler
    public void onChatWithTime(AsyncPlayerChatEvent event) {
        boolean shouldShowTime = plugin.getConfig().getBoolean("showTime");
        if (shouldShowTime) {
            String message = event.getMessage();
            String pName = event.getPlayer().getName();
            String time = dateFormat.format(new Date());
            String timeMessage = plugin.getConfig().getString("timeMessage");
            String timeFormat = timeMessage
                    .replace("%message%", message)
                    .replace("%time%", time)
                    .replace("%player%", pName);
            event.setMessage(ColorUtil.color(timeFormat));
        }
    }
}
