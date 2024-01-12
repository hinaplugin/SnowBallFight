package com.hinaplugin.snowballfight.system;

import com.hinaplugin.snowballfight.SnowBallFight;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class InvincibleTimer extends BukkitRunnable {
    private final Player player;

    public InvincibleTimer(final Player player) {
        this.player = player;
    }

    public void run() {
        SnowBallFight.getPlugin().getWarehouse().getInvincible().replace(this.player, false);
        this.cancel();
    }
}