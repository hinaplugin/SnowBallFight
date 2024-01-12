package com.hinaplugin.snowballfight.system;

import com.hinaplugin.snowballfight.SnowBallFight;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CreateBall {
    public CreateBall() {
    }

    public ItemStack Create(final int amount) {
        final ItemStack SnowBall = new ItemStack(Material.SNOWBALL, amount);
        final ItemMeta SnowBallMeta = SnowBall.getItemMeta();
        final PersistentDataContainer container = SnowBallMeta.getPersistentDataContainer();
        container.set(SnowBallFight.getPlugin().getKey(), PersistentDataType.STRING, "sb");
        SnowBallMeta.addEnchant(Enchantment.LUCK, 1, false);
        SnowBallMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        SnowBallMeta.displayName(Component.text().content("雪合戦専用雪玉").build());
        SnowBall.setItemMeta(SnowBallMeta);
        return SnowBall;
    }
}
