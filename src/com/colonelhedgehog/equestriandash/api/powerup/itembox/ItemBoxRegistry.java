package com.colonelhedgehog.equestriandash.api.powerup.itembox;

import com.colonelhedgehog.equestriandash.api.lang.TooFewPowerupsException;
import com.colonelhedgehog.equestriandash.api.powerup.Powerup;
import com.colonelhedgehog.equestriandash.api.powerup.PowerupsRegistry;
import com.colonelhedgehog.equestriandash.api.track.Track;
import com.colonelhedgehog.equestriandash.api.track.TrackRegistry;
import com.colonelhedgehog.equestriandash.assets.Ranking;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ColonelHedgehog on 12/26/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class ItemBoxRegistry
{
    private static EquestrianDash plugin = EquestrianDash.getInstance();
    private FileConfiguration boxData;
    private File boxDataFile;

    public ItemBoxRegistry()
    {
    }

    private String serialize(Location loc)
    {
        return loc.getBlockX() + "_" + loc.getBlockY() + "_" + loc.getBlockZ();
    }

    public void register(boolean save, ItemBox... ibs)
    {
        List<Track> tracks = new ArrayList<>();
        for (ItemBox ib : ibs)
        {
            if (ib == null)
            {
                continue;
            }

            if (!ib.getTrack().getItemBoxes().contains(ib))
            {
                ib.getTrack().getItemBoxes().add(ib);
            }

            if (!save)
            {
                continue;
            }

            ib.getTrack().getItemBoxData().set("ItemBoxes." + serialize(ib.getLocation()) + ".Visible", true);

            if (!tracks.contains(ib.getTrack()))
            {
                tracks.add(ib.getTrack());
            }
        }

        tracks.forEach(Track::saveItemBoxData);
    }

    public void deregister(boolean save, ItemBox... ibs)
    {
        List<Track> tracks = new ArrayList<>();
        for (ItemBox ib : ibs)
        {
            if (ib == null)
            {
                continue;
            }

            ib.getTrack().getItemBoxes().remove(ib);

            if (!save)
            {
                continue;
            }

            ib.getTrack().getItemBoxData().set("ItemBoxes." + serialize(ib.getLocation()), null);

            if (!tracks.contains(ib.getTrack()))
            {
                tracks.add(ib.getTrack());
            }
        }

        tracks.forEach(Track::saveItemBoxData);
    }

    public ItemBox getByLocation(Location loc)
    {
        TrackRegistry trackRegistry = plugin.getTrackRegistry();
        Track track = trackRegistry.getTrackByWorld(loc.getWorld());

        if (track == null)
        {
            return null;
        }

        for (ItemBox ib : track.getItemBoxes())
        {
            if (ib.getLocation().getBlock().equals(loc.getBlock()))
            {
                return ib;
            }
        }

        return null;
    }

    public void giveReward(final Player p, final ItemBox ib, boolean rop)
    {
        if (p.getInventory().getItem(0) != null)
        {
            return;
        }

        //e.getServer().b
        // roadcastMessage("Was an ender crystal!");
        PowerupsRegistry powerupsRegistry = plugin.getPowerupsRegistry();
        ItemStack si = p.getInventory().getItem(0);

        if (si == null || si.getType() == Material.AIR || rop)
        {
            Location il = ib.getEnderCrystal().getLocation();
            Firework fw = il.getWorld().spawn(il, Firework.class);
            FireworkMeta data = fw.getFireworkMeta();
            data.addEffects(FireworkEffect.builder().withColor(Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PURPLE).with(FireworkEffect.Type.STAR).build());
            data.setPower(0);
            fw.setFireworkMeta(data);

            p.getInventory().clear();
            List<Powerup> pl = new ArrayList<>();
            for (Powerup pow : powerupsRegistry.getPowerups())
            {
                if (pow.getChance(plugin.getRacerHandler().getRacer(p).getRank()) >= 1)
                {
                    for (int i = 0; i < pow.getChance(Ranking.getRank(p)); i++)
                    {
                        pl.add(pow);
                    }
                }
            }

            if (pl.isEmpty())
            {
                try
                {
                    throw new TooFewPowerupsException();
                }
                catch (TooFewPowerupsException e)
                {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i <= 8; i++)
            {
                Powerup powerup = pl.get(new Random().nextInt(pl.size()));
                p.getInventory().setItem(i, powerup.getItem());
            }

            spinInv(p);
        }
    }

    private void spinInv(final Player p)
    {
        Sound sound = Sound.valueOf(plugin.getConfig().getString("InvSpinSound"));

        if (sound == null)
        {
            plugin.getLogger().warning("Couldn't find a sound named \"" + plugin.getConfig().getString("InvSpinSound") + "\" for spinning inventory sounds!");
        }

        p.setMetadata("invSpinning", new FixedMetadataValue(plugin, true));

        final int[] count = {0};
        final int[] random = {27 + new Random().nextInt(11)};
        final float[] pitch = {0};
        final int[] slot = {0};
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if (count[0] < random[0] && !p.isDead() && p.isOnline())
                {
                    p.playSound(p.getLocation(), sound, 3, pitch[0]);
                    p.getInventory().setHeldItemSlot(slot[0]);
                }
                else if (!p.isDead() || p.isOnline())
                {
                    cancel();
                    final ItemStack chosen = p.getInventory().getItem(slot[0]);

                    p.getInventory().clear();

                    p.getInventory().setItem(0, chosen);
                    p.getInventory().setHeldItemSlot(0);

                    p.playSound(p.getLocation(), sound, 3, 2);
                    p.playSound(p.getLocation(), sound, 3, 1.5F);
                    p.playSound(p.getLocation(), sound, 3, 1);
                    p.setMetadata("invSpinning", new FixedMetadataValue(plugin, false));
                }
                else
                {
                    cancel();
                }
                count[0]++;
                pitch[0] = pitch[0] < 1.9 ? pitch[0] + 0.333333334F : 0;
                slot[0] = slot[0] < 8 ? slot[0] + 1 : 0;

            }
        }.runTaskTimer(plugin, 0, 2);
    }

    private void setName(ItemStack is, String name)
    {
        ItemMeta m = is.getItemMeta();
        m.setDisplayName(name);
        is.setItemMeta(m);
    }
}
