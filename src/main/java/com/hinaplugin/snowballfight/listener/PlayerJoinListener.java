package com.hinaplugin.snowballfight.listener;

import com.hinaplugin.snowballfight.SnowBallFight;
import com.hinaplugin.snowballfight.system.EndSystem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener extends EndSystem implements Listener {
    final SnowBallFight plugin = SnowBallFight.getPlugin();

    public PlayerJoinListener() {
    }

    @EventHandler
    public void onLogin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if (this.plugin.getWarehouse().getLogoutPlayers().contains(player)) {
            this.plugin.getWarehouse().getLogoutPlayers().remove(player);
            this.EndTP(0L, 1);
        }
    }
}
