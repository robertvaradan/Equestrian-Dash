/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.colonelhedgehog.equestriandash.core;

import com.colonelhedgehog.equestriandash.api.powerup.Powerup;
import com.colonelhedgehog.equestriandash.api.powerup.PowerupsRegistry;
import com.colonelhedgehog.equestriandash.api.powerup.itembox.ItemBox;
import com.colonelhedgehog.equestriandash.api.powerup.itembox.ItemBoxRegistry;
import com.colonelhedgehog.equestriandash.api.track.Track;
import com.colonelhedgehog.equestriandash.api.track.TrackRegistry;
import com.colonelhedgehog.equestriandash.assets.Powerups;
import com.colonelhedgehog.equestriandash.assets.VoteBoard;
import com.colonelhedgehog.equestriandash.assets.commands.EDCmd;
import com.colonelhedgehog.equestriandash.assets.commands.VoteCmd;
import com.colonelhedgehog.equestriandash.assets.handlers.CooldownHandler;
import com.colonelhedgehog.equestriandash.assets.handlers.GameHandler;
import com.colonelhedgehog.equestriandash.assets.handlers.PropertyHandler;
import com.colonelhedgehog.equestriandash.assets.handlers.RacerHandler;
import com.colonelhedgehog.equestriandash.assets.tasks.LocationTask;
import com.colonelhedgehog.equestriandash.assets.tasks.MovementTask;
import com.colonelhedgehog.equestriandash.assets.tasks.PositionTask;
import com.colonelhedgehog.equestriandash.events.*;
import com.colonelhedgehog.equestriandash.utilities.Customization;
import com.colonelhedgehog.equestriandash.utilities.Seeker;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.mcstats.Metrics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * @name EquestrianDash
 * @version 0.9.0
 * @author Robert/ColonelHedgehog
 * Created by Robert AKA ColonelHedgehog. Do not redistribute this or download it from any other location than its project page/GitHub Repository on BukkitDev/SpigotMC.
 * Leave this section (from "@author" to ":)") intact, too, please. :)
 */


/**
 * TODO & FIXME:
 * todo: * Never EVER accept waffles from strangers again.
 */
public class EquestrianDash extends JavaPlugin implements Listener, CommandExecutor
{
    public static boolean tm = false;
    public static EquestrianDash plugin;
    public static Location Lap1;
    public static Location Lap2;
    public static Cuboid LapCuboid;
    public static String Prefix = "§9§l[§3Equestrian§bDash§9§l]§r: ";
    private PropertyHandler propertyHandler;
    private RacerHandler racerHandler;
    private CooldownHandler cooldownHandler;
    private ItemBoxRegistry itemBoxRegistry;
    private VoteBoard voteBoard;
    private GameHandler gameHandler;
    private PositionTask positionTask;
    private MovementTask movementTask;
    private GarbageControl garbageControl;
    private LocationTask locationHandler;

    private BukkitTask locationTask;

    @Override
    public void onEnable()
    {
        plugin = this;
        propertyHandler = new PropertyHandler();
        racerHandler = new RacerHandler();
        powerupsRegistry = new PowerupsRegistry();
        cooldownHandler = new CooldownHandler();
        itemBoxRegistry = new ItemBoxRegistry();
        trackRegistry = new TrackRegistry();
        voteBoard = new VoteBoard();
        gameHandler = new GameHandler();
        positionTask = new PositionTask();
        movementTask = new MovementTask();
        locationHandler = new LocationTask();
        garbageControl = new GarbageControl();

        gameHandler.setState(GameHandler.GameState.WAITING_FOR_PLAYERS);
        registerEvents();
        setupMetrics();

        getCommand("ed").setExecutor(new EDCmd());
        getCommand("vote").setExecutor(new VoteCmd());

        if (getServer().getPluginManager().getPlugin("TitleManager") != null)
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

        if (!configf.exists())
        {
            System.out.println("[EquestrianDash] Could not find a config, so generating one!");
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
        else if (plugin.getConfig().contains("Config") || plugin.getConfig().contains("Config.Powerups"))
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
        if (!tp.exists())
        {
            boolean maed = tp.mkdir();
            if (maed /* u maed */)
            {
                getLogger().info("Created Tracks folder.");
            }
            else /* or naw? */
            {
                getLogger().warning("Failed to create Tracks folder!");
            }
        }

        positionTask.runTaskTimer(this, 20, 20);
    }

    private void diePotato() // In theory a potato *could* be lying around somewhere, right?
    {
        for (World w : Bukkit.getWorlds())
        {
            for (Entity e : w.getEntities())
            {
                if (e instanceof Item)
                {
                    e.remove();
                }
            }
        }
    }

    private void setupMetrics()
    {
        if (getConfig().getBoolean("MetricsEnabled"))
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
        pm.registerEvents(new EntitySpawnListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new VehicleExitListener(), this);
        pm.registerEvents(new HorseJumpListener(), this);
        pm.registerEvents(new EntityDamageByEntityListener(), this);
        pm.registerEvents(new EntityExplodeListener(), this);
        pm.registerEvents(new PlayerInteractEntityListener(), this);
        pm.registerEvents(new Powerups(), this);
        pm.registerEvents(new Seeker(), this);
        pm.registerEvents(new EntityDamageListener(), this);
        pm.registerEvents(new BlockPlaceBreakListeners(), this);
        pm.registerEvents(new BlockDamageListener(), this);
        pm.registerEvents(new PlayerRespawnListener(), this);
        pm.registerEvents(new EntityDeathListener(), this);
        //pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new PlayerToggleSprintListener(), this);
        //pm.registerEvents(new EntityShootBowListener(), this);
        pm.registerEvents(new Customization(), this);
        pm.registerEvents(new FoodLevelChangeListener(), this);
        pm.registerEvents(new EntityTargetLivingEntityListener(), this);
        pm.registerEvents(new PlayerDropItemListener(), this);
        pm.registerEvents(new WorldLoadListener(), this);
        pm.registerEvents(new PlayerCommandPreprocessListener(), this);

        if (!plugin.getConfig().getBoolean("EditMode"))
        {
            pm.registerEvents(new LocationTask(), this); // Don't register it unless we need to, blasted event...
        }
        // pm.registerEvents(new BlockPistonListener(), this);

        setupPowerups();
        setupTracks();
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () ->
                getLogger().info(powerupsRegistry.getPowerups().size() + " powerups were registered."), 20L);

        if (!plugin.getConfig().getBoolean("EditMode"))
        {
            plugin.getVoteBoard().createBoard();
        }
    }

