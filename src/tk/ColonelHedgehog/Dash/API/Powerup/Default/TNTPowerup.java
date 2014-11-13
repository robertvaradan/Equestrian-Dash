package tk.ColonelHedgehog.Dash.API.Powerup.Default;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.ColonelHedgehog.Dash.API.Entity.Racer;
import tk.ColonelHedgehog.Dash.API.Powerup.Powerup;
import tk.ColonelHedgehog.Dash.Core.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ColonelHedgehog on 11/9/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class TNTPowerup implements Powerup
{
    @Override
    public ItemStack getItem()
    {
        ItemStack icon = new ItemStack(Material.getMaterial(Main.plugin.getConfig().getString("Config.Powerups.TNT.Material"))); // The powerup's icon.
        ItemMeta iconMeta = icon.getItemMeta(); // Getting its meta.
        iconMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Config.Powerups.TNT.Title"))); // Setting its display name to a predefined string in the config.
        icon.setItemMeta(iconMeta); // Now we set all the meta.
        return icon;
    }

    @Override
    public void doOnRightClick(Racer racer, Action action)
    {
        // This will be performed in the event that you right-click with the item.
        if (Main.plugin.getConfig().getBoolean("Config.Powerups.TNT.ThrowDirections.ThrowBehindEnabled"))
        {
            Player p = racer.getPlayer();
            p.playSound(p.getLocation(), Sound.FUSE, 7, 1);
            TNTPrimed tnt = p.getWorld().spawn(p.getLocation(), TNTPrimed.class);
            tnt.setFuseTicks(Main.plugin.getConfig().getInt("Config.Powerups.TNT.ThrowDirections.ThrowBehindTicks"));

            racer.getPlayer().getInventory().clear();
        }
    }

    @Override
    public void doOnLeftClick(Racer racer, Action action)
    {
        // This will be performed in the event that you left-click with the item.
        if(Main.plugin.getConfig().getBoolean("Config.Powerups.TNT.ThrowDirections.ThrowAheadEnabled"))
        {
            Player p = racer.getPlayer();

            p.playSound(p.getLocation(), Sound.FUSE, 7, 1);
            TNTPrimed tnt = p.getWorld().spawn(p.getLocation(), TNTPrimed.class);
            tnt.setVelocity(p.getLocation().getDirection().normalize().multiply(Main.plugin.getConfig().getDouble("Config.Powerups.TNT.ThrowDirections.ThrowAheadMultiplier")));
            tnt.setFuseTicks(Main.plugin.getConfig().getInt("Config.Powerups.TNT.ThrowDirections.ThrowAheadTicks"));

            racer.getPlayer().getInventory().clear();
        }
    }

    @Override
    public void doOnDrop(Racer racer, Item dropped)
    {

    }

    @Override
    public void doOnRightClickRacer(Racer racer, Racer clicked)
    {
        // This will be performed in the event that you right-click someone with the item.
    }

    @Override
    public void doOnLeftClickRacer(Racer racer, Racer clicked)
    {
        // This will be performed in the event that you left-click someone with the item.
    }

    @Override
    public void doOnPickup(Racer racer, Racer dropper, Item item)
    {
        // This will be performed in the event that you pick up the item.
    }

    // For not reference's sake:

    /*@Override
    public String getMessage()
    {
        // This is executed when a message needs to be sent to the racer.
        return Main.Prefix + "§aYou used a §cTNT Powerup§a!"; // Consistancy is cool, yo.
    }*/

    @Override
    public double getChance(int rank)
    {
        return (rank / Main.plugin.getConfig().getDouble("Config.Powerups.TNT.Chance")); // Chance that when we hit an item-box, this will be an option.
    }

    @Override
    public List<ActionType> cancelledEvents()
    {
        List<ActionType> actions = new ArrayList<>();
        actions.add(ActionType.ALL);
        // ALL types of actions will be cancelled.
        return actions;
    }
}
