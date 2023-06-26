package chatcontrolplus.chatcontrolplus.data;

import chatcontrolplus.chatcontrolplus.ChatUtils;
import chatcontrolplus.chatcontrolplus.utils.ColorUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class SavePlayerData {
    private ChatUtils plugin;

    public SavePlayerData(ChatUtils plugin) {
        this.plugin = plugin;
    }

    public void saveData(Player p) {
        File playerDataFile = new File(plugin.getDataFolder(), "playerData.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        UUID pUUID = p.getUniqueId();
        String pName = p.getName();
        boolean wantsMuteSpy = playerDataConfig.getBoolean("messageSpy.player." + pUUID + ".ms", false);
        if (!wantsMuteSpy) {
            playerDataConfig.set("messageSpy.player." + pUUID, true);
            playerDataConfig.set("messageSpy.player." + pUUID + ".ign", pName);
            playerDataConfig.set("messageSpy.player." + pUUID + ".ms", true);
            p.sendMessage(ColorUtil.color("&aYou enabled message spy."));
        } else {
            playerDataConfig.set("messageSpy.player." + pUUID, false);
            playerDataConfig.set("messageSpy.player." + pUUID + ".ign", pName);
            playerDataConfig.set("messageSpy.player." + pUUID + ".ms", false);
            p.sendMessage(ColorUtil.color("&cYou disabled message spy."));
        }
        SaveData.saveData(playerDataConfig, playerDataFile);
    }
}

