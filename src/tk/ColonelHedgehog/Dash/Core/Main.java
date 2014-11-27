/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Core;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import net.minecraft.util.org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.mcstats.Metrics;
import tk.ColonelHedgehog.Dash.API.Powerup.Default.*;
import tk.ColonelHedgehog.Dash.API.Powerup.PowerupsRegistery;
import tk.ColonelHedgehog.Dash.API.Track.Track;
import tk.ColonelHedgehog.Dash.API.Track.TrackRegistery;
import tk.ColonelHedgehog.Dash.Assets.Commands.EDCmd;
import tk.ColonelHedgehog.Dash.Assets.Commands.VoteCmd;
import tk.ColonelHedgehog.Dash.Assets.CooldownHandler;
import tk.ColonelHedgehog.Dash.Assets.GameState;
import tk.ColonelHedgehog.Dash.Assets.Powerups;
import tk.ColonelHedgehog.Dash.Assets.VoteBoard;
import tk.ColonelHedgehog.Dash.Events.*;
import tk.ColonelHedgehog.Dash.Lib.Customization;
import tk.ColonelHedgehog.Dash.Lib.Seeker;

import java.io.File;
import java.io.IOException;


/**
 * @name EquestrianDash
 * @version 0.9.0
 * @author Robert/TheHandfish
 * Created by Robert AKA TheHandfish. Do not redistribute this or download it from any other location than its project page/GitHub Repository on BukkitDev. 
 * Leave this section (from "@author" to ":)") intact, too, please. :)
 */
public class Main extends JavaPlugin implements Listener, CommandExecutor 
{
    public static Main plugin;
    public static Location Lap1;
    public static Location Lap2;
    public static Cuboid LapCuboid;
    public static String Prefix = "§9§l[§3Equestrian§bDash§9§l]§r: ";

    @Override
    public void onEnable()
    {
        plugin = this;

        GameState.setState(GameState.State.WAITING_FOR_PLAYERS);
        registerEvents();
        setupMetrics();

        getCommand("ed").setExecutor(new EDCmd());
        getCommand("vote").setExecutor(new VoteCmd());
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
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new CreatureSpawnListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new VehicleExistListener(), this);
        getServer().getPluginManager().registerEvents(new HorseJumpListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        getServer().getPluginManager().registerEvents(new EntityExplodeListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractEntityListener(), this);
        getServer().getPluginManager().registerEvents(new Powerups(), this);
        getServer().getPluginManager().registerEvents(new Seeker(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDeathListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerToggleSprintListener(), this);
        getServer().getPluginManager().registerEvents(new EntityShootBowListener(), this);
        getServer().getPluginManager().registerEvents(new Customization(), this);
        getServer().getPluginManager().registerEvents(new FoodLevelChangeListener(), this);
        getServer().getPluginManager().registerEvents(new EntityTargetLivingEntityListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItemListener(), this);
        getServer().getPluginManager().registerEvents(new WorldLoadListener(), this);

        setupPowerups();
        setupTracks();
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable()
        {
            @Override
            public void run()
            {
                getLogger().info(powerupsRegistery.getPowerups().size() + " powerups were registered.");
            }
        }, 20L);


        if(getServer().getPluginManager().getPlugin("ProtocolLib") == null)
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

    private void setupTracks()
    {
        trackRegistery = new TrackRegistery();

        File[] files = new File(Main.plugin.getDataFolder() + "/Tracks/").listFiles();

        int num = 0;
        if (files != null)
        {
            for(File file : files)
            {
                if (file.exists())
                {
                    World w = Bukkit.getWorld(file.getName());
                    Main.getTrackRegistery().registerTrack(new Track(w));
                    num++;
                }
            }
        }

        getLogger().info("Registered " + num + " track(s).");

    }

    private static TrackRegistery trackRegistery;

    public static Main getInstance()
    {
        return plugin;
    }

    public static TrackRegistery getTrackRegistery()
    {
        return trackRegistery;
    }

    private void setupPowerups()
    {
        powerupsRegistery = new PowerupsRegistery();
        cooldownHandler = new CooldownHandler();

        if(plugin.getConfig().getBoolean("Powerups.Launcher.Enabled"))
        {
            powerupsRegistery.registerPowerup(new LauncherPowerup());
        }
        if (plugin.getConfig().getBoolean("Powerups.Speed.Enabled"))
        {
            powerupsRegistery.registerPowerup(new SpeedPowerup());
        }
        if (plugin.getConfig().getBoolean("Powerups.TNT.Enabled"))
        {
            powerupsRegistery.registerPowerup(new TNTPowerup());
        }
        if (plugin.getConfig().getBoolean("Powerups.Slime.Enabled"))
        {
            powerupsRegistery.registerPowerup(new SlimePowerup());
        }
        if (plugin.getConfig().getBoolean("Powerups.Arrow.Enabled"))
        {
            powerupsRegistery.registerPowerup(new ArrowPowerup());
        }
        if (plugin.getConfig().getBoolean("Powerups.Ice.Enabled"))
        {
            powerupsRegistery.registerPowerup(new IcePowerup());
        }
        if (plugin.getConfig().getBoolean("Powerups.Wither.Enabled"))
        {
            powerupsRegistery.registerPowerup(new WitherPowerup());
        }
        if (plugin.getConfig().getBoolean("Powerups.Thief.Enabled"))
        {
            powerupsRegistery.registerPowerup(new ThiefPowerup());
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

    private static PowerupsRegistery powerupsRegistery;

    public static PowerupsRegistery getPowerupsRegistery()
    {
        return powerupsRegistery;
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