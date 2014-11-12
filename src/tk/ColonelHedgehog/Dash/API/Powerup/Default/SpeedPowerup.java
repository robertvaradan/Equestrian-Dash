package tk.ColonelHedgehog.Dash.API.Powerup.Default;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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
public class SpeedPowerup implements Powerup
{
    @Override
    public ItemStack getItem()
    {
        ItemStack icon = new ItemStack(Material.getMaterial(Main.plugin.getConfig().getString("Config.Powerups.Speed.Material"))); // The powerup's icon.
        ItemMeta iconMeta = icon.getItemMeta(); // Getting its meta.
        iconMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Config.Powerups.Speed.Title"))); // Setting its display name to a predefined string in the config.
        icon.setItemMeta(iconMeta); // Now we set all the meta.
        return icon;
    }

    @Override
    public void doOnRightClick(Racer racer, Action action)
    {
        // This will be performed in the event that you right-click with the item.
        Horse h = racer.getHorse();
        h.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 10));

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
        return Main.Prefix + "§aYou used a §fSpeed Powerup§a!"; // Consistancy is cool, yo.
    }*/

    @Override
    public double getChance(int rank)
    {
        return (rank / Main.plugin.getConfig().getDouble("Config.Powerups.Speed.Chance")); // Chance that when we hit an item-box, this will be an option.
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
