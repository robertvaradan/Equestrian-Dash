package tk.ColonelHedgehog.Dash.API.Powerup.Stock;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import tk.ColonelHedgehog.Dash.API.Entity.Racer;
import tk.ColonelHedgehog.Dash.API.Powerup.Powerup;
import tk.ColonelHedgehog.Dash.Core.Main;

/**
 * Created by ColonelHedgehog on 11/9/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class LaunchPowerup implements Powerup
{
    @Override
    public int getItemAmountReduction()
    {
        return 1;
    }

    @Override
    public ItemStack getItem()
    {
        ItemStack icon = new ItemStack(Material.DIAMOND); // A diamond will be this powerup's "icon".
        ItemMeta iconMeta = icon.getItemMeta(); // Getting its meta.
        iconMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Config.Powerups.Launcher.Title"))); // Setting its display name to a predefined string in the config.
        icon.setItemMeta(iconMeta); // Now we set all the meta.
        return icon;
    }

    @Override
    public void doOnRightClick(Racer racer)
    {
        // This will be performed in the event that you right-click with the item.
        Horse h = racer.getHorse();
        h.setVelocity(h.getLocation().getDirection().multiply(new Vector(2, 1, 2)).add(new Vector(0, 1.5, 0)));
        racer.setVelocity(racer.getLocation().getDirection().multiply(new Vector(2, 1, 2)).add(new Vector(0, 1.5, 0)));
        racer.playSound(racer.getLocation(), Sound.FIREWORK_LARGE_BLAST2, 7, 1);
    }

    @Override
    public void doOnLeftClick(Racer racer)
    {
        // This will be performed in the event that you left-click with the item.
    }

    @Override
    public void doOnDrop(Racer racer)
    {
        // This will be performed in the event that you drop the item.
    }

    @Override
    public String getMessage()
    {
        // This is executed when
        return Main.Prefix + "§aYou used a §bLaunch Powerup§a!"; // Consistancy is cool, yo.
    }
}
