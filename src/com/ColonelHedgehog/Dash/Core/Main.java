/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ColonelHedgehog.Dash.Core;

import com.ColonelHedgehog.Dash.API.Powerup.Default.*;
import com.ColonelHedgehog.Dash.API.Powerup.ItemBox.ItemBox;
import com.ColonelHedgehog.Dash.API.Powerup.ItemBox.ItemBoxRegistry;
import com.ColonelHedgehog.Dash.API.Powerup.PowerupsRegistry;
import com.ColonelHedgehog.Dash.API.Track.Track;
import com.ColonelHedgehog.Dash.API.Track.TrackRegistry;
import com.ColonelHedgehog.Dash.Assets.Commands.EDCmd;
import com.ColonelHedgehog.Dash.Assets.Commands.VoteCmd;
import com.ColonelHedgehog.Dash.Assets.CooldownHandler;
import com.ColonelHedgehog.Dash.Assets.GameState;
import com.ColonelHedgehog.Dash.Assets.Powerups;
import com.ColonelHedgehog.Dash.Assets.VoteBoard;
import com.ColonelHedgehog.Dash.Events.*;
import com.ColonelHedgehog.Dash.Lib.Customization;
import com.ColonelHedgehog.Dash.Lib.Seeker;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.mcstats.Metrics;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * @name EquestrianDash
 * @version 0.9.0
 * @author Robert/TheHandfish
 * Created by Robert AKA TheHandfish. Do not redistribute this or download it from any other location than its project page/GitHub Repository on BukkitDev. 
 * Leave this section (from "@author" to ":)") intact, too, please. :)
 */
public class Main extends JavaPlugin implements Listener, CommandExecutor 
{
    public static boolean tm = false;
    public static Main plugin;
    public static Location Lap1;
    public static Location Lap2;
    public static Cuboid LapCuboid;
    public static String Prefix = "§9§l[§3Equestrian§bDash§9§l]§r: ";
    private static ItemBoxRegistry itemBoxRegistery;

    public static void setItemBoxRegistery(ItemBoxRegistry itemBoxRegistery)
    {
        Main.itemBoxRegistery = itemBoxRegistery;
    }

