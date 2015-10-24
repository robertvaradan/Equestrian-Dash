package com.colonelhedgehog.equestriandash.api.powerup.common;

import com.colonelhedgehog.equestriandash.api.entity.Racer;
import com.colonelhedgehog.equestriandash.api.powerup.Powerup;
import com.colonelhedgehog.equestriandash.api.track.Track;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
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
 * Created by ColonelHedgehog on 10/11/15.
 */
public class FireChargePowerup implements Powerup
{

    private EquestrianDash plugin = EquestrianDash.getInstance();

    @Override
    public ItemStack getItem()
    {
        ItemStack stack = new ItemStack(Material.valueOf(EquestrianDash.plugin.getConfig().getString("Powerups.FireCharge.Material")));
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("§c§lFire Charge");
        stack.setItemMeta(meta);

        return stack;
    }

    private String getMessage()
    {
        return EquestrianDash.Prefix + "§aYou used a " + this.getItem().getItemMeta().getDisplayName() + "§a!";
    }

    @Override
    public void doOnRightClick(Racer racer, Action action)
    {
        Track track = plugin.getTrackRegistry().getTrackByWorld(racer.getPlayer().getWorld());
        double speed = track.getTrackData().getDouble("NMS.MaxHorseSpeed");
        Player p = racer.getPlayer();
        p.sendMessage(getMessage());
        p.getWorld().playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST, 3, 1);

        final int max = plugin.getConfig().getInt("Powerups.FireCharge.FireTrail.MaxPlaced");
        final int ticks = plugin.getConfig().getInt("Powerups.FireCharge.FireTrail.FirePerTick");
        //p.addPotionEffect(new PotionEffect())
        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, ticks * max, 1));
        racer.getHorse().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, ticks * max, 1));
        plugin.getPropertyHandler().setNMSHorseSpeed(racer.getHorse(), speed * plugin.getConfig().getDouble("Powerups.FireCharge.FireTrail.Speed"));
        List<Location> locs = new ArrayList<>();
        new BukkitRunnable()
        {
            private int count = 0;
            @Override
            public void run()
            {
                if(count == max || !p.isOnline() || racer.getHorse() == null || racer.getHorse().isDead())
                {
                    if(p.isOnline())
                    {
                        p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                        p.setFireTicks(0);
                        if(racer.getHorse() != null)
                        {
                            racer.getHorse().setFireTicks(0);
                            plugin.getPropertyHandler().setNMSHorseSpeed(racer.getHorse(), speed);
                        }
                    }
                    cancel();
                    return;
                }

                Block b = racer.getHorse().getLocation().getBlock();
                if(b.getType() == Material.AIR)
                {
                    b.setType(Material.FIRE);
                    b.getWorld().playSound(b.getLocation(), Sound.FIRE_IGNITE, 3, 1);
                    locs.add(b.getLocation());
                }
                count++;
            }
        }.runTaskTimer(plugin, 0, ticks);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                for(Location loc : locs)
                {
                    Block b = loc.getBlock();

                    if(b.getType() != Material.FIRE)
                    {
                        continue;
                    }

                    b.setType(Material.AIR);
                }
            }
        }.runTaskLater(plugin, max * ticks + plugin.getConfig().getInt("Powerups.FireCharge.FireTrail.DespawnAfter"));

        p.getInventory().clear();
    }

    @Override
    public void doOnLeftClick(Racer racer, Action action)
    {

    }

    @Override
    public void doOnDrop(Racer racer, Item dropped)
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
    public void doOnPickup(Racer racer, Racer dropper, Item item)
    {

    }

    @Override
    public double getChance(int rank)
    {
        return (rank / EquestrianDash.plugin.getConfig().getDouble("Powerups.FireCharge.Chance")); // Chance that when we hit an item-box, this will be an option.
    }

    @Override
    public List<ActionType> cancelledEvents()
    {
        List<ActionType> actions = new ArrayList<>();
        actions.add(ActionType.ALL);
        return actions;
    }
}
