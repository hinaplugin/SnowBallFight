package com.hinaplugin.snowballfight.listener;

import com.hinaplugin.snowballfight.SnowBallFight;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {
    private final SnowBallFight plugin = SnowBallFight.getPlugin();

    public PlayerDamageListener() {
    }

    @EventHandler
    public void onDamage(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player player = (Player)event.getEntity();
            if (!this.plugin.isStart() && !this.plugin.isWait()) {
                return;
            }

            if (this.plugin.getWarehouse().getJoinPlayers().contains(player)) {
                event.setCancelled(true);
            }
        }

    }
}
