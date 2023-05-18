package chatcontrolplus.chatcontrolplus.utils;

import com.tcoded.legacycolorcodeparser.LegacyColorCodeParser;
import org.bukkit.ChatColor;

public class ColorUtil {

    public static String color(String string) {
        string = LegacyColorCodeParser.convertHexToLegacy('&', string);
        string = ChatColor.translateAlternateColorCodes('&', string);
        return string;
    }
}
