package com.hinaplugin.snowballfight.system;

import com.hinaplugin.snowballfight.LogTrace;
import com.hinaplugin.snowballfight.SnowBallFight;
import java.util.Iterator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.entity.Player;

public class VictoryJudgment extends EndSystem {
    private final SnowBallFight plugin = SnowBallFight.getPlugin();
    private final Warehouse warehouse = SnowBallFight.getPlugin().getWarehouse();
    private int redLife = 0;
    private int blueLife = 0;

    public VictoryJudgment() {
    }

    public boolean Judgment() {
        try {
            Iterator var1;
            int life;
            for(var1 = this.warehouse.getRedTeam().values().iterator(); var1.hasNext(); this.redLife += life) {
                life = (Integer)var1.next();
            }

            for(var1 = this.warehouse.getBlueTeam().values().iterator(); var1.hasNext(); this.blueLife += life) {
                life = (Integer)var1.next();
            }

            return this.redLife == 0 || this.blueLife == 0;
        } catch (final Exception exception) {
            new LogTrace(exception);
            return false;
        }
    }

    public void Victory() {
        Iterator var1;
        Player player;
        if (this.redLife != 0) {
            var1 = this.warehouse.getJoinPlayers().iterator();

            while(var1.hasNext()) {
                player = (Player)var1.next();
                player.sendTitlePart(TitlePart.TITLE, ((TextComponent.Builder)Component.text().content("赤チームの勝利!").color(TextColor.color(255, 0, 0))).build());
            }
        } else {
            var1 = this.warehouse.getJoinPlayers().iterator();

            while(var1.hasNext()) {
                player = (Player)var1.next();
                player.sendTitlePart(TitlePart.TITLE, ((TextComponent.Builder)Component.text().content("青チームの勝利!").color(TextColor.color(0, 0, 255))).build());
            }
        }

        this.plugin.setStart(false);
        this.EndTP(100L, 0);
    }

    public void TimeOutVictory() {
        Iterator var1;
        int life;
        for(var1 = this.warehouse.getRedTeam().values().iterator(); var1.hasNext(); this.redLife += life) {
            life = (Integer)var1.next();
        }

        for(var1 = this.warehouse.getBlueTeam().values().iterator(); var1.hasNext(); this.blueLife += life) {
            life = (Integer)var1.next();
        }

        Player player;
        if (this.redLife > this.blueLife) {
            var1 = this.warehouse.getJoinPlayers().iterator();

            while(var1.hasNext()) {
                player = (Player)var1.next();
                player.sendTitlePart(TitlePart.TITLE, ((TextComponent.Builder)Component.text().content("赤チームの勝利!").color(TextColor.color(255, 0, 0))).build());
            }
        } else if (this.redLife < this.blueLife) {
            var1 = this.warehouse.getJoinPlayers().iterator();

            while(var1.hasNext()) {
                player = (Player)var1.next();
                player.sendTitlePart(TitlePart.TITLE, ((TextComponent.Builder)Component.text().content("青チームの勝利!").color(TextColor.color(0, 0, 255))).build());
            }
        } else {
            var1 = this.warehouse.getJoinPlayers().iterator();

            while(var1.hasNext()) {
                player = (Player)var1.next();
                player.sendTitlePart(TitlePart.TITLE, ((TextComponent.Builder)Component.text().content("引き分け!").color(TextColor.color(192, 192, 192))).build());
            }
        }

        this.plugin.setStart(false);
        this.EndTP(100L, 0);
    }
}
