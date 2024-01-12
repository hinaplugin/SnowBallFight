package com.hinaplugin.snowballfight.system;

import com.google.common.collect.Lists;
import com.hinaplugin.snowballfight.LogTrace;
import com.hinaplugin.snowballfight.SnowBallFight;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.bukkit.entity.Player;

public class TeamDivision extends LifeScoreboard {
    private final SnowBallFight plugin = SnowBallFight.getPlugin();
    private final Warehouse warehouse = SnowBallFight.getPlugin().getWarehouse();

    public TeamDivision() {
    }

    public boolean Division() {
        try {
            if (!this.warehouse.getJoinPlayers().isEmpty() && this.warehouse.getJoinPlayers().size() >= 2) {
                int life = SnowBallFight.getPlugin().getConfig().getInt("life", 3);
                List<Player> randomPlayer = Lists.newArrayList(this.warehouse.getJoinPlayers());
                Random random = new Random();

                int addLife;
                for(int i = 0; !randomPlayer.isEmpty(); ++i) {
                    addLife = random.nextInt(randomPlayer.size());
                    if (i % 2 == 0) {
                        this.warehouse.getRedTeam().put(randomPlayer.get(addLife), life);
                        this.plugin.getRedTeam().addEntry(randomPlayer.get(addLife).getName());
                        this.SetTeamScoreboard(randomPlayer.get(addLife), life, 0);
                    } else {
                        this.warehouse.getBlueTeam().put(randomPlayer.get(addLife), life);
                        this.plugin.getBlueTeam().addEntry(randomPlayer.get(addLife).getName());
                        this.SetTeamScoreboard(randomPlayer.get(addLife), life, 1);
                    }

                    randomPlayer.remove(addLife);
                }

                if (this.warehouse.getRedTeam().size() != this.warehouse.getBlueTeam().size()) {
                    addLife = life;

                    while(addLife > 0) {
                        for(final Iterator<Player> var6 = this.warehouse.getBlueTeam().keySet().iterator(); var6.hasNext(); --addLife) {
                            final Player player = var6.next();
                            if (addLife <= 0) {
                                break;
                            }

                            this.warehouse.getBlueTeam().replace(player, this.warehouse.getBlueTeam().get(player) + 1);
                            this.plugin.getBlueObjective().getScore(player).setScore(this.plugin.getBlueObjective().getScore(player).getScore() + 1);
                        }
                    }
                }

                this.Show();
                return true;
            } else {
                return false;
            }
        } catch (final Exception exception) {
            new LogTrace(exception);
            return false;
        }
    }
}
