/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import static com.hand.Race.Join.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @name Equestrian Dash
 * @version 0.8.3
 * @author Robert/TheHandfish
 * Created by Robert AKA TheHandfish. Do not redistribute this or download it from any other location than its project page/GitHub Repository on BukkitDev. 
 * Leave this section (from "@author" to ":)") intact, too, please. :)
 */
public class Main extends JavaPlugin implements Listener, CommandExecutor 
{
    public static Main plugin;
    public static boolean racing = false;


    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(new Join(this), this);
        getServer().getPluginManager().registerEvents(new Spawn(this), this);
        getServer().getPluginManager().registerEvents(new Leave(this), this);
        getServer().getPluginManager().registerEvents(new Dismount(this), this);
        getServer().getPluginManager().registerEvents(new Jump(this), this);
        getServer().getPluginManager().registerEvents(new Move(this), this);
        getServer().getPluginManager().registerEvents(new Combust(this), this);
        getServer().getPluginManager().registerEvents(new Explode(this), this);
        getServer().getPluginManager().registerEvents(new ItemBox(this), this);
        getServer().getPluginManager().registerEvents(new Powerups(this), this);
        getServer().getPluginManager().registerEvents(new Seeker(this), this);
        getServer().getPluginManager().registerEvents(new CMove(this), this);
        getServer().getPluginManager().registerEvents(new Col(this), this);
        getServer().getPluginManager().registerEvents(new Damage(this), this);
        getServer().getPluginManager().registerEvents(new Login(this), this);
        getServer().getPluginManager().registerEvents(new Place(this), this);
        getServer().getPluginManager().registerEvents(new Respawn(this), this);
        getServer().getPluginManager().registerEvents(new Death(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(this), this);
        getServer().getPluginManager().registerEvents(new Break(this), this);
        getServer().getPluginManager().registerEvents(new Ignite(this), this);
        getServer().getPluginManager().registerEvents(new Inter(this), this);
        getServer().getPluginManager().registerEvents(new Sprint(this), this);
        getServer().getPluginManager().registerEvents(new Shoot(this), this);
        getServer().getPluginManager().registerEvents(new Customization(this), this);
        plugin = this;
        this.saveDefaultConfig();       
    }    
    
