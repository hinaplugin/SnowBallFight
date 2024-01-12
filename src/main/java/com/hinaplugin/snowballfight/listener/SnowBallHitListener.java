package com.hinaplugin.snowballfight.listener;

import com.hinaplugin.snowballfight.SnowBallFight;
import com.hinaplugin.snowballfight.system.InvincibleTimer;
import com.hinaplugin.snowballfight.system.VictoryJudgment;
import com.hinaplugin.snowballfight.system.Warehouse;
import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class SnowBallHitListener implements Listener {
    private final SnowBallFight plugin = SnowBallFight.getPlugin();
    private final Warehouse warehouse = SnowBallFight.getPlugin().getWarehouse();

    public SnowBallHitListener() {
    }

    @EventHandler
    public void onSnowBallHit(final EntityDamageByEntityEvent event) {
        if (SnowBallFight.getPlugin().isStart()) {
            final Entity target = event.getEntity();
            final Entity attacker = event.getDamager();
            if (target instanceof Player) {
                if (attacker instanceof Snowball) {
                    final Player targetPlayer = (Player)target;
                    final ItemStack snowball = ((Snowball)attacker).getItem();
                    final PersistentDataContainer container = snowball.getItemMeta().getPersistentDataContainer();
                    if ("sb".equalsIgnoreCase(container.get(SnowBallFight.getPlugin().getKey(), PersistentDataType.STRING))) {
                        if (!(Boolean)this.warehouse.getInvincible().get(targetPlayer)) {
                            final ProjectileSource source = ((Snowball)attacker).getShooter();
                            if (source instanceof Player) {
                                final Player attackPlayer = (Player)source;
                                if (!targetPlayer.getName().equalsIgnoreCase(attackPlayer.getName())) {
                                    if (!this.warehouse.getRedTeam().containsKey(targetPlayer) || !this.warehouse.getRedTeam().containsKey(attackPlayer)) {
                                        if (!this.warehouse.getBlueTeam().containsKey(targetPlayer) || !this.warehouse.getBlueTeam().containsKey(attackPlayer)) {
                                            targetPlayer.sendMessage(ChatColor.RED + attackPlayer.getName() + "に撃破されました...");

                                            for (Player player : this.warehouse.getJoinPlayers()) {
                                                if (!attackPlayer.getName().equalsIgnoreCase(targetPlayer.getName())) {
                                                    player.sendMessage(ChatColor.AQUA + attackPlayer.getName() + "が" + targetPlayer.getName() + "を撃破しました!");
                                                }
                                            }

                                            this.warehouse.getInvincible().replace(targetPlayer, true);
                                            (new InvincibleTimer(targetPlayer)).runTaskLater(this.plugin, 200L);
                                            this.ReduceLife(targetPlayer);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void ReduceLife(final @NotNull Player targetPlayer) {
        (new BukkitRunnable() {
            public void run() {
                targetPlayer.setGameMode(GameMode.SPECTATOR);
                this.cancel();
            }
        }).runTaskLater(SnowBallFight.getPlugin(), 0L);
        Iterator var2;
        Player player;
        if (this.warehouse.getRedTeam().containsKey(targetPlayer)) {
            this.warehouse.getRedTeam().replace(targetPlayer, this.warehouse.getRedTeam().get(targetPlayer) - 1);
            this.plugin.getRedObjective().getScore(targetPlayer).setScore(this.plugin.getRedObjective().getScore(targetPlayer).getScore() - 1);
            if (this.warehouse.getRedTeam().get(targetPlayer) == 0) {
                var2 = this.warehouse.getJoinPlayers().iterator();

                while(var2.hasNext()) {
                    player = (Player)var2.next();
                    player.sendMessage(ChatColor.AQUA + targetPlayer.getName() + "が脱落しました...");
                }
            } else {
                targetPlayer.sendMessage(ChatColor.GREEN + "5秒後に復活します...");
                (new BukkitRunnable() {
                    public void run() {
                        final World redWorld = SnowBallHitListener.this.plugin.getServer().getWorld(SnowBallHitListener.this.plugin.getConfig().getString("red-location.world", "world"));
                        final Location redLocation = new Location(redWorld, SnowBallHitListener.this.plugin.getConfig().getDouble("red-location.x", 0.0), SnowBallHitListener.this.plugin.getConfig().getInt("red-location.y", 0), SnowBallHitListener.this.plugin.getConfig().getDouble("red-location.z", 0.0), (float)SnowBallHitListener.this.plugin.getConfig().getInt("red-location.facing", 0), 0.0F);
                        targetPlayer.teleport(redLocation);
                        targetPlayer.setGameMode(GameMode.ADVENTURE);
                        this.cancel();
                    }
                }).runTaskLater(SnowBallFight.getPlugin(), 100L);
            }
        } else {
            this.warehouse.getBlueTeam().replace(targetPlayer, this.warehouse.getBlueTeam().get(targetPlayer) - 1);
            this.plugin.getBlueObjective().getScore(targetPlayer).setScore(this.plugin.getBlueObjective().getScore(targetPlayer).getScore() - 1);
            if (this.warehouse.getBlueTeam().get(targetPlayer) == 0) {
                var2 = this.warehouse.getJoinPlayers().iterator();

                while(var2.hasNext()) {
                    player = (Player)var2.next();
                    player.sendMessage(ChatColor.AQUA + targetPlayer.getName() + "が脱落しました...");
                }
            } else {
                targetPlayer.sendMessage(ChatColor.GREEN + "5秒後に復活します...");
                (new BukkitRunnable() {
                    public void run() {
                        final World blueWorld = SnowBallHitListener.this.plugin.getServer().getWorld(SnowBallHitListener.this.plugin.getConfig().getString("blue-location.world", "world"));
                        final Location blueLocation = new Location(blueWorld, SnowBallHitListener.this.plugin.getConfig().getDouble("blue-location.x", 0.0), SnowBallHitListener.this.plugin.getConfig().getInt("blue-location.y", 0), SnowBallHitListener.this.plugin.getConfig().getDouble("blue-location.z", 0.0), (float)SnowBallHitListener.this.plugin.getConfig().getInt("blue-location.facing", 0), 0.0F);
                        targetPlayer.teleport(blueLocation);
                        targetPlayer.setGameMode(GameMode.ADVENTURE);
                        this.cancel();
                    }
                }).runTaskLater(SnowBallFight.getPlugin(), 100L);
            }
        }

        final VictoryJudgment victoryJudgment = new VictoryJudgment();
        if (victoryJudgment.Judgment()) {
            victoryJudgment.Victory();
        }

    }
}
