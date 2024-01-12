package com.hinaplugin.snowballfight.system;

import com.hinaplugin.snowballfight.SnowBallFight;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

public class LifeScoreboard extends TeamManager {
    private final SnowBallFight plugin = SnowBallFight.getPlugin();
    private final Scoreboard scoreboard = SnowBallFight.getPlugin().getScoreboard();

    public LifeScoreboard() {
    }

    public void Create() {
        this.Unregister();
        this.plugin.setRedObjective(this.scoreboard.registerNewObjective("RedTeamLife", Criteria.DUMMY, Component.text().content("赤チームライフ").build()));
        this.plugin.setBlueObjective(this.scoreboard.registerNewObjective("BlueTeamLife", Criteria.DUMMY, Component.text().content("青チームライフ").build()));
        this.CreateTeam();
    }

    public void SetTeamScoreboard(@NotNull final Player player, @NotNull final Integer life, @NotNull final Integer team) {
        if (team == 0) {
            this.plugin.getRedObjective().getScore(player).setScore(life);
        } else {
            this.plugin.getBlueObjective().getScore(player).setScore(life);
        }

    }

    public void Show() {
        this.plugin.getRedObjective().setDisplaySlot(DisplaySlot.SIDEBAR_TEAM_RED);
        this.plugin.getBlueObjective().setDisplaySlot(DisplaySlot.SIDEBAR_TEAM_BLUE);
    }

    public void Unregister() {
        Objective redObjective = this.scoreboard.getObjective("RedTeamLife");
        if (redObjective != null) {
            redObjective.unregister();
        }

        final Objective blueObjective = this.scoreboard.getObjective("BlueTeamLife");
        if (blueObjective != null) {
            blueObjective.unregister();
        }

        this.UnregisterTeam();
    }
}
