package tk.ColonelHedgehog.Dash.API.Powerup.Default;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.ColonelHedgehog.Dash.API.Entity.Racer;
import tk.ColonelHedgehog.Dash.API.Powerup.Powerup;
import tk.ColonelHedgehog.Dash.Core.Main;

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
        ItemStack icon = new ItemStack(Material.getMaterial(Main.plugin.getConfig().getString("Powerups.Thief.Material")));
        ItemMeta im = icon.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Powerups.Thief.Title")));
        icon.setItemMeta(im);
        return icon;
    }

    private String getMessage()
    {
        return Main.Prefix + "§aYou used a " + this.getItem().getItemMeta().getDisplayName() + "§a!";
    }

    @Override
    public void doOnRightClick(Racer racer, Action action)
    {

    }

    @Override
    public void doOnLeftClick(Racer racer, Action action)
    {
        racer.getPlayer().sendMessage(Main.Prefix + "§6Right-click another racer to use this item.");
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
            racer.getPlayer().sendMessage(Main.Prefix + "§aYou stole §e" + clicked.getPlayer().getName() + "§a's " + item.getItemMeta().getDisplayName() + "§a!");
            clicked.getPlayer().sendMessage(Main.Prefix + "§e" + racer.getPlayer().getName() + " §cstole your " + item.getItemMeta().getDisplayName() + "§c!");
            racer.getPlayer().setItemInHand(item);
            clicked.getPlayer().getInventory().clear();
        }
        else
        {
            racer.getPlayer().playSound(racer.getPlayer().getLocation(), Sound.ITEM_BREAK, 3, 1);
            racer.getPlayer().sendMessage(Main.Prefix + "§eThe racer you clicked had no powerup, so nothing was stolen! §cWell that was a waste.");
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
        if(8 - rank < 4.5 && 8 - rank >= 3)
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
