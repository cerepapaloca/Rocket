package Plugin;
import Plugin.command.CommandRocket;
import Plugin.config.MainConfigManager;
import Plugin.utiles.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public class RocketCore extends JavaPlugin {

    public static boolean cm = false;//cm = ChatMessag;
    private MainConfigManager mainconfiganager;


    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Chat.PlayerDeathListener(this), this);
        MainConfigManager mainconfiganager = new MainConfigManager(this);
        mainconfiganager.mainconfigmanager(this);
        RegisterCommands();
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfigManager.prefix + "&2ON"));
    }


    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfigManager.prefix  + "&4OFF"));
    }

    private void RegisterCommands() {
        Objects.requireNonNull(this.getCommand("Rocket")).setExecutor(new CommandRocket(this));
    }

    public boolean cm() {
        return cm;
    }
}
