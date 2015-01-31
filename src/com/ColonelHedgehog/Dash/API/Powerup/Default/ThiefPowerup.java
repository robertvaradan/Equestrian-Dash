package com.ColonelHedgehog.Dash.API.Powerup.Default;

import com.ColonelHedgehog.Dash.API.Entity.Racer;
import com.ColonelHedgehog.Dash.API.Powerup.Powerup;
import com.ColonelHedgehog.Dash.Core.EquestrianDash;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ColonelHedgehog on 11/14/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class ThiefPowerup implements Powerup
{

    @Override
    public ItemStack getItem()
    {
        ItemStack icon = new ItemStack(Material.getMaterial(EquestrianDash.plugin.getConfig().getString("Powerups.Thief.Material")));
        ItemMeta im = icon.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', EquestrianDash.plugin.getConfig().getString("Powerups.Thief.Title")));
        icon.setItemMeta(im);
        return icon;
    }

    private String getMessage()
    {
        return EquestrianDash.Prefix + "§aYou used a " + this.getItem().getItemMeta().getDisplayName() + "§a!";
    }

    @Override
    public void doOnRightClick(Racer racer, Action action)
    {

    }

    @Override
    public void doOnLeftClick(Racer racer, Action action)
    {
        racer.getPlayer().sendMessage(EquestrianDash.Prefix + "§6Right-click another racer to use this item.");
    }

    @Override
    public void doOnDrop(Racer racer, Item dropped)
    {

    }

    @Override
    public void doOnRightClickRacer(Racer racer, Racer clicked)
    {
        racer.getPlayer().sendMessage(getMessage());
        ItemStack item = clicked.getPlayer().getInventory().getItem(0);
        if(item != null)
        {
            racer.getPlayer().sendMessage(EquestrianDash.Prefix + "§aYou stole §e" + clicked.getPlayer().getName() + "§a's " + item.getItemMeta().getDisplayName() + "§a!");
            clicked.getPlayer().sendMessage(EquestrianDash.Prefix + "§e" + racer.getPlayer().getName() + " §cstole your " + item.getItemMeta().getDisplayName() + "§c!");
            racer.getPlayer().setItemInHand(item);
            clicked.getPlayer().getInventory().clear();
            racer.getPlayer().playSound(racer.getPlayer().getLocation(), Sound.CAT_MEOW, 3, 0);
            clicked.getPlayer().playSound(clicked.getPlayer().getLocation(), Sound.CAT_MEOW, 3, 0);
        }
        else
        {
            racer.getPlayer().playSound(racer.getPlayer().getLocation(), Sound.ITEM_BREAK, 3, 1);
            racer.getPlayer().sendMessage(EquestrianDash.Prefix + "§eThe racer you clicked had no powerup, so nothing was stolen! §cWell that was a waste.");
            racer.getPlayer().getInventory().clear();
        }
    }

    @Override
    public void doOnLeftClickRacer(Racer racer, Racer clicked)
    {

    }

    @Override
    public void doOnPickup(Racer racer, Racer dropper, Item item)
    {

    }

    @Override
    public double getChance(int rank)
    {
        int racing = Bukkit.getOnlinePlayers().size();

        if(racing - rank < (racing / 2) + 0.5 && racing - rank >= racing / 2 - 1)
        {
            return 2;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public List<ActionType> cancelledEvents()
    {
        List<ActionType> list = new ArrayList<>();
        list.add(ActionType.ALL);
        return list;
    }
}
