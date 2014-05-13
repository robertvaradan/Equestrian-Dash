/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

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
        this.saveDefaultConfig();
        
 


        
    }

    @Override
    public void onDisable()
    {
        
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
    
    public static boolean edmark = false;
    
    @Override 
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("edmarker")) { // If the player typed /basic then do the following...   
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length >= 1 && args[0].equalsIgnoreCase("on")) {
                    if (p.hasPermission("equestriandash.editor")) {
                        p.sendMessage("§9Equestrian Dash §aedit mode §benabled§a.");
                        if (args.length > 1) {
                            p.setMetadata("editorNumber", new FixedMetadataValue(plugin, args[1]));
                        } else if (args.length == 1) {
                            p.sendMessage("§6WARNING: §eNo start number was stated. Using \"§a1§e.\"");
                            p.setMetadata("editorNumber", new FixedMetadataValue(plugin, 1));
                        }
                        p.setMetadata("editorEnabled", new FixedMetadataValue(plugin, true));
                        return true;
                    } else {
                        p.sendMessage("§4ERROR: §cInsufficient permissions.");
                        return false;
                    }
                } else if (p.hasPermission("equestriandash.editor")) {
 
                    p.setMetadata("editorEnabled", new FixedMetadataValue(plugin, false));
                    p.setMetadata("editorNumber", new FixedMetadataValue(plugin, 1));
                    p.sendMessage("§9Equestrian Dash §aedit mode §bdisabled§a.");
                    return true;
                    }
            } else {
                sender.sendMessage("You must be a player!");
                return false;
            }
        }
        // do something
        return false;
    }

}