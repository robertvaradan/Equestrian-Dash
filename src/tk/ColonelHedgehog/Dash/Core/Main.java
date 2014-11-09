/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import tk.ColonelHedgehog.Dash.Assets.Commands.EDCmd;
import tk.ColonelHedgehog.Dash.Assets.Powerups;
import tk.ColonelHedgehog.Dash.Events.*;
import tk.ColonelHedgehog.Dash.Lib.Customization;
import tk.ColonelHedgehog.Dash.Lib.Seeker;

import java.io.File;


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

        registerEvents();

        getCommand("ed").setExecutor(new EDCmd());
        this.saveDefaultConfig();

        File configf = new File(this.getDataFolder() + "/config.yml");

        if(configf.exists())
        {
            System.out.println("[EquestrianDash] Could not find a config, so generating one!");
            getConfig().options().copyDefaults(true);
            saveConfig();
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
    }
    
    public static void buildRaceline(Player p)
    {
        Lap1 = new Location(p.getWorld(), plugin.getConfig().getDouble("Config.Raceline.Lap1.X"), plugin.getConfig().getDouble("Config.Raceline.Lap1.Y"), plugin.getConfig().getDouble("Config.Raceline.Lap1.Z"));
        Lap2 = new Location(p.getWorld(), plugin.getConfig().getDouble("Config.Raceline.Lap2.X"), plugin.getConfig().getDouble("Config.Raceline.Lap2.Y"), plugin.getConfig().getDouble("Config.Raceline.Lap2.Z"));
        LapCuboid = new Cuboid(Lap1, Lap2);
    }
}