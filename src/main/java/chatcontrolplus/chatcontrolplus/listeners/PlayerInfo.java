package chatcontrolplus.chatcontrolplus.listeners;

import chatcontrolplus.chatcontrolplus.ChatUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;

public class PlayerInfo implements Listener {

    public PlayerInfo(ChatUtils plugin) {
        this.plugin = plugin;
    }
    private ChatUtils plugin;

    public void playerInfo(Player playerToGet, Player player) {

        String pName = playerToGet.getName();

        Location deathLoc = playerToGet.getLastDeathLocation();
        int xDeath = (int) deathLoc.getX();
        int yDeath = (int) deathLoc.getY();
        int zDeath = (int) deathLoc.getZ();

        boolean isOnline = playerToGet.isOnline();

        player.sendMessage(" ");
        player.sendMessage(ChatColor.GREEN + "Information for: " + pName);
        player.sendMessage(ChatColor.GREEN + "Name: " + pName);

        if (isOnline) {
            UUID pUUID = playerToGet.getUniqueId();
            TextComponent uuidMessage = new TextComponent(ChatColor.GREEN + "UUID: " + pUUID);
            uuidMessage.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, pUUID.toString()));
            uuidMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder("Click To Copy: " + pUUID).color(net.md_5.bungee.api.ChatColor.GRAY).italic(true).create()));
            player.spigot().sendMessage(uuidMessage);
        } else {
            player.sendMessage(ChatColor.GREEN + "UUID: N/A");
        }


        if (isOnline) {
            GameMode pMode = playerToGet.getGameMode();
            GameMode previousMode = playerToGet.getPreviousGameMode();
            String stringPMode = pMode.toString().toLowerCase();

            TextComponent gamemodeMessage = new TextComponent(ChatColor.GREEN + "Gamemode: " + pMode);
            gamemodeMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gamemode " + stringPMode));
            gamemodeMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder("Click To Switch Gamemode to: " + pMode).color(net.md_5.bungee.api.ChatColor.GRAY).italic(true).create()));
            player.spigot().sendMessage(gamemodeMessage);
//        player.sendMessage(ChatColor.YELLOW + "Gamemode: " + pMode);

            String previousPMode = previousMode.toString().toLowerCase();

            TextComponent previousGamemodeMessage = new TextComponent(ChatColor.RED + "Previous Gamemode: " + previousMode);
            previousGamemodeMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gamemode " + previousPMode));
            previousGamemodeMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder("Click To Switch Gamemode to: " + pMode).color(net.md_5.bungee.api.ChatColor.GRAY).italic(true).create()));
            player.spigot().sendMessage(previousGamemodeMessage);
        } else {
            player.sendMessage(ChatColor.GREEN + "Gamemode: N/A");
            player.sendMessage(ChatColor.RED + "Previous Gamemode: N/A");
        }

        player.sendMessage();
        if (isOnline) {
            Location playerLoc = playerToGet.getLocation();
            int x = (int) playerLoc.getX();
            int y = (int) playerLoc.getY();
            int z = (int) playerLoc.getZ();
            player.sendMessage(ChatColor.GREEN + "Online: true");
            TextComponent locMessage = new TextComponent(ChatColor.GREEN + "Location: " + x + " " + y + " " + z);
            locMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + pName + " " + x + " " + y + " " + z));
            locMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder("Click To Teleport: " + x + " " + y + " " + z).color(net.md_5.bungee.api.ChatColor.GRAY).italic(true).create()));
            player.spigot().sendMessage(locMessage);
        } else {
            player.sendMessage(ChatColor.RED + "Online: false");
            player.sendMessage(ChatColor.RED + "Location: N/A");
        }

        TextComponent locMessage = new TextComponent(ChatColor.GREEN + "Death Location: " + xDeath + " " + yDeath + " " + zDeath);
        locMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + pName + " " + xDeath + " " + yDeath + " " + zDeath));
        locMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("Click To Teleport: " + xDeath + " " + yDeath + " " + zDeath).color(net.md_5.bungee.api.ChatColor.GRAY).italic(true).create()));
        player.spigot().sendMessage(locMessage);

        if (isOnline) {
            float nextLevel = playerToGet.getExpToLevel();
            float exp = playerToGet.getExp();
            int level = (int) playerToGet.getLevel();
            player.sendMessage(ChatColor.GREEN + "Exp: " + exp);
            player.sendMessage(ChatColor.GREEN + "Exp Level: " + nextLevel);
            player.sendMessage(ChatColor.GREEN + "Level: " + level);
        } else {
            player.sendMessage(ChatColor.GREEN + "Exp: N/A");
            player.sendMessage(ChatColor.GREEN + "Exp Level: N/A");
            player.sendMessage(ChatColor.GREEN + "Level: N/A");
        }

        boolean shouldShowIp = plugin.getConfig().getBoolean("shouldShowIp");
        if (shouldShowIp) {
            if (player.hasPermission("chatutils.seeip")) {
                player.sendMessage(ChatColor.GREEN + "Ip: " + Objects.requireNonNull(playerToGet.getAddress()).getAddress().getHostAddress());
            } else {
                player.sendMessage(ChatColor.RED + "Ip: N/A");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Ip: Disabled");
        }
        player.sendMessage(" ");
    }
}
