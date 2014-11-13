package tk.ColonelHedgehog.Dash.API.Powerup.Default;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import tk.ColonelHedgehog.Dash.API.Entity.Racer;
import tk.ColonelHedgehog.Dash.API.Powerup.Powerup;
import tk.ColonelHedgehog.Dash.Core.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ColonelHedgehog on 11/11/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class SlimePowerup implements Powerup
{

    @Override
    public ItemStack getItem()
    {
        ItemStack icon = new ItemStack(Material.getMaterial(Main.plugin.getConfig().getString("Config.Powerups.Slime.Material")));
        ItemMeta im = icon.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Config.Powerups.Slime.Title")));
        icon.setItemMeta(im);
        return icon;
    }

    @Override
    public void doOnRightClick(Racer racer, Action action)
    {
        Player p = racer.getPlayer();
        Location l1 = new Location(p.getWorld(), p.getLocation().getBlockX() + 1, p.getLocation().getBlockY(), p.getLocation().getBlockZ());
        Location l2 = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ() - 1);
        Slime s1 = (Slime) p.getWorld().spawnEntity(l1, EntityType.SLIME);
        Slime s2 = (Slime) p.getWorld().spawnEntity(l2, EntityType.SLIME);
        Slime s3 = (Slime) p.getWorld().spawnEntity(l1, EntityType.SLIME);
        Slime s4 = (Slime) p.getWorld().spawnEntity(l2, EntityType.SLIME);
        s1.setMetadata("Creator", new FixedMetadataValue(Main.plugin, p.getName()));
        s2.setMetadata("Creator", new FixedMetadataValue(Main.plugin, p.getName()));
        s3.setMetadata("Creator", new FixedMetadataValue(Main.plugin, p.getName()));
        s4.setMetadata("Creator", new FixedMetadataValue(Main.plugin, p.getName()));
        p.getInventory().clear();
    }

    @Override
    public void doOnLeftClick(Racer racer, Action action)
    {

    }

    @Override
    public void doOnDrop(Racer racer)
    {

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
    public void doOnPickup(Racer racer, Racer dropper)
    {

    }

    @Override
    public double getChance(int rank)
    {
        double chance = (8 - rank) / Main.plugin.getConfig().getDouble("Config.Powerups.Slime.Chance");
        return chance;
    }

    @Override
    public List<ActionType> cancelledEvents()
    {
        List<ActionType> list = new ArrayList<>();
        list.add(ActionType.ALL);
        return list;
    }
}