    @Override
    public void onEnable()
    {
        plugin = this;

        GameState.setState(GameState.State.WAITING_FOR_PLAYERS);
        registerEvents();
        setupMetrics();

        getCommand("ed").setExecutor(new EDCmd());
        getCommand("vote").setExecutor(new VoteCmd());
        if(getServer().getPluginManager().getPlugin("TitleManager") != null)
        {
            tm = true;
            getLogger().info("Optional dependency TitleManager was found.");
        }
        else
        {
            getLogger().info("Couldn't locate TitleManager. Lap titles will not be enabled.");
        }
        this.saveDefaultConfig();

        File configf = new File(this.getDataFolder() + "/config.yml");

        if(!configf.exists())
        {
            System.out.println("[EquestrianDash] Could not find a config, so generating one!");
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
        else if(plugin.getConfig().contains("Config") || plugin.getConfig().contains("Config.Powerups"))
        {
            // KILL IT WITH FIRE!
            try
            {
                FileUtils.copyFile(configf, new File(this.getDataFolder() + "/legacy_config.yml"));
                File configFile = new File(plugin.getDataFolder(), "config.yml");
                configFile.delete();
                saveDefaultConfig();
                reloadConfig();
                new BukkitRunnable()
                {

                    @Override
                    public void run()
                    {

                        System.out.println("[EquestrianDash] You were using an outdated config, so I KILLED IT WITH FIRE! (Don't worry, I saved a backup first: legacy_config.yml)");
                    }
                }.runTaskLater(plugin, 5);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }


        File tp = new File(getDataFolder() + "/Tracks");
        if(!tp.exists())
        {
            boolean maed = tp.mkdir();
            if(maed /* u maed */)
            {
                getLogger().info("Created Tracks folder.");
            }
            else /* or naw? */
            {
                getLogger().warning("Failed to create Tracks folder!");
            }
        }
    }

    private void diePotato() // In theory a potato *could* be lying around somewhere, right?
    {
        for(World w : Bukkit.getWorlds())
        {
            for(Entity e : w.getEntities())
            {
                if(e instanceof Item)
                {
                    e.remove();
                }
            }
        }
    }

    private void setupMetrics()
    {
        if(getConfig().getBoolean("MetricsEnabled"))
        {
            try
            {
                Metrics metrics = new Metrics(this);
                metrics.start();
                getLogger().info("Metrics connection established.");
            }
            catch (IOException e)
            {
                getLogger().severe("Failed to connect to mcstats.org!");
            }
        }
    }

    private void registerEvents()
    {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new CreatureSpawnListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new VehicleExistListener(), this);
        pm.registerEvents(new HorseJumpListener(), this);
        pm.registerEvents(new PlayerMoveListener(), this);
        pm.registerEvents(new EntityDamageByEntityListener(), this);
        pm.registerEvents(new EntityExplodeListener(), this);
        pm.registerEvents(new PlayerInteractEntityListener(), this);
        pm.registerEvents(new Powerups(), this);
        pm.registerEvents(new Seeker(), this);
        pm.registerEvents(new EntityDamageListener(), this);
        pm.registerEvents(new BlockPlaceListener(), this);
        pm.registerEvents(new PlayerRespawnListener(), this);
        pm.registerEvents(new EntityDeathListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new PlayerToggleSprintListener(), this);
        pm.registerEvents(new EntityShootBowListener(), this);
        pm.registerEvents(new Customization(), this);
        pm.registerEvents(new FoodLevelChangeListener(), this);
        pm.registerEvents(new EntityTargetLivingEntityListener(), this);
        pm.registerEvents(new PlayerDropItemListener(), this);
        pm.registerEvents(new WorldLoadListener(), this);
        pm.registerEvents(new PlayerCommandPreprocessListener(), this);
        // pm.registerEvents(new BlockPistonListener(), this);

        setupPowerups();
        setupTracks();
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable()
        {
            @Override
            public void run()
            {
                getLogger().info(powerupsRegistry.getPowerups().size() + " powerups were registered.");
            }
        }, 20L);


        if(pm.getPlugin("ProtocolLib") == null)
        {
            getLogger().info("No ProtocolLib dependency found. This plugin can run but players will need to respawn by manually clicking the button after death.");
        }
        else
        {
            getLogger().info("Found and hooked ProtocolLib. Players will respawn automatically after death.");
        }

        if (!plugin.getConfig().getBoolean("EditMode"))
        {
            VoteBoard.createBoard();
        }
    }

