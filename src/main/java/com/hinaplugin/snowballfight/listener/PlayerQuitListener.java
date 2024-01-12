package com.hinaplugin.snowballfight.listener;

import com.hinaplugin.snowballfight.SnowBallFight;
import com.hinaplugin.snowballfight.system.VictoryJudgment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final SnowBallFight plugin = SnowBallFight.getPlugin();

    public PlayerQuitListener() {
    }

    @EventHandler
    public void onLogout(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        if (this.plugin.isStart() || this.plugin.isWait()) {
            if (this.plugin.getWarehouse().getJoinPlayers().contains(player)) {
                this.plugin.getWarehouse().getLogoutPlayers().add(player);
                this.plugin.getWarehouse().getJoinPlayers().remove(player);
                this.plugin.getWarehouse().getRedTeam().remove(player);
                this.plugin.getWarehouse().getBlueTeam().remove(player);
                if (this.plugin.getWarehouse().getRedTeam().containsKey(player)) {
                    this.plugin.getRedObjective().getScore(player).resetScore();
                } else {
                    this.plugin.getBlueObjective().getScore(player).resetScore();
                }

                final VictoryJudgment victoryJudgment = new VictoryJudgment();
                if (victoryJudgment.Judgment()) {
                    victoryJudgment.Victory();
                }

            }
        }
    }
}
