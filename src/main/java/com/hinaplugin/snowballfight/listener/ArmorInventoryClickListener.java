package com.hinaplugin.snowballfight.listener;

import com.hinaplugin.snowballfight.SnowBallFight;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

public class ArmorInventoryClickListener implements Listener {
    public ArmorInventoryClickListener() {
    }

    @EventHandler
    public void onClick(final InventoryClickEvent event) {
        final Player player = (Player)event.getWhoClicked();
        if (SnowBallFight.getPlugin().getWarehouse().getJoinPlayers().contains(player)) {
            if ((SnowBallFight.getPlugin().isStart() || SnowBallFight.getPlugin().isWait()) && event.getSlotType() == SlotType.ARMOR) {
                event.setCancelled(true);
                event.setResult(Result.DENY);
            }

        }
    }
}
