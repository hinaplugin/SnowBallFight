package com.hinaplugin.snowballfight.system;

import com.hinaplugin.snowballfight.LogTrace;
import com.hinaplugin.snowballfight.SnowBallFight;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer {
    private BossBar bar = null;
    private final SnowBallFight plugin = SnowBallFight.getPlugin();
    private final int mainTime;
    private int time;

    public Timer() {
        this.mainTime = this.plugin.getConfig().getInt("time", 300);
        this.time = this.mainTime;
    }

    private void CreateBossBar() {
        try {
            this.bar = Bukkit.createBossBar("", BarColor.GREEN, BarStyle.SOLID);
            Iterator<Player> var1 = this.plugin.getWarehouse().getBlueTeam().keySet().iterator();

            Player player;
            while(var1.hasNext()) {
                player = var1.next();
                this.bar.addPlayer(player);
            }

            var1 = this.plugin.getWarehouse().getRedTeam().keySet().iterator();

            while(var1.hasNext()) {
                player = var1.next();
                this.bar.addPlayer(player);
            }
        } catch (final Exception exception) {
            new LogTrace(exception);
        }

    }

    public void RemoveBossBar() {
        if (this.bar != null) {
            this.bar.removeAll();
            this.bar.setVisible(false);
        }
    }

    public boolean StartTimer() {
        try {
            this.CreateBossBar();
            this.bar.setVisible(true);
            final BukkitRunnable runnable = new BukkitRunnable() {
                public void run() {
                    Timer.this.time--;
                    if (Timer.this.time < 0) {
                        (new VictoryJudgment()).TimeOutVictory();
                        this.cancel();
                    } else {
                        Timer.this.bar.setTitle("試合残り時間: " + (new TimeChanger(Timer.this.time)).Change());
                        Timer.this.bar.setProgress((double)Timer.this.time / (double)Timer.this.mainTime);
                        double percent = (double)Timer.this.time / (double)Timer.this.mainTime * 100.0;
                        if (percent >= 50.0) {
                            Timer.this.bar.setColor(BarColor.GREEN);
                        } else if (percent >= 20.0) {
                            Timer.this.bar.setColor(BarColor.YELLOW);
                        } else {
                            Timer.this.bar.setColor(BarColor.RED);
                        }

                    }
                }
            };
            runnable.runTaskTimer(this.plugin, 0L, 20L);
            this.plugin.setRunnable(runnable);
            return true;
        } catch (final Exception exception) {
            new LogTrace(exception);
            return false;
        }
    }
}
