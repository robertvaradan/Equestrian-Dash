/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.colonelhedgehog.equestriandash.assets.tasks;

import com.colonelhedgehog.equestriandash.api.powerup.itembox.ItemBox;
import com.colonelhedgehog.equestriandash.assets.handlers.GameHandler;
import com.colonelhedgehog.equestriandash.assets.handlers.RacerHandler;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

import static com.colonelhedgehog.equestriandash.core.EquestrianDash.LapCuboid;

/**
 * @author ColonelHedgehog
 */
public class LocationTask implements Listener
{
    public static EquestrianDash plugin = EquestrianDash.plugin;

    public BukkitTask generateTask(int ticks)
    {
        HashMap<UUID, Double> hashmap = new HashMap<>();
        RacerHandler racerHandler = plugin.getRacerHandler();
        GameHandler gameHandler = plugin.getGameHandler();

        return new BukkitRunnable()
        {
            @Override
            public void run()
            {

                for (Player p : racerHandler.getPlayers())
                {
                    if (gameHandler.getGameState() == GameHandler.GameState.COUNT_DOWN_TO_START || gameHandler.getFinished().contains(racerHandler.getRacer(p)))
                    {
                        continue;
                    }

                    double newZ = p.getLocation().getZ();
                    double oldZ = !hashmap.containsKey(p.getUniqueId()) ? newZ : hashmap.get(p.getUniqueId());

                    hashmap.put(p.getUniqueId(), newZ);

                    if (isInRaceline(p))
                    {
                        if (p.getMetadata("playerInLine").get(0).asBoolean())
                        {
                            return;
                        }

                        if (oldZ < newZ && !gameHandler.getFinished().contains(racerHandler.getRacer(p)) && plugin.getGameHandler().getGameState() == GameHandler.GameState.RACE_IN_PROGRESS)
                        {
                            gameHandler.doLap(p);
                        }
                        else
                        {
                            Entity v = p.getVehicle();
                            v.setVelocity(new Vector(0, 0.5, 1.5));
                            p.setVelocity(new Vector(0, 0.5, 1.5));
                            p.sendMessage(EquestrianDash.Prefix + "§4§oWrong way, cheater! Turn around.");
                        }
                    }
                    else
                    {
                        p.setMetadata("playerInLine", new FixedMetadataValue(plugin, false));
                    }

                    if (p.getVehicle() != null && p.getVehicle() instanceof Horse)
                    {
                        updateRespawn(p);
                    }

                    if (p.getGameMode() == GameMode.CREATIVE)
                    {
                        return;
                    }

                    for (Entity e : p.getNearbyEntities(0.25, 0.25, 0.25))
                    {
                        if (!(e instanceof EnderCrystal))
                        {
                            continue;
                        }

                        ItemBox ib = plugin.getItemBoxRegistry().getByLocation(e.getLocation().getBlock().getLocation());

                        if (ib == null)
                        {
                            return;
                        }

                        if (ib.getEnderCrystal() == null)
                        {
                            return;
                        }

                        if (plugin.getCooldownHandler().isCooling(p))
                        {
                            return;
                        }

                        ib.getLocation().getWorld().playEffect(ib.getLocation(), Effect.STEP_SOUND, 20);
                        ib.getLocation().getWorld().playSound(ib.getLocation(), Sound.GLASS, 3, 1);
                        ib.despawn();

                        if (!e.isDead())
                        {
                            e.remove();
                        }

                        ib.respawn();
                        plugin.getCooldownHandler().placeInCooldown(p, plugin.getConfig().getLong("PowerupSettings.PickupCooldown"));
                        plugin.getItemBoxRegistry().giveReward(p, ib, plugin.getConfig().getBoolean("PowerupSettings.ReplaceOld"));
                    }

                    if (p.getRemainingAir() < p.getMaximumAir())
                    {
                        plugin.getRacerHandler().respawnPlayer(p);
                    }
                }
            }
        }.runTaskTimer(plugin, 0, ticks);
    }

    public static void updateRespawn(Player p)
    {
        Entity e = p.getVehicle();

        if(e == null)
        {
            return;
        }

        Block block = e.getLocation().getBlock().getRelative(BlockFace.DOWN);

        for (int x = -3; x < 3; x++)
        {
            for (int z = -3; z < 3; z++)
            {
                Block scan = block.getRelative(x, 0, z);
                if ((scan.isLiquid() && block.getType() != Material.LAVA && block.getType() != Material.STATIONARY_LAVA) || block.getType() == Material.AIR)
                {
                    return;
                }
            }
        }

        if (block.isLiquid())
        {
            plugin.getRacerHandler().respawnPlayer(p);
            return;
        }

        plugin.getRacerHandler().lastLocation.put(p.getUniqueId(), p.getLocation());
    }

    public static boolean isInRaceline(Player p)
    {
        return LapCuboid != null && LapCuboid.contains(p.getLocation()) && p.getVehicle() != null;
    }



    @SuppressWarnings("deprecation")
    public static void firstPlace(Player p)
    {
        fireWorkLoops(p);
        //delayStopPlayer(p);
        p.playEffect(p.getLocation(), Effect.RECORD_PLAY, 2257);
    }

    public static void fireWorkLoops(final Player p)
    {
        new BukkitRunnable()
        {

            @Override
            public void run()
            {
                //Start game method
                int num1 = getRandom(1, 6);
                int num2 = getRandom(-3, 3);
                int num3 = getRandom(-5, 5);

                Location fwloc = new Location(p.getWorld(), p.getLocation().getX() + num3 / num1 + num2, p.getLocation().getY(), p.getLocation().getZ() + num2 + num3 + num1);


                Firework fw = p.getWorld().spawn(fwloc, Firework.class);
                FireworkMeta data = fw.getFireworkMeta();
                data.addEffects(FireworkEffect.builder().withColor(getRandomColor(num1)).with(FireworkEffect.Type.STAR).build());
                data.setPower(0);
                fw.setFireworkMeta(data);
                if (!p.isOnline())
                {
                    cancel(); //Cancels the timer
                }
            }

        }.runTaskTimer(plugin, 0L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);
    }

    public static int getRandom(int from, int to)
    {
        return (int) ((Math.random() * to) + from);
    }

    public static Color getRandomColor(int rand)
    {
        switch (rand)
        {
            case 1:
                return Color.RED;
            case 2:
                return Color.ORANGE;
            case 3:
                return Color.YELLOW;
            case 4:
                return Color.LIME;
            case 5:
                return Color.BLUE;
            case 6:
                return Color.PURPLE;
            default:
                return Color.RED;
        }
    }



    /*int place = 8;
    int lap = p.getMetadata("raceLap").get(0).asInt();
    for(int i = 0; i <= racerHandler.getPlayers().size(); i++)
    {
        if(evalPlace(p, lap) > evalPlace(racers.get(i - 1), lap))
        {
            place--;
            RPlace[place] = p;
        }
        else
        {
            place--;
        }
    }*/

}
