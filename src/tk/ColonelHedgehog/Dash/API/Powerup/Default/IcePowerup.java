package tk.ColonelHedgehog.Dash.API.Powerup.Default;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import tk.ColonelHedgehog.Dash.API.Entity.Racer;
import tk.ColonelHedgehog.Dash.API.Powerup.Powerup;
import tk.ColonelHedgehog.Dash.Core.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ColonelHedgehog on 11/13/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class IcePowerup implements Powerup
{

    @Override
    public ItemStack getItem()
    {
        ItemStack icon = new ItemStack(Material.getMaterial(Main.plugin.getConfig().getString("Config.Powerups.Ice.Material")));
        ItemMeta iconMeta = icon.getItemMeta();
        iconMeta.setDisplayName(Main.plugin.getConfig().getString("Config.Powerups.Ice.Title"));
        icon.setItemMeta(iconMeta);
        return icon;
    }

    @Override
    public void doOnRightClick(Racer racer, Action action)
    {

    }

    @Override
    public void doOnLeftClick(Racer racer, Action action)
    {

    }

    @Override
    public void doOnDrop(Racer racer, final Item dropped)
    {
        new BukkitRunnable()
        {

            @Override
            public void run()
            {
                if(!dropped.isDead() && dropped.isOnGround())
                {
                    // Ice thingy
                }
                else
                {
                    dropped.remove();
                }
            }
        }.runTaskLater(Main.plugin, Main.plugin.getConfig().getLong("Config.Powerups.Ice.DelayInTicks"));
    }

    @Override
    public void doOnRightClickRacer(Racer racer, Racer clicked)
    {

    }

    @Override
    public void doOnLeftClickRacer(Racer racer, Racer clicked)
    {

    }

    @Override
    public void doOnPickup(Racer racer, Racer dropper, Item item)
    {
        dropper.getPlayer().sendMessage(Main.Prefix + "§e" + racer.getPlayer().getName() + " §6recovered your " + Main.plugin.getConfig().getString("Config.Powerups.Ice.Title") + "§6!");
    }

    @Override
    public double getChance(int rank)
    {
        return 0;
    }

    @Override
    public List<ActionType> cancelledEvents()
    {
        List<ActionType> list = new ArrayList<>();
        list.add(ActionType.LEFT_CLICK);
        list.add(ActionType.LEFT_CLICK_ENTITY);
        list.add(ActionType.RIGHT_CLICK);
        list.add(ActionType.RIGHT_CLICK_ENTITY);
        return list;
    }
}
