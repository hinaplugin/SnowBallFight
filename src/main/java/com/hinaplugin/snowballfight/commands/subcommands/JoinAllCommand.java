package com.hinaplugin.snowballfight.commands.subcommands;

import com.hinaplugin.snowballfight.LogTrace;
import com.hinaplugin.snowballfight.SnowBallFight;
import com.hinaplugin.snowballfight.system.Warehouse;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class JoinAllCommand {
    private final SnowBallFight plugin = SnowBallFight.getPlugin();
    private final Warehouse warehouse = SnowBallFight.getPlugin().getWarehouse();

    public JoinAllCommand() {
    }

    public boolean JoinAll(@NotNull CommandSender sender) {
        try {
            if (!sender.hasPermission("snowballfight.commands.joinall")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission perform this commands!");
                return true;
            } else if (!SnowBallFight.getPlugin().isStart() && !SnowBallFight.getPlugin().isWait()) {

                for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
                    if (!this.warehouse.getJoinPlayers().contains(player)) {
                        this.warehouse.getJoinPlayers().add(player);
                        player.sendMessage(ChatColor.GREEN + "オンラインメンバー全員が待機室に参加しました．");
                    }
                }

                sender.sendMessage(ChatColor.AQUA + "オンラインメンバー全員を待機室に参加させました．");
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "すでにゲームが開始されているため実行できません．");
                return true;
            }
        } catch (final Exception exception) {
            new LogTrace(exception);
            return false;
        }
    }
}
