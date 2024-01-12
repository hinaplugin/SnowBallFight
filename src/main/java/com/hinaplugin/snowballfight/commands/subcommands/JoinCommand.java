package com.hinaplugin.snowballfight.commands.subcommands;

import com.hinaplugin.snowballfight.LogTrace;
import com.hinaplugin.snowballfight.SnowBallFight;
import com.hinaplugin.snowballfight.system.Warehouse;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class JoinCommand {
    private final Warehouse warehouse = SnowBallFight.getPlugin().getWarehouse();

    public JoinCommand() {
    }

    public boolean Join(@NotNull CommandSender sender) {
        try {
            if (!sender.hasPermission("snowballfight.commands.join")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission perform this commands!");
            } else {
                if (sender instanceof Player) {
                    Player player = (Player)sender;
                    if (this.warehouse.getJoinPlayers().contains(player)) {
                        player.sendMessage(ChatColor.RED + "すでに待機室に入室しているため実行できません．");
                        return true;
                    }

                    if (SnowBallFight.getPlugin().isStart() || SnowBallFight.getPlugin().isWait()) {
                        player.sendMessage(ChatColor.RED + "すでにゲームが開始されているため実行できません．");
                        return true;
                    }

                    this.warehouse.getJoinPlayers().add(player);
                    player.sendMessage(ChatColor.AQUA + "待機室に入室しました．");
                } else {
                    sender.sendMessage("このコマンドはプレイヤーのみ実行できます．");
                }

            }
            return true;
        } catch (final Exception exception) {
            new LogTrace(exception);
            return false;
        }
    }
}
