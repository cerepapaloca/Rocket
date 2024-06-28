package Plugin.utiles;

import Plugin.command.CommandRocket;
import Plugin.RocketCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Chat {
    public static class PlayerDeathListener implements Listener {

        private final RocketCore plugin;;
        private final Plugin.command.CommandRocket CommandRocket;

        public PlayerDeathListener(RocketCore plugin) {
            this.plugin = plugin;
            this.CommandRocket = new CommandRocket(plugin);
        }
        //Cancela los mesajes de muerte de manera temporal
        @EventHandler
        public void onPlayerDeath(PlayerDeathEvent event) {
            if (plugin.cm()) {
                event.setDeathMessage(null);
                RocketCore.cm = false;
            }
        }
    }
}
