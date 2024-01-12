package com.hinaplugin.snowballfight.system;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;

public class CreateUniform {
    public CreateUniform() {
    }

    public void RedUniform(@NotNull final Player player) {
        final ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
        final ItemMeta helmetMeta = helmet.getItemMeta();
        final LeatherArmorMeta helmetArmorMeta = (LeatherArmorMeta)helmetMeta;
        helmetArmorMeta.setColor(Color.RED);
        helmet.setItemMeta(helmetArmorMeta);
        player.getInventory().setHelmet(helmet);
        final ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        final ItemMeta chestplateMeta = chestplate.getItemMeta();
        final LeatherArmorMeta chestplateArmorMeta = (LeatherArmorMeta)chestplateMeta;
        chestplateArmorMeta.setColor(Color.RED);
        chestplate.setItemMeta(chestplateArmorMeta);
        player.getInventory().setChestplate(chestplate);
        final ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        final ItemMeta leggingsMeta = leggings.getItemMeta();
        final LeatherArmorMeta leggingsArmorMeta = (LeatherArmorMeta)leggingsMeta;
        leggingsArmorMeta.setColor(Color.RED);
        leggings.setItemMeta(leggingsArmorMeta);
        player.getInventory().setLeggings(leggings);
        final ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
        final ItemMeta bootsMeta = boots.getItemMeta();
        final LeatherArmorMeta bootsArmorMeta = (LeatherArmorMeta)bootsMeta;
        bootsArmorMeta.setColor(Color.RED);
        boots.setItemMeta(bootsArmorMeta);
        player.getInventory().setBoots(boots);
    }

    public void BlueUniform(@NotNull final Player player) {
        final ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
        final ItemMeta helmetMeta = helmet.getItemMeta();
        final LeatherArmorMeta helmetArmorMeta = (LeatherArmorMeta)helmetMeta;
        helmetArmorMeta.setColor(Color.BLUE);
        helmet.setItemMeta(helmetArmorMeta);
        player.getInventory().setHelmet(helmet);
        final ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        final ItemMeta chestplateMeta = chestplate.getItemMeta();
        final LeatherArmorMeta chestplateArmorMeta = (LeatherArmorMeta)chestplateMeta;
        chestplateArmorMeta.setColor(Color.BLUE);
        chestplate.setItemMeta(chestplateArmorMeta);
        player.getInventory().setChestplate(chestplate);
        final ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        final ItemMeta leggingsMeta = leggings.getItemMeta();
        final LeatherArmorMeta leggingsArmorMeta = (LeatherArmorMeta)leggingsMeta;
        leggingsArmorMeta.setColor(Color.BLUE);
        leggings.setItemMeta(leggingsArmorMeta);
        player.getInventory().setLeggings(leggings);
        final ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
        final ItemMeta bootsMeta = boots.getItemMeta();
        final LeatherArmorMeta bootsArmorMeta = (LeatherArmorMeta)bootsMeta;
        bootsArmorMeta.setColor(Color.BLUE);
        boots.setItemMeta(bootsArmorMeta);
        player.getInventory().setBoots(boots);
    }
}
