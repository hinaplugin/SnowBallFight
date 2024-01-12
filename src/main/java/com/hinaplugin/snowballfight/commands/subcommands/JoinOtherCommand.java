package com.hinaplugin.snowballfight.commands.subcommands;

import com.google.common.collect.Lists;
import com.hinaplugin.snowballfight.LogTrace;
import com.hinaplugin.snowballfight.SnowBallFight;
import com.hinaplugin.snowballfight.system.Warehouse;
import java.util.Collection;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class JoinOtherCommand {
    private final Warehouse warehouse = SnowBallFight.getPlugin().getWarehouse();

    public JoinOtherCommand() {
    }

    public boolean onJoinOther(@NotNull CommandSender sender, @NotNull String target) {
        try {
            if (!sender.hasPermission("snowballfight.commands.joinother")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission perform this commands!");
            } else {
                Player player;
                if (!(sender instanceof Player) && !(sender instanceof ConsoleCommandSender)) {
                    if (sender instanceof BlockCommandSender) {
                        if (target.equalsIgnoreCase("@p")) {
                            Location location = ((BlockCommandSender)sender).getBlock().getLocation();
                            double radius = 0.0;
                            List<Player> playerList = Lists.newArrayList();

                            do {
                                Collection<Player> players = location.getNearbyPlayers(radius);

                                for (Player value : players) {
                                    player = value;
                                    if (!playerList.contains(player)) {
                                        if (!this.warehouse.getJoinPlayers().contains(player)) {
                                            this.warehouse.getJoinPlayers().add(player);
                                            break;
                                        }

                                        playerList.add(player);
                                    }
                                }

                                radius += 0.5;
                            } while(radius <= 100.0);
                        } else {
                            player = sender.getServer().getPlayer(target);
                            if (player != null && !this.warehouse.getJoinPlayers().contains(player)) {
                                this.warehouse.getJoinPlayers().add(player);
                            }
                        }
                    }
                } else {
                    player = sender.getServer().getPlayer(target);
                    if (player == null) {
                        sender.sendMessage(ChatColor.RED + target + "が見つかりませんでした．");
                        return true;
                    }

                    if (this.warehouse.getJoinPlayers().contains(player)) {
                        sender.sendMessage(ChatColor.RED + target + "はすでに参加しています．");
                        return true;
                    }

                    this.warehouse.getJoinPlayers().add(player);
                    sender.sendMessage(ChatColor.AQUA + target + "を待機室に参加させました．");
                }

            }
            return true;
        } catch (final Exception exception) {
            new LogTrace(exception);
            return false;
        }
    }
}
