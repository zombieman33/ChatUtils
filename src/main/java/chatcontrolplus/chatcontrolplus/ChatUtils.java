package chatcontrolplus.chatcontrolplus;

import chatcontrolplus.chatcontrolplus.commands.*;
import chatcontrolplus.chatcontrolplus.listeners.*;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.security.Permission;

public final class ChatUtils extends JavaPlugin {
    private Permission permission;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Configs
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            getLogger().info("Config file not found, creating...");
            saveResource("config.yml", false);
        }

        // Commands
        StaffChatCommand staffChatCommand = new StaffChatCommand(this);
        PluginCommand staffCmd = getCommand("staffchat");
        if (staffCmd != null) staffCmd.setExecutor(staffChatCommand);
        AdminChatCommand adminChatCommand = new AdminChatCommand(this);
        PluginCommand adminCmd = getCommand("adminchat");
        if (adminCmd != null) adminCmd.setExecutor(adminChatCommand);

        ClearChatCommand clearChatCommand = new ClearChatCommand(this);
        PluginCommand clearChatCmd = getCommand("clearchat");
        if (clearChatCmd != null) clearChatCmd.setExecutor(clearChatCommand);

        MuteChatCommand muteChatCommand = new MuteChatCommand(this);
        PluginCommand muteChatCmd = getCommand("mutechat");
        if (muteChatCmd != null) muteChatCmd.setExecutor(muteChatCommand);

        SudoCommand sudoCommand = new SudoCommand(this);
        PluginCommand sudoCmd = getCommand("sudo");
        if (sudoCmd != null) sudoCmd.setExecutor(sudoCommand);

        BroadCastCommand broadCastCommand = new BroadCastCommand(this);
        PluginCommand broadcastCmd = getCommand("broadcast");
        if (broadcastCmd != null) broadcastCmd.setExecutor(broadCastCommand);

        PlayerInfoCommand playerInfoCommand = new PlayerInfoCommand(this);
        PluginCommand playerInfoCmd = getCommand("players");
        if (playerInfoCmd != null) playerInfoCmd.setExecutor(playerInfoCommand);

        MessageCommand messageCommand = new MessageCommand(this);
        PluginCommand messageCmd = getCommand("message");
        if (messageCmd != null) messageCmd.setExecutor(messageCommand);

        MainCommands mainCommand = new MainCommands(this);
        PluginCommand mainCmd = getCommand("chatutils");
        if (mainCmd != null) mainCmd.setExecutor(mainCommand);

        getServer().getPluginManager().registerEvents(new ClearChatListener(this), this);
        getServer().getPluginManager().registerEvents(new MuteChatListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatConfigurations(this), this);
        getServer().getPluginManager().registerEvents(new EmojiListener(this), this);
        getServer().getPluginManager().registerEvents(new TimeMessageListener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
