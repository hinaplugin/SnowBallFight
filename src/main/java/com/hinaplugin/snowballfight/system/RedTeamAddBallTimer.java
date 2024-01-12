package com.hinaplugin.snowballfight.system;

import com.hinaplugin.snowballfight.SnowBallFight;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RedTeamAddBallTimer extends BukkitRunnable {
    final int amount = SnowBallFight.getPlugin().getConfig().getInt("add-ball", 16);
    final Warehouse warehouse = SnowBallFight.getPlugin().getWarehouse();

    public RedTeamAddBallTimer() {
    }

    public void run() {
        for (final Player player : this.warehouse.getRedTeam().keySet()) {
            if (this.warehouse.getRedTeam().get(player) != 0) {
                player.getInventory().addItem((new CreateBall()).Create(this.amount));
            }
        }
    }
}
