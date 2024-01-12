package com.hinaplugin.snowballfight.commands.subcommands;

import com.hinaplugin.snowballfight.LogTrace;
import com.hinaplugin.snowballfight.SnowBallFight;
import com.hinaplugin.snowballfight.system.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class StartCommand extends TeamDivision {
    private final SnowBallFight plugin = SnowBallFight.getPlugin();
    private final Warehouse warehouse = SnowBallFight.getPlugin().getWarehouse();

    public StartCommand() {
    }

    public boolean Start(@NotNull CommandSender sender) {
        try {
            if (!sender.hasPermission("snowballfight.commands.start")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission perform this commands!");
                return true;
            } else {
                if (!this.plugin.getConfig().getBoolean("out-player", false)) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("参加プレイヤー以外からの開始コマンドが無効に設定されているため実行できません．");
                        return true;
                    }

                    Player player = (Player)sender;
                    if (!this.warehouse.getJoinPlayers().contains(player)) {
                        sender.sendMessage(ChatColor.RED + "参加プレイヤー以外からの開始コマンドが無効に設定されているため実行できません．");
                        return true;
                    }
                }

                if (!this.plugin.isStart() && !this.plugin.isWait()) {
                    if (!this.warehouse.getJoinPlayers().isEmpty() && this.warehouse.getJoinPlayers().size() >= 2) {
                        this.plugin.setTimer(new Timer());
                        this.Create();
                        if (!this.Division()) {
                            this.ErrorAnnounce(this.plugin.getTimer());
                            return false;
                        } else {

                            for (final Player player : this.warehouse.getJoinPlayers()) {
                                player.getInventory().clear();
                            }

                            World redWorld = this.plugin.getServer().getWorld(this.plugin.getConfig().getString("red-location.world", "world"));
                            if (redWorld == null) {
                                this.ErrorAnnounce(this.plugin.getTimer());
                                if (this.plugin.getRunnable() != null) {
                                    this.plugin.getRunnable().cancel();
                                    this.plugin.setRunnable(null);
                                }

                                return false;
                            } else {
                                final Location redLocation = new Location(redWorld, this.plugin.getConfig().getDouble("red-location.x", 0.0), (double)this.plugin.getConfig().getInt("red-location.y", 0), this.plugin.getConfig().getDouble("red-location.z", 0.0), (float)this.plugin.getConfig().getInt("red-location.facing", 0), 0.0F);

                                for (final Player player : this.warehouse.getRedTeam().keySet()) {
                                    (new BukkitRunnable() {
                                        public void run() {
                                            player.teleport(redLocation);
                                            player.setGameMode(GameMode.ADVENTURE);
                                            this.cancel();
                                        }
                                    }).runTaskLater(SnowBallFight.getPlugin(), 0L);
                                }

                                final World blueWorld = this.plugin.getServer().getWorld(this.plugin.getConfig().getString("blue-location.world", "world"));
                                if (blueWorld == null) {
                                    this.ErrorAnnounce(this.plugin.getTimer());
                                    if (this.plugin.getRunnable() != null) {
                                        this.plugin.getRunnable().cancel();
                                        this.plugin.setRunnable(null);
                                    }

                                    return false;
                                } else {
                                    final Location blueLocation = new Location(blueWorld, this.plugin.getConfig().getDouble("blue-location.x", 0.0), (double)this.plugin.getConfig().getInt("blue-location.y", 0), this.plugin.getConfig().getDouble("blue-location.z", 0.0), (float)this.plugin.getConfig().getInt("blue-location.facing", 0), 0.0F);
                                    Iterator<Player> var6 = this.warehouse.getBlueTeam().keySet().iterator();

                                    Player player;
                                    while(var6.hasNext()) {
                                        player = var6.next();
                                        final Player finalPlayer = player;
                                        (new BukkitRunnable() {
                                            public void run() {
                                                finalPlayer.teleport(blueLocation);
                                                finalPlayer.setGameMode(GameMode.ADVENTURE);
                                                this.cancel();
                                            }
                                        }).runTaskLater(SnowBallFight.getPlugin(), 0L);
                                    }

                                    this.plugin.setWait(true);
                                    this.StartCount();
                                    var6 = this.warehouse.getRedTeam().keySet().iterator();

                                    while(var6.hasNext()) {
                                        player = var6.next();
                                        (new CreateUniform()).RedUniform(player);
                                    }

                                    var6 = this.warehouse.getBlueTeam().keySet().iterator();

                                    while(var6.hasNext()) {
                                        player = var6.next();
                                        (new CreateUniform()).BlueUniform(player);
                                    }

                                    long time = (long)SnowBallFight.getPlugin().getConfig().getInt("add-time", 15) * 20L;
                                    RedTeamAddBallTimer redRunnable = new RedTeamAddBallTimer();
                                    redRunnable.runTaskTimer(SnowBallFight.getPlugin(), time + 100L, time);
                                    SnowBallFight.getPlugin().setRedRunnable(redRunnable);
                                    BlueTeamAddBallTimer blueRunnable = new BlueTeamAddBallTimer();
                                    blueRunnable.runTaskTimer(SnowBallFight.getPlugin(), time + 100L, time);
                                    SnowBallFight.getPlugin().setBlueRunnable(blueRunnable);
                                    this.warehouse.CopyMap();
                                    return true;
                                }
                            }
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "参加人数が足りないため実行できません．");
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "すでに開始されているため実行できません．");
                    return true;
                }
            }
        } catch (final Exception exception) {
            new LogTrace(exception);
            return false;
        }
    }

    private void ErrorAnnounce(final Timer timer) {
        Iterator var2;
        Player player;
        if (!this.warehouse.getJoinPlayers().isEmpty()) {
            var2 = this.warehouse.getJoinPlayers().iterator();

            while(var2.hasNext()) {
                player = (Player)var2.next();
                player.sendMessage(ChatColor.RED + "エラーが発生したためチームが解散しました．");
            }
        }

        if (!this.warehouse.getRedTeam().isEmpty()) {
            var2 = this.warehouse.getRedTeam().keySet().iterator();

            while(var2.hasNext()) {
                player = (Player)var2.next();
                player.sendMessage(ChatColor.RED + "エラーが発生したためチームが解散しました．");
            }
        }

        if (!this.warehouse.getBlueTeam().isEmpty()) {
            var2 = this.warehouse.getBlueTeam().keySet().iterator();

            while(var2.hasNext()) {
                player = (Player)var2.next();
                player.sendMessage(ChatColor.RED + "エラーが発生したためチームが解散しました．");
            }
        }

        timer.RemoveBossBar();
        this.warehouse.TeamReset();
    }

    private void StartCount() {
        final int[] t = new int[]{5};
        (new BukkitRunnable() {
            public void run() {
                Iterator var1;
                Player player;
                var1 = StartCommand.this.warehouse.getJoinPlayers().iterator();
                if (t[0] == 0) {

                    while(var1.hasNext()) {
                        player = (Player)var1.next();
                        player.sendTitlePart(TitlePart.TITLE, Component.text().content("START").color(TextColor.color(0, 255, 0)).build());
                    }

                    if (!StartCommand.this.plugin.getTimer().StartTimer()) {
                        StartCommand.this.ErrorAnnounce(StartCommand.this.plugin.getTimer());
                    }

                    int amount = SnowBallFight.getPlugin().getConfig().getInt("start-ball", 3);
                    Iterator<Player> var5 = StartCommand.this.warehouse.getRedTeam().keySet().iterator();

                    Player playerx;
                    while(var5.hasNext()) {
                        playerx = (Player)var5.next();
                        playerx.getInventory().addItem(new CreateBall().Create(amount));
                    }

                    var5 = StartCommand.this.warehouse.getBlueTeam().keySet().iterator();

                    while(var5.hasNext()) {
                        playerx = (Player)var5.next();
                        playerx.getInventory().addItem(new CreateBall().Create(amount));
                    }

                    StartCommand.this.plugin.setWait(false);
                    StartCommand.this.plugin.setStart(true);
                    this.cancel();
                } else {

                    while(var1.hasNext()) {
                        player = (Player)var1.next();
                        switch (t[0]) {
                            case 1:
                                player.sendTitlePart(TitlePart.TITLE, Component.text().content(String.valueOf(t[0])).color(TextColor.color(255, 0, 0)).build());
                                break;
                            case 2:
                            case 3:
                                player.sendTitlePart(TitlePart.TITLE, Component.text().content(String.valueOf(t[0])).color(TextColor.color(255, 255, 0)).build());
                                break;
                            case 4:
                            case 5:
                                player.sendTitlePart(TitlePart.TITLE, Component.text().content(String.valueOf(t[0])).color(TextColor.color(0, 255, 250)).build());
                        }
                    }
                }

                t[0]--;
            }
        }).runTaskTimer(this.plugin, 0L, 20L);
    }
}
