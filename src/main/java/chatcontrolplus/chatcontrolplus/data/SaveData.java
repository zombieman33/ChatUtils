package chatcontrolplus.chatcontrolplus.data;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class SaveData {
    public static void saveData(FileConfiguration config, File configFile) {
        try {
            config.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while saving the player data config.", e);
        }
    }
}
