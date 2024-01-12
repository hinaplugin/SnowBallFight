package com.hinaplugin.snowballfight.system;

import com.hinaplugin.snowballfight.SnowBallFight;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BlueTeamAddBallTimer extends BukkitRunnable {
    final int amount = SnowBallFight.getPlugin().getConfig().getInt("add-ball", 16);
    final Warehouse warehouse = SnowBallFight.getPlugin().getWarehouse();

    public BlueTeamAddBallTimer() {
    }

    public void run() {

        for (final Player player : this.warehouse.getBlueTeam().keySet()) {
            if (this.warehouse.getBlueTeam().get(player) != 0) {
                player.getInventory().addItem((new CreateBall()).Create(this.amount));
            }
        }

    }
}
