package com.hinaplugin.snowballfight.system;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.Player;

public class Warehouse {
    private final List<Player> joinPlayers = Lists.newArrayList();
    private final Map<Player, Integer> redTeam = Maps.newHashMap();
    private final Map<Player, Integer> blueTeam = Maps.newHashMap();
    private final List<Player> logoutPlayers = Lists.newArrayList();
    private final Map<Player, Boolean> invincible = Maps.newHashMap();

    public Warehouse() {
    }

    public List<Player> getJoinPlayers() {
        return this.joinPlayers;
    }

    public Map<Player, Integer> getRedTeam() {
        return this.redTeam;
    }

    public Map<Player, Integer> getBlueTeam() {
        return this.blueTeam;
    }

    public List<Player> getLogoutPlayers() {
        return this.logoutPlayers;
    }

    public Map<Player, Boolean> getInvincible() {
        return this.invincible;
    }

    public void CopyMap() {
        for (Player player : this.joinPlayers) {
            this.invincible.put(player, false);
        }
    }

    public void TeamReset() {
        this.joinPlayers.clear();
        this.redTeam.clear();
        this.blueTeam.clear();
    }
}
