package com.colonelhedgehog.equestriandash.api.powerup.common;

import com.colonelhedgehog.equestriandash.api.entity.Racer;
import com.colonelhedgehog.equestriandash.api.powerup.Powerup;
import com.colonelhedgehog.equestriandash.assets.handlers.RacerHandler;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ColonelHedgehog on 11/14/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class ThiefPowerup implements Powerup
{
    private EquestrianDash plugin = EquestrianDash.getInstance();
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
        racer.getPlayer().playSound(racer.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 3, 1);
        racer.getPlayer().getInventory().remove(getItem().getType());
        dropped.remove();
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
        RacerHandler racerHandler = plugin.getRacerHandler();
        int racing = racerHandler.getPlayers().size();

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
        Collections.addAll(list, ActionType.values());
        list.remove(ActionType.DROP);
        return list;
    }
}
