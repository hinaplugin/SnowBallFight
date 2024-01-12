package com.hinaplugin.snowballfight;

import com.hinaplugin.snowballfight.commands.CommandHandler;
import com.hinaplugin.snowballfight.listener.*;
import com.hinaplugin.snowballfight.system.LifeScoreboard;
import com.hinaplugin.snowballfight.system.Timer;
import com.hinaplugin.snowballfight.system.Warehouse;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public final class SnowBallFight extends JavaPlugin {
    private static SnowBallFight plugin;
    private final NamespacedKey key = new NamespacedKey(this, "SnowBallFight");
    private final Warehouse warehouse = new Warehouse();
    private BukkitRunnable runnable = null;
    private BukkitRunnable redRunnable = null;
    private BukkitRunnable blueRunnable = null;
    private Scoreboard scoreboard;
    private Objective redObjective;
    private Objective blueObjective;
    private Team redTeam = null;
    private Team blueTeam = null;
    private boolean start = false;
    private boolean wait = false;
    private Timer timer;

    public SnowBallFight() {
    }

    public void onEnable() {
        try {
            plugin = this;
            File configFile = new File(this.getDataFolder(), "config.yml");
            if (!configFile.exists()) {
                this.saveDefaultConfig();
            }

            this.getServer().getPluginManager().registerEvents(new ArmorInventoryClickListener(), this);
            this.getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
            this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
            this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
            this.getServer().getPluginManager().registerEvents(new SnowBallHitListener(), this);
            this.getServer().getPluginManager().registerEvents(new WaitTimeMoveListener(), this);
            ScoreboardManager scoreboardManager = this.getServer().getScoreboardManager();
            this.scoreboard = scoreboardManager.getMainScoreboard();
            PluginCommand command = this.getCommand("snowballfight");
            if (command != null) {
                command.setExecutor(new CommandHandler());
            }

            this.getLogger().info("SnowBallFight is Enabled!");
        } catch (final Exception exception) {
            new LogTrace(exception);
        }

    }

    public void onDisable() {
        try {
            if (this.timer != null) {
                this.timer.RemoveBossBar();
            }
            HandlerList.unregisterAll(this);
            Command command = this.getServer().getCommandMap().getCommand("snowballfight");
            if (command != null) {
                command.unregister(this.getServer().getCommandMap());
            }

            (new LifeScoreboard()).Unregister();
            if (this.start) {
                this.stopRunnable();
                this.timer.RemoveBossBar();
            }

            this.getLogger().info("SnowBallFight is Disabled!");
        } catch (final Exception exception) {
            new LogTrace(exception);
        }

    }

    public static SnowBallFight getPlugin() {
        return plugin;
    }

    public NamespacedKey getKey() {
        return this.key;
    }

    public Warehouse getWarehouse() {
        return this.warehouse;
    }

    public BukkitRunnable getRunnable() {
        return this.runnable;
    }

    public boolean isStart() {
        return this.start;
    }

    public void setRunnable(@Nullable BukkitRunnable runnable) {
        this.runnable = runnable;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setRedRunnable(@Nullable BukkitRunnable runnable) {
        this.redRunnable = runnable;
    }

    public void setBlueRunnable(@Nullable BukkitRunnable runnable) {
        this.blueRunnable = runnable;
    }

    public boolean isWait() {
        return this.wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    public Timer getTimer() {
        return this.timer;
    }

    public void setTimer(@Nullable Timer timer) {
        this.timer = timer;
    }

    public void stopRunnable() {
        this.runnable.cancel();
        this.redRunnable.cancel();
        this.blueRunnable.cancel();
        this.runnable = null;
        this.redRunnable = null;
        this.blueRunnable = null;
    }

    public void resetTeam() {
        this.redTeam = this.scoreboard.getTeam("RedTeam");
        if (this.redTeam != null) {
            this.redTeam.unregister();
        }

        this.blueTeam = this.scoreboard.getTeam("BlueTeam");
        if (this.blueTeam != null) {
            this.blueTeam.unregister();
        }

        this.redTeam = this.scoreboard.registerNewTeam("RedTeam");
        this.blueTeam = this.scoreboard.registerNewTeam("BlueTeam");
        this.redTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        this.blueTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
    }

    public Team getRedTeam() {
        return this.redTeam;
    }

    public void setRedTeam(@NotNull Team redTeam) {
        this.redTeam = redTeam;
    }

    public void setBlueTeam(@NotNull Team blueTeam) {
        this.blueTeam = blueTeam;
    }

    public Team getBlueTeam() {
        return this.blueTeam;
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public void setRedObjective(@NotNull Objective redObjective) {
        this.redObjective = redObjective;
    }

    public void setBlueObjective(@Nullable Objective blueObjective) {
        this.blueObjective = blueObjective;
    }

    public Objective getRedObjective() {
        return this.redObjective;
    }

    public Objective getBlueObjective() {
        return this.blueObjective;
    }
}