    private static ItemBoxRegistry itemBoxRegistry;
    public static ItemBoxRegistry getItemBoxRegistry()
    {
        return itemBoxRegistry;
    }
    public void setupItemBoxes()
    {
        itemBoxRegistry = new ItemBoxRegistry();
        if(!new File(getDataFolder() + "/ItemBoxes.yml").exists())
        {
            getLogger().info("Creating new ItemBox data...");
            itemBoxRegistry.initialize();
            getLogger().info("Done creating new ItemBox data!");
        }

        boolean editmode = getConfig().getBoolean("EditMode");
        for(Track t : trackRegistry.getTracks())
        {
            World w = t.getWorld();
            getLogger().info("Initializing Item Boxes for track: \"" + t.getDisplayName() + "\"");
            List<String> locsd = itemBoxRegistry.getBoxData().getStringList(w.getName());
            int i = 0;

            for (String str : locsd)
            {
                String[] split = str.split(",");
                Location loc = new Location(w, Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                ItemBox ib = new ItemBox(loc, null);

                if (editmode)
                {
                    ib.spawn(true);
                }


                itemBoxRegistry.register(ib, false);
                i++;
            }

            getLogger().info(i + " Item Box" + (i == 1 ? "" : "es") + " were loaded for \"" + t.getDisplayName() + "\".");
        }
    }

    private void setupTracks()
    {
        trackRegistry = new TrackRegistry();

        String[] files = new File(Main.plugin.getDataFolder() + "/Tracks/").list();

        int num = 0;
        if (files != null)
        {
            for(String file : files)
            {
                World w = Bukkit.getWorld(file);
                getLogger().info("Registering track for \"" + file + "\"");
                Main.getTrackRegistry().registerTrack(new Track(w));
                num++;
            }
        }

        getLogger().info("Registered " + num + " track(s).");
        setupItemBoxes();
    }

    private static TrackRegistry trackRegistry;

    public static Main getInstance()
    {
        return plugin;
    }

    public static TrackRegistry getTrackRegistry()
    {
        return trackRegistry;
    }

    private void setupPowerups()
    {
        powerupsRegistry = new PowerupsRegistry();
        cooldownHandler = new CooldownHandler();

        if(plugin.getConfig().getBoolean("Powerups.Launcher.Enabled"))
        {
            powerupsRegistry.registerPowerup(new LauncherPowerup());
        }
        if (plugin.getConfig().getBoolean("Powerups.Speed.Enabled"))
        {
            powerupsRegistry.registerPowerup(new SpeedPowerup());
        }
        if (plugin.getConfig().getBoolean("Powerups.TNT.Enabled"))
        {
            powerupsRegistry.registerPowerup(new TNTPowerup());
        }
        if (plugin.getConfig().getBoolean("Powerups.Slime.Enabled"))
        {
            powerupsRegistry.registerPowerup(new SlimePowerup());
        }
        if (plugin.getConfig().getBoolean("Powerups.Arrow.Enabled"))
        {
            powerupsRegistry.registerPowerup(new ArrowPowerup());
        }
        if (plugin.getConfig().getBoolean("Powerups.Ice.Enabled"))
        {
            powerupsRegistry.registerPowerup(new IcePowerup());
        }
        if (plugin.getConfig().getBoolean("Powerups.Wither.Enabled"))
        {
            powerupsRegistry.registerPowerup(new WitherPowerup());
        }
        if (plugin.getConfig().getBoolean("Powerups.Thief.Enabled"))
        {
            powerupsRegistry.registerPowerup(new ThiefPowerup());
        }
        if (plugin.getConfig().getBoolean("Powerups.Chicken.Enabled"))
        {
            powerupsRegistry.registerPowerup(new ChickenPowerup());
        }
    }

    @Override
    public void onDisable()
    {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(p.getVehicle() != null)
            {
                p.getVehicle().remove();
            }
            p.getInventory().clear();
        }

        GarbageControl.destroyGarbage();
    }
    
    public static void buildRaceline()
    {
        Lap1 = new Location(GameState.getCurrentTrack().getWorld(), GameState.getCurrentTrack().getTrackData().getDouble("Raceline.Lap1.X"), GameState.getCurrentTrack().getTrackData().getDouble("Raceline.Lap1.Y"), GameState.getCurrentTrack().getTrackData().getDouble("Raceline.Lap1.Z"));
        Lap2 = new Location(GameState.getCurrentTrack().getWorld(), GameState.getCurrentTrack().getTrackData().getDouble("Raceline.Lap2.X"), GameState.getCurrentTrack().getTrackData().getDouble("Raceline.Lap2.Y"), GameState.getCurrentTrack().getTrackData().getDouble("Raceline.Lap2.Z"));
        LapCuboid = new Cuboid(Lap1, Lap2);
    }

    private static PowerupsRegistry powerupsRegistry;

    public static PowerupsRegistry getPowerupsRegistry()
    {
        return powerupsRegistry;
    }


    private ProtocolManager protocolManager;

    @Override
    public void onLoad()
    {
        if(getServer().getPluginManager().getPlugin("ProtocolLib") != null)
        {
            protocolManager = ProtocolLibrary.getProtocolManager();
        }
    }

    private static CooldownHandler cooldownHandler;

    public static CooldownHandler getCooldownHandler()
    {
        return cooldownHandler;
    }

}