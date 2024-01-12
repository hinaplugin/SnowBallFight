package com.hinaplugin.snowballfight.commands.subcommands;

import com.hinaplugin.snowballfight.LogTrace;
import com.hinaplugin.snowballfight.SnowBallFight;
import com.hinaplugin.snowballfight.system.EndSystem;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StopCommand extends EndSystem {
    private final SnowBallFight plugin = SnowBallFight.getPlugin();

    public StopCommand() {
    }

    public boolean Stop(@NotNull CommandSender sender) {
        try {
            if (!sender.hasPermission("snowballfight.commands.stop")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission perform this commands!");
                return true;
            } else if (!this.plugin.isStart() && !this.plugin.isWait()) {
                sender.sendMessage(ChatColor.RED + "まだゲームが開始されていないため実行できません．");
                return true;
            } else {
                List<Player> playerList = this.plugin.getWarehouse().getJoinPlayers();
                this.EndTP(100L, 0);
                this.plugin.setWait(false);
                this.plugin.setStart(false);

                for (Player player : playerList) {
                    player.sendMessage(ChatColor.AQUA + "ゲームを中止しました．");
                }

                return true;
            }
        } catch (final Exception exception) {
            new LogTrace(exception);
            return false;
        }
    }
}
