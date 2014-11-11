package tk.ColonelHedgehog.Dash.API.Powerup.Default;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Horse;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
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
public class LauncherPowerup implements Powerup
{
    @Override
    public ItemStack getItem()
    {
        ItemStack icon = new ItemStack(Material.getMaterial(Main.plugin.getConfig().getString("Config.Powerups.Launcher.Material"))); // The powerup's "icon".
        ItemMeta iconMeta = icon.getItemMeta(); // Getting its meta.
        iconMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Config.Powerups.Launcher.Title"))); // Setting its display name to a predefined string in the config.
        icon.setItemMeta(iconMeta); // Now we set all the meta.
        return icon;
    }

    @Override
    public void doOnRightClick(Racer racer, Action action)
    {
        // This will be performed in the event that you right-click with the item.
        Horse h = racer.getHorse();
        h.setVelocity(h.getLocation().getDirection().multiply(new Vector(2, 1, 2)).add(new Vector(0, 1.5, 0)));
        racer.getPlayer().setVelocity(racer.getPlayer().getLocation().getDirection().multiply(new Vector(2, 1, 2)).add(new Vector(0, 1.5, 0)));
        racer.getPlayer().playSound(racer.getPlayer().getLocation(), Sound.FIREWORK_LARGE_BLAST2, 7, 1);

        racer.getPlayer().getInventory().clear();
    }

    // For reference's sake:

    @Override
    public void doOnLeftClick(Racer racer, Action action)
    {
        // This will be performed in the event that you left-click with the item.
    }

    @Override
    public void doOnDrop(Racer racer)
    {
        // This will be performed in the event that you drop the item.
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
    public void doOnPickup(Racer racer, Racer dropper)
    {
        // This will be performed in the event that you pick up the item.
    }

    // For not reference's sake:

    /*@Override
    public String getMessage()
    {
        // This is executed when a message needs to be sent to the racer.
        return Main.Prefix + "§aYou used a §bLaunch Powerup§a!"; // Consistancy is cool, yo.
    }*/

    @Override
    public int getChance()
    {
        return Main.plugin.getConfig().getInt("Config.Powerups.Launcher.Chance"); // Chance that when we hit an item-box, this will be an option.
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
