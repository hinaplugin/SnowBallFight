package com.hinaplugin.snowballfight.system;

import com.hinaplugin.snowballfight.SnowBallFight;
import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class EndSystem {
    private final SnowBallFight plugin = SnowBallFight.getPlugin();
    private final Warehouse warehouse;

    public EndSystem() {
        this.warehouse = this.plugin.getWarehouse();
    }

    public void EndTP(@NotNull final Long delay, @NotNull final Integer mode) {

        for (final Player player : this.warehouse.getJoinPlayers()) {
            player.sendMessage(ChatColor.GREEN + "試合終了処理を実行中...");
            player.sendMessage(ChatColor.GREEN + "5秒後にTPします...");
        }

        (new BukkitRunnable() {
            public void run() {
                final World world = EndSystem.this.plugin.getServer().getWorld(EndSystem.this.plugin.getConfig().getString("end-location.world", "world"));
                if (world != null) {
                    final Location locationx = new Location(world, EndSystem.this.plugin.getConfig().getDouble("end-location.x", 0.0), (double)EndSystem.this.plugin.getConfig().getInt("end-location.y", 0), EndSystem.this.plugin.getConfig().getDouble("end-location.z", 0.0), (float)EndSystem.this.plugin.getConfig().getInt("end-location.facing", 0), 0.0F);

                    for (final Player player : EndSystem.this.warehouse.getJoinPlayers()) {
                        player.getInventory().clear();
                        player.teleport(locationx);
                        player.setGameMode(EndSystem.this.plugin.getServer().getDefaultGameMode());
                    }
                } else {
                    final World otherWorld = (World)EndSystem.this.plugin.getServer().getWorlds().get(0);
                    final Location location = new Location(otherWorld, EndSystem.this.plugin.getConfig().getDouble("end-location.x", 0.0), (double)EndSystem.this.plugin.getConfig().getInt("end-location.y", 0), EndSystem.this.plugin.getConfig().getDouble("end-location.z", 0.0), (float)EndSystem.this.plugin.getConfig().getInt("end-location.facing", 0), 0.0F);

                    for (final Player playerx : EndSystem.this.warehouse.getJoinPlayers()) {
                        playerx.getInventory().clear();
                        playerx.teleport(location);
                        playerx.setGameMode(EndSystem.this.plugin.getServer().getDefaultGameMode());
                    }
                }

                if (mode == 0) {
                    EndSystem.this.End();
                }

                this.cancel();
            }
        }).runTaskLater(this.plugin, delay);
    }

    private void End() {
        this.plugin.stopRunnable();
        this.plugin.getTimer().RemoveBossBar();
        this.warehouse.TeamReset();
        this.plugin.resetTeam();
        (new LifeScoreboard()).Unregister();
    }
}
