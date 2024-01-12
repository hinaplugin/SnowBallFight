package com.hinaplugin.snowballfight.system;

import com.hinaplugin.snowballfight.SnowBallFight;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

public class TeamManager {
    private final SnowBallFight plugin = SnowBallFight.getPlugin();
    private Team redTeam;
    private Team blueTeam;

    public TeamManager() {
    }

    public void CreateTeam() {
        this.UnregisterTeam();
        this.redTeam = this.plugin.getScoreboard().registerNewTeam("RedTeam");
        this.blueTeam = this.plugin.getScoreboard().registerNewTeam("BlueTeam");
        this.redTeam.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.NEVER);
        this.blueTeam.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.NEVER);
        this.redTeam.color(NamedTextColor.RED);
        this.blueTeam.color(NamedTextColor.BLUE);
        this.plugin.setRedTeam(this.redTeam);
        this.plugin.setBlueTeam(this.blueTeam);
    }

    public void UnregisterTeam() {
        this.redTeam = this.plugin.getScoreboard().getTeam("RedTeam");
        if (this.redTeam != null) {
            this.redTeam.unregister();
        }

        this.blueTeam = this.plugin.getScoreboard().getTeam("BlueTeam");
        if (this.blueTeam != null) {
            this.blueTeam.unregister();
        }

    }
}