    @Override
    public void onDisable()
    {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(p.getVehicle() != null)
            {
                p.getVehicle().remove();
                p.getInventory().clear();
            }
        }
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
       Join.onJoin(event);   
    }
    public void onSpawn(CreatureSpawnEvent event)
    {
       Spawn.onSpawn(event);   
    }
    public void onLeave(PlayerQuitEvent event)
    {
        Leave.onLeave(event);
    }
    public void onDismount(VehicleExitEvent event)
    {
        Dismount.onDismount(event);
    }
    public void onJump(HorseJumpEvent event)
    {
        Jump.onJump(event);
    }
    public void onMove(PlayerMoveEvent event)
    {
        Move.onMove(event);
    }
    public static void onCombust(EntityDamageByEntityEvent event)
    {
        Combust.onCombust(event);
    }
    public static void onSplode(EntityDeathEvent event)
    {
        Splode.onSplode(event);        
    }
    public static void onExplode(EntityExplodeEvent event)
    {
        Explode.onExplode(event);
    }
    public static void onBox(PlayerInteractEntityEvent event)
    {
        ItemBox.onBox(event);
    }
    public static void onPow(PlayerInteractEvent event)
    {
        Powerups.onPow(event);
    }
    public static void onSeek(EntityShootBowEvent event)
    {
        Seeker.onSeek(event);
    }
    public static void onCMove(VehicleMoveEvent event)
    {
        CMove.onCMove(event);
    }
    public static void onCol(VehicleEntityCollisionEvent event)
    {
        Col.onCol(event);
    }
    public static void onDamage(EntityDamageEvent event)
    {
        Damage.onDamage(event);
    }
    public static void onLogin(PlayerQuitEvent event)
    {
        Login.onLogin(event);
    }
    public static void onPlace(BlockPlaceEvent event)
    {
        Place.onPlace(event);
    }
    public static void onRespawn(PlayerRespawnEvent event)
    {
        Respawn.onRespawn(event);
    }
    public static void onDeath(EntityDeathEvent event)
    {
        Death.onDeath(event);
    }
    public static void onPlayerDeath(PlayerDeathEvent event)
    {
        PlayerDeath.onPlayerDeath(event);
    }
    public static void onBreak(BlockBreakEvent event)
    {
        Break.onBreak(event);
    }
    public static void onIgnite(BlockIgniteEvent event)
    {
        Ignite.onIgnite(event);
    }
    public static void onInter(PlayerInteractEvent event)
    {
        Inter.onInter(event);
    }
    public static void onSprint(PlayerToggleSprintEvent event)
    {
        Sprint.onSprint(event);
    }
    public static void onInv(InventoryClickEvent event)
    {
        Customization.onInv(event);
    }
    public static void onShoot(EntityShootBowEvent event)
    {
        Shoot.onShoot(event);
    }
    
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
    {
	if (cmd.getName().equalsIgnoreCase("edspawn"))  // If the player typed /basic then do the following...
        {
            if (sender instanceof Player) 
            {
                Player p = (Player) sender;
                if(args.length >= 1)
                {
                    if(args[0].equalsIgnoreCase("FlareSpawn"))
                    {
                    plugin.getConfig().set("Config.Fw.X", p.getLocation().getX());
                    plugin.getConfig().set("Config.Fw.Y", p.getLocation().getY());
                    plugin.getConfig().set("Config.Fw.Z", p.getLocation().getZ());
                    }
                    else if(args[0].equalsIgnoreCase("Spawn1"))
                    {
                    plugin.getConfig().set("Config.Spawn1.X", p.getLocation().getX());
                    plugin.getConfig().set("Config.Spawn1.Y", p.getLocation().getY());
                    plugin.getConfig().set("Config.Spawn1.Z", p.getLocation().getZ());
                    }
                    else if(args[0].equalsIgnoreCase("Spawn2"))
                    {
                    plugin.getConfig().set("Config.Spawn2.X", p.getLocation().getX());
                    plugin.getConfig().set("Config.Spawn2.Y", p.getLocation().getY());
                    plugin.getConfig().set("Config.Spawn2.Z", p.getLocation().getZ());
                    }
                    else if(args[0].equalsIgnoreCase("Spawn3"))
                    {
                    plugin.getConfig().set("Config.Spawn3.X", p.getLocation().getX());
                    plugin.getConfig().set("Config.Spawn3.Y", p.getLocation().getY());
                    plugin.getConfig().set("Config.Spawn3.Z", p.getLocation().getZ());
                    }
                    else if(args[0].equalsIgnoreCase("Spawn4"))
                    {
                    plugin.getConfig().set("Config.Spawn4.X", p.getLocation().getX());
                    plugin.getConfig().set("Config.Spawn4.Y", p.getLocation().getY());
                    plugin.getConfig().set("Config.Spawn4.Z", p.getLocation().getZ());
                    }
                    else if(args[0].equalsIgnoreCase("Spawn5"))
                    {
                    plugin.getConfig().set("Config.Spawn5.X", p.getLocation().getX());
                    plugin.getConfig().set("Config.Spawn5.Y", p.getLocation().getY());
                    plugin.getConfig().set("Config.Spawn5.Z", p.getLocation().getZ());
                    }
                    else if(args[0].equalsIgnoreCase("Spawn6"))
                    {
                    plugin.getConfig().set("Config.Spawn6.X", p.getLocation().getX());
                    plugin.getConfig().set("Config.Spawn6.Y", p.getLocation().getY());
                    plugin.getConfig().set("Config.Spawn6.Z", p.getLocation().getZ());
                    }
                    else if(args[0].equalsIgnoreCase("Spawn7"))
                    {
                    plugin.getConfig().set("Config.Spawn7.X", p.getLocation().getX());
                    plugin.getConfig().set("Config.Spawn7.Y", p.getLocation().getY());
                    plugin.getConfig().set("Config.Spawn7.Z", p.getLocation().getZ());
                    }
                    else if(args[0].equalsIgnoreCase("Spawn8"))
                    {
                    plugin.getConfig().set("Config.Spawn8.X", p.getLocation().getX());
                    plugin.getConfig().set("Config.Spawn8.Y", p.getLocation().getY());
                    plugin.getConfig().set("Config.Spawn8.Z", p.getLocation().getZ());
                    }
                    else
                    {
                        p.sendMessage("§4Error: §6\"§e" + args[0] + "§6\" is not a valid spawnpoint.");
                        return false;
                    }
                    
                    p.sendMessage(Prefix + "§3Spawnpoint for §b" + args[0] + " §3saved.");
                    
                    this.saveConfig();
                    return true;
                }
                else
                {
                   p.sendMessage("§4Error: §6Specifiy spawn type! §aExample: §6Spawn1, Spawn2, FlareSpawn");
                   return false;
                }
            } 
            else 
            {
                sender.sendMessage("§4You must be a player!");
                return false;
            }
            // do something
            
    
        }
        return false;
    }
}