package com.colonelhedgehog.equestriandash.api.powerup.common;

import com.colonelhedgehog.equestriandash.api.entity.Racer;
import com.colonelhedgehog.equestriandash.api.powerup.Powerup;
import com.colonelhedgehog.equestriandash.api.track.Track;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ColonelHedgehog on 11/9/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class SpeedPowerup implements Powerup
{
    private EquestrianDash plugin = EquestrianDash.getInstance();
    @Override
    public ItemStack getItem()
    {
        ItemStack icon = new ItemStack(Material.getMaterial(EquestrianDash.plugin.getConfig().getString("Powerups.Speed.Material"))); // The powerup's icon.
        ItemMeta iconMeta = icon.getItemMeta(); // Getting its meta.
        iconMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', EquestrianDash.plugin.getConfig().getString("Powerups.Speed.Title"))); // Setting its display name to a predefined string in the config.
        icon.setItemMeta(iconMeta); // Now we set all the meta.
        return icon;
    }

    private String getMessage()
    {
        return EquestrianDash.Prefix + "§aYou used a " + this.getItem().getItemMeta().getDisplayName() + "§a!";
    }

    @Override
    public void doOnRightClick(Racer racer, Action action)
    {
        // This will be performed in the event that you right-click with the item.
        Player p = racer.getPlayer();
        p.sendMessage(getMessage());
        Horse h = racer.getHorse();

        Track track = plugin.getTrackRegistry().getTrackByWorld(racer.getPlayer().getWorld());

        if(track.getTrackData().getBoolean("NMS.Enabled"))
        {
            plugin.getPropertyHandler().setNMSHorseSpeed(h, track.getTrackData().getDouble("NMS.MaxHorseSpeed") * plugin.getConfig().getDouble("Powerups.Speed.Amplifier"));

            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    if(!p.isOnline() && h.isDead())
                    {
                        return;
                    }

                    plugin.getPropertyHandler().setNMSHorseSpeed(h, track.getTrackData().getDouble("NMS.MaxHorseSpeed"));
                }
            }.runTaskLater(plugin, plugin.getConfig().getInt("Powerups.Speed.Length"));
        }
        else
        {
            h.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 10));
        }

        racer.getPlayer().getInventory().clear();
    }

    // For reference's sake:

    @Override
    public void doOnLeftClick(Racer racer, Action action)
    {
        // This will be performed in the event that you left-click with the item.
    }

    @Override
    public void doOnDrop(Racer racer, Item dropped)
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
    public void doOnPickup(Racer racer, Racer dropper, Item item)
    {
        // This will be performed in the event that you pick up the item.
    }

    // For not reference's sake:

    /*@Override
    public String getMessage()
    {
        // This is executed when a message needs to be sent to the racer.
        return EquestrianDash.Prefix + "§aYou used a §fSpeed Powerup§a!"; // Consistancy is cool, yo.
    }*/

    @Override
    public double getChance(int rank)
    {
        return (rank / EquestrianDash.plugin.getConfig().getDouble("Powerups.Speed.Chance")); // Chance that when we hit an item-box, this will be an option.
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
