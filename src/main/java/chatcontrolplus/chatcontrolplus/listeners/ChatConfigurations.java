package chatcontrolplus.chatcontrolplus.listeners;

import chatcontrolplus.chatcontrolplus.ChatUtils;
import chatcontrolplus.chatcontrolplus.utils.ColorUtil;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ChatConfigurations implements Listener {

    private ChatUtils plugin;

    public ChatConfigurations(ChatUtils plugin) {
        this.plugin = plugin;
    }

//    @EventHandler(priority = EventPriority.HIGHEST)
//    public void onChat(AsyncPlayerChatEvent event) {
//        Player player = event.getPlayer();
//        String message = event.getMessage();
//        String itemPlaceholder = plugin.getConfig().getString("messageToReplace");
//
//        boolean showItem = plugin.getConfig().getBoolean("showItem");
//        if (showItem) {
//            assert itemPlaceholder != null;
//            if (message.contains(itemPlaceholder)) {
//                ItemStack heldItem = player.getInventory().getItemInMainHand();
//                if (heldItem.getType() == Material.AIR) {
//                    event.setCancelled(true);
//                    player.sendMessage(ChatColor.RED + "You need to hold an item to send: " + itemPlaceholder);
//                } else {
//                    ItemMeta meta = heldItem.getItemMeta();
//                    String heldItemName = meta != null && meta.hasDisplayName()
//                            ? meta.getDisplayName()
//                            : heldItem.getType().toString().toLowerCase().replace("_", " ");

//                    String itemDetailsStr = getItemDetails(heldItem);
//
//                    String[] parts = message.split(itemPlaceholder);
//                    ComponentBuilder builder = new ComponentBuilder();
//
//                    boolean first = true;
//                    for (String part : parts) {
//                        if (first) {
//                            first = false;
//                        }
//                        else {
//                            TextComponent itemComponent = new TextComponent(heldItemName);
//                            BaseComponent[] itemDetailsComp = new ComponentBuilder(itemDetailsStr).color(net.md_5.bungee.api.ChatColor.GRAY).italic(true).create();
//                            itemComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(itemDetailsComp)));
//                            builder.append(itemComponent);
//                        }
//
//                        builder.append(part);
//                    }
//
//                    String format = event.getFormat();
//
//                    String formatted = String.format(format, player.getName(), event.getMessage());
//                    event.setCancelled(true);
//
//                    BaseComponent[] components = builder.create();
//                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
//                        onlinePlayers.spigot().sendMessage(components);
//                    }
//                }
//            }
//        }
//    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        String itemPlaceholder = plugin.getConfig().getString("messageToReplace");

        boolean showItem = plugin.getConfig().getBoolean("showItem");
        if (showItem && itemPlaceholder != null && message.contains(itemPlaceholder)) {
            ItemStack heldItem = player.getInventory().getItemInMainHand();

            if (heldItem.getType() == Material.AIR) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You need to hold an item to send: " + itemPlaceholder);
                return;
            }

            ItemMeta meta = heldItem.getItemMeta();
            String heldItemName = meta != null && meta.hasDisplayName()
                    ? meta.getDisplayName()
                    : heldItem.getType().toString().toLowerCase().replace("_", " ");

            String itemDetails = getItemDetails(heldItem);
            String formatted = message.replace(itemPlaceholder, heldItemName);
            event.setMessage(formatted);
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                onlinePlayers.sendMessage(itemDetails);
            }
        }
    }

    private String getItemDetails(ItemStack itemStack) {
        StringBuilder details = new StringBuilder();
        Material material = itemStack.getType();
        String name;
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
            name = itemStack.getItemMeta().getDisplayName();
        } else {
            name = material.toString().toLowerCase().replace("_", " ");
        }
        List<String> lore = itemStack.getItemMeta().getLore();
        int amount = itemStack.getAmount();

        details.append(ChatColor.GRAY + "Material: ").append(material.toString()).append("\n");
        details.append(ChatColor.GRAY + "Name: ").append(name).append("\n");
        details.append(ChatColor.GRAY + "Lore: ").append(lore != null ? lore.toString() : "N/A").append("\n");
        details.append(ChatColor.GRAY + "Amount: ").append(amount);

        return details.toString();
    }

    @EventHandler
    public void onChatRude(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String plainMessage = event.getMessage();
        // Blacklist Message
        List<String> blockedMessages = plugin.getConfig().getStringList("blacklist");
        String blockedMessageTemplate = plugin.getConfig().getString("blacklistMessage");

        for (String blockedWord : blockedMessages) {
            if (plainMessage.toLowerCase().contains(blockedWord.toLowerCase())) {
                event.setCancelled(true);
                String blockedMessage = blockedMessageTemplate.replace("%word%", blockedWord).replace("%player%", player.getName()).replace("%message%", plainMessage);
                player.sendMessage(ColorUtil.color(blockedMessage));
                return;
            }
        }
    }
    @EventHandler
    public void onChatAdvertise(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String plainMessage = event.getMessage();
        // Blacklist Message
        List<String> blockedMessages = plugin.getConfig().getStringList("disabledAd");
        String blockedMessageTemplate = plugin.getConfig().getString("blacklistAdMessage");

        for (String blockedWord : blockedMessages) {
            if (plainMessage.toLowerCase().contains(blockedWord.toLowerCase())) {
                event.setCancelled(true);
                String blockedMessage = blockedMessageTemplate.replace("%word%", blockedWord).replace("%player%", player.getName()).replace("%message%", plainMessage);
                player.sendMessage(ColorUtil.color(blockedMessage));
                return;
            }
        }
    }
}