package com.hinaplugin.snowballfight.listener;

import com.hinaplugin.snowballfight.SnowBallFight;
import com.hinaplugin.snowballfight.system.Warehouse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class WaitTimeMoveListener implements Listener {
    public WaitTimeMoveListener() {
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
        final Warehouse warehouse = SnowBallFight.getPlugin().getWarehouse();
        final Player player = event.getPlayer();
        if (SnowBallFight.getPlugin().isWait()) {
            if (warehouse.getRedTeam().containsKey(player) || warehouse.getBlueTeam().containsKey(player)) {
                event.setCancelled(true);
            }

        }
    }
}