    public ItemBoxRegistry getItemBoxRegistry()
    {
        return itemBoxRegistry;
    }

    public void setItemBoxRegistery(ItemBoxRegistry itemBoxRegistry)
    {
        this.itemBoxRegistry = itemBoxRegistry;
    }

    public void setupItemBoxes()
    {
        getLogger().info("Now initializing Item Box data. This could take a little bit...");

        boolean editmode = getConfig().getBoolean("EditMode");
        final TrackRegistry trackRegistry = this.trackRegistry;
        int index = 0;

        List<Location> processed = new ArrayList<>();
        while (index < trackRegistry.getTracks().size())
        {
            Track t = trackRegistry.getTracks().get(index);
            World w = t.getWorld();
            getLogger().info("Initializing Item Boxes for track: \"" + t.getDisplayName() + "\"");
            if (!t.getItemBoxData().contains("ItemBoxes"))
            {
                getLogger().info("No Item Boxes could be found. Make sure to add some to your map with /ed ib.");
                index++;
                continue;
            }
            Set<String> locs = t.getItemBoxData().getConfigurationSection("ItemBoxes").getKeys(false);
            List<String> locsd = new ArrayList<>();
            locsd.addAll(locs);

            int index2 = 0;
            int index3 = 0;

            w.getEntitiesByClass(EnderCrystal.class).forEach(EnderCrystal::remove);

            while (index3 < locsd.size())
            {
                String str = locsd.get(index2);

                String[] split = str.split("_");
                final Location loc = new Location(w, Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));

                if (processed.contains(loc))
                {
                    index3++;
                    continue;
                }

                processed.add(loc);


                ItemBox ib = new ItemBox(loc, null);

                if (editmode)
                {
                    loc.getChunk().load(true);
                    ib.spawn(false);
                }


                itemBoxRegistry.register(false, ib);
                index3++;
                index2++;
            }

            plugin.getLogger().info("Registered " + index2 + " Item Box" + (index2 == 1 ? "" : "es") + " on track \"" + t.getDisplayName() + ".\"");
            index++;
        }
    }


    private void setupTracks()
    {
        String[] files = new File(EquestrianDash.plugin.getDataFolder() + "/Tracks/").list();

        int num = 0;
        if (files != null)
        {
            for (String file : files)
            {
                World w = Bukkit.getWorld(file);
                if (file.contains("."))
                {
                    getLogger().info("Ignoring file \"" + file + "\" - It's not a folder.");
                    continue;
                }

                if (w == null)
                {
                    getLogger().warning("The file \"" + file + "\" couldn't be matched to a world. Maybe the world it was used with before was deleted? Please delete the folder as well (if that is the case) to speed up this process.");
                    continue;
                }

                getLogger().info("Registering track for \"" + file + "\"");
                Track track = new Track(w);
                getTrackRegistry().registerTrack(track);
                num++;
            }
        }

        getLogger().info("Registered " + num + " track" + (num == 1 ? "" : "s") + ".");
        setupItemBoxes();
    }

    private TrackRegistry trackRegistry;

    public static EquestrianDash getInstance()
    {
        return plugin;
    }

    public TrackRegistry getTrackRegistry()
    {
        return trackRegistry;
    }

    private void setupPowerups()
    {
        FileConfiguration config = getConfig();
        ConfigurationSection section = config.getConfigurationSection("Powerups");

        for (String name : section.getKeys(false))
        {
            try
            {
                ConfigurationSection powerupSection = config.getConfigurationSection("Powerups." + name);

                if (powerupSection.getBoolean("Enabled")) // H4CK3R M0D3: INITIATED
                {
                    Class<?> powerupClass = Class.forName("com.colonelhedgehog.equestriandash.api.powerup.common." + name + "Powerup");
                    Powerup instance = (Powerup) powerupClass.newInstance();
                    powerupsRegistry.registerPowerup(instance);
                }

            }
            catch (ClassNotFoundException e)
            {
                plugin.getLogger().info("Failed to initialize powerup \"" + name + ".\" No powerup class was found for it.");
            }
            catch (InstantiationException | IllegalAccessException e)
            {
                plugin.getLogger().info("Failed to initialize powerup \"" + name + ".\" It did not include a functioning class that implements Powerup.");
                e.printStackTrace();
            }
        }
        /*if (true)
        {
            powerupsRegistry.registerPowerup(new JumpPowerup());
        }
        if (config.getBoolean("Powerups.Speed.Enabled"))
        {
            powerupsRegistry.registerPowerup(new SpeedPowerup());
        }
        if (config.getBoolean("Powerups.TNT.Enabled"))
        {
            powerupsRegistry.registerPowerup(new TNTPowerup());
        }
        if (config.getBoolean("Powerups.Slime.Enabled"))
        {
            powerupsRegistry.registerPowerup(new SlimePowerup());
        }
        if (config.getBoolean("Powerups.Arrow.Enabled"))
        {
            powerupsRegistry.registerPowerup(new ArrowPowerup());
        }
        if (config.getBoolean("Powerups.Ice.Enabled"))
        {
            powerupsRegistry.registerPowerup(new IcePowerup());
        }
        if (config.getBoolean("Powerups.Wither.Enabled"))
        {
            powerupsRegistry.registerPowerup(new WitherPowerup());
        }
        if (config.getBoolean("Powerups.Lightning.Enabled"))
        {
            powerupsRegistry.registerPowerup(new LightningPowerup());
        }
        if (config.getBoolean("Powerups.Thief.Enabled"))
        {
            powerupsRegistry.registerPowerup(new ThiefPowerup());
        }
        if (config.getBoolean("Powerups.Trollkin.Enabled"))
        {
            powerupsRegistry.registerPowerup(new TrollkinPowerup());
        }
        if (config.getBoolean("Powerups.FireCharge.Enabled"))
        {
            powerupsRegistry.registerPowerup(new FireChargePowerup());
        }*/
    }

    @Override
    public void onDisable()
    {
        for (Player p : racerHandler.getPlayers())
        {
            if (p.getVehicle() != null)
            {
                p.getVehicle().remove();
            }
            p.getInventory().clear();
        }

        getGarbageControl().destroyGarbage();
    }

    public PropertyHandler getPropertyHandler()
    {
        return propertyHandler;
    }

    public RacerHandler getRacerHandler()
    {
        return racerHandler;
    }

    public void buildRaceline()
    {
        Track track = gameHandler.getCurrentTrack();
        FileConfiguration td = track.getTrackData();
        Lap1 = new Location(track.getWorld(), td.getDouble("Raceline.Lap1.X"), td.getDouble("Raceline.Lap1.Y"), td.getDouble("Raceline.Lap1.Z"));
        Lap2 = new Location(track.getWorld(), td.getDouble("Raceline.Lap2.X"), td.getDouble("Raceline.Lap2.Y"), td.getDouble("Raceline.Lap2.Z"));
        LapCuboid = new Cuboid(Lap1, Lap2);
    }

    private PowerupsRegistry powerupsRegistry;

    public PowerupsRegistry getPowerupsRegistry()
    {
        return powerupsRegistry;
    }

    public CooldownHandler getCooldownHandler()
    {
        return cooldownHandler;
    }

    public VoteBoard getVoteBoard()
    {
        return voteBoard;
    }

    public GameHandler getGameHandler()
    {
        return gameHandler;
    }

    public PositionTask getPositionTask()
    {
        return positionTask;
    }

    public MovementTask getMovementTask()
    {
        return movementTask;
    }

    public GarbageControl getGarbageControl()
    {
        return garbageControl;
    }

    public LocationTask getLocationHandler()
    {
        return locationHandler;
    }

    public BukkitTask getLocationTask()
    {
        return locationTask;
    }

    public void setLocationTask(BukkitTask locationTask)
    {
        this.locationTask = locationTask;
    }

    public void reloadConfigs()
    {
        reloadConfig();
        //EquestrianDash.getInstance().setupItemBoxes();

        getTrackRegistry().getTracks().forEach(track -> {
            track.getWorld().getEntitiesByClass(EnderCrystal.class).forEach(EnderCrystal::remove);
            track.reloadTrackData();
            track.reloadItemBoxData();

            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    track.getItemBoxes().stream().filter(itembox -> itembox.getEnderCrystal() == null || itembox.getEnderCrystal().isDead()).forEach(ItemBox::spawn);
                }
            }.runTaskLater(plugin, 1);
        });
    }
}