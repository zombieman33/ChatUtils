package chatcontrolplus.chatcontrolplus.commands;

import chatcontrolplus.chatcontrolplus.ChatUtils;
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

        if (args.length == 0) {
            playerInfo(player, player);
        } else if (args.length == 1) {
            if (player.hasPermission("chatutils.player")) {
                String targetName = args[0];
                Player target = Bukkit.getPlayerExact(targetName);
                if (target != null) {
                    playerInfo(target, player);
                } else {
                    player.sendMessage(ChatColor.RED + targetName + " is not online.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Usage: /players [name]");
        }

        return true;
    }

    public void playerInfo(Player playerToGet, Player player) {

        String pName = playerToGet.getName();
        UUID pUUID = playerToGet.getUniqueId();
        Location playerLoc = playerToGet.getLocation();
        Location deathLoc = playerToGet.getLastDeathLocation();
        int xDeath = (int) deathLoc.getX();
        int yDeath = (int) deathLoc.getY();
        int zDeath = (int) deathLoc.getZ();

        float nextLevel = playerToGet.getExpToLevel();
        float exp = playerToGet.getExp();
        int level = (int) playerToGet.getLevel();

        int x = (int) playerLoc.getX();
        int y = (int) playerLoc.getY();
        int z = (int) playerLoc.getZ();
        GameMode pMode = playerToGet.getGameMode();
        GameMode previousMode = playerToGet.getPreviousGameMode();
        boolean isOnline = playerToGet.isOnline();

        player.sendMessage(" ");
        player.sendMessage(ChatColor.GREEN + "Information for: " + pName);
        player.sendMessage(ChatColor.GREEN + "Name: " + pName);

        TextComponent uuidMessage = new TextComponent(ChatColor.GREEN + "UUID: " + pUUID);
        uuidMessage.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, pUUID.toString()));
        uuidMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("Click To Copy: " + pUUID).color(net.md_5.bungee.api.ChatColor.GRAY).italic(true).create()));
        player.spigot().sendMessage(uuidMessage);

        player.sendMessage(ChatColor.YELLOW + "Gamemode: " + pMode);
        player.sendMessage(ChatColor.RED + "Previous Gamemode: " + previousMode);
        if (isOnline) {
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

        player.sendMessage(ChatColor.GREEN + "Exp: " + exp);
        player.sendMessage(ChatColor.GREEN + "Exp Level: " + nextLevel);
        player.sendMessage(ChatColor.GREEN + "Level: " + level);

        if (player.hasPermission("chatutils.seeip")) {
            player.sendMessage(ChatColor.GREEN + "Ip: " + Objects.requireNonNull(playerToGet.getAddress()).getAddress().getHostAddress());
        } else {
            player.sendMessage(ChatColor.RED + "Ip: N/A");
        }

        player.sendMessage(" ");
    }
}
