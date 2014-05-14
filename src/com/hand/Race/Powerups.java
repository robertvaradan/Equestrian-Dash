/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import static com.hand.Race.Join.Prefix;
import static com.hand.Race.Join.plugin;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Robert
 */
public class Powerups implements Listener
{
    public static Main plugin;
    public Powerups(Main ins)
    {
        Powerups.plugin = ins;
    }
    
    @EventHandler
    public static void onPow(PlayerInteractEvent event)
    {
        Player p = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_AIR)
        {
            evalEffect(p);
        }
        else if (event.getAction() == Action.LEFT_CLICK_AIR)
        {
            specialTnT(p);
        }
    }
    
    public static void specialTnT(Player p)
    {
        if(p.getItemInHand().getType() == Material.TNT)
        {
        p.playSound(p.getLocation(), Sound.FUSE, 7, 1);
        TNTPrimed tnt = (TNTPrimed) p.getWorld().spawn(p.getLocation(), TNTPrimed.class);
        tnt.setVelocity(p.getLocation().getDirection().normalize().multiply(2.5));
        ((TNTPrimed)tnt).setFuseTicks(60);  
        p.getInventory().removeItem(p.getInventory().getItemInHand());        
        }
    }
    
    public static void speedUp(Player horse, int time, int str)
    {
        horse.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, str));
        horse.getInventory().setItemInHand(new ItemStack(Material.AIR));
    }
    
    public static void removeItem(final Horse h)
    {
        
    new BukkitRunnable() 
    {
        
        @Override
        public void run() 
        {
        h.getInventory().removeItem(new ItemStack(Material.IRON_BARDING));
        h.getInventory().removeItem(new ItemStack(Material.DIAMOND_BARDING));
        h.setMetadata("HorseIron", new FixedMetadataValue(plugin, false));
        h.setMetadata("HorseDiamond", new FixedMetadataValue(plugin, false));

            cancel(); //Cancels the timer
        }
      
    }.runTaskTimer(plugin, 200L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);

    }
            private int fcount;

    public static void evalEffect(Player p)
    {
        Material i = p.getItemInHand().getType();
        if(i == Material.SUGAR || i == Material.SKULL_ITEM || i == Material.FERMENTED_SPIDER_EYE || i == Material.DIAMOND || i == Material.BLAZE_ROD || i == Material.TNT || i == Material.IRON_BARDING || i == Material.NETHER_STALK || i == Material.DIAMOND_BARDING)
        {
        boolean exception = false;
        if(p.getItemInHand().getType() == Material.SUGAR)
        {
            speedUp(p, 150, 2);
            p.getWorld().playEffect(p.getLocation(), Effect.BLAZE_SHOOT, 1);
        }
        else if(p.getItemInHand().getType() == Material.FERMENTED_SPIDER_EYE)
        {
            p.playSound(p.getLocation(), Sound.GHAST_MOAN, 7, 1);
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 1));                    
            if(p.getVehicle() != null)
            {
            Horse horse = (Horse) p.getVehicle();
            horse.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 1));
            }
            
            int random = new Random().nextInt(Bukkit.getOnlinePlayers().length);
            Player target = Bukkit.getOnlinePlayers()[random];

            if(target == p)
            {
            target.sendMessage(Prefix + "§7§l§oYou stole your own item. §nFAIL.");
            }
            else if(target != p)
            {
            target.sendMessage(Prefix + "§7§l§o" + p.getDisplayName() + " stole your item!");
            target.sendMessage(Prefix + "§7§l§oYou stole " + target.getDisplayName() + "'s item!");
            exception = true;
            ItemStack car = new ItemStack(target.getInventory().getItemInHand().getType(), 1);
            p.getInventory().addItem(car);
            target.getInventory().removeItem(car);                        
            }
        }
        else if(p.getItemInHand().getType() == Material.DIAMOND)
        {
            speedUp(p, 20, 30);  
        p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 7, 1);            
        }
        else if(p.getItemInHand().getType() == Material.BLAZE_ROD)
        {
            Location l1 = new Location(p.getWorld(), p.getLocation().getBlockX() + 1, p.getLocation().getBlockY(), p.getLocation().getBlockZ());
            Location l2 = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY() - 1, p.getLocation().getBlockZ());
            p.getWorld().spawnCreature(l1, EntityType.BLAZE);
            p.getWorld().spawnCreature(l2, EntityType.BLAZE);
        }    

        else if(p.getItemInHand().getType() == Material.TNT)
        {
        p.playSound(p.getLocation(), Sound.FUSE, 7, 1);                        
          Entity tnt = p.getWorld().spawn(p.getLocation(), TNTPrimed.class);
          ((TNTPrimed)tnt).setFuseTicks(40);            
        }
        else if(p.getItemInHand().getType() == Material.IRON_BARDING)
        {
        p.playSound(p.getLocation(), Sound.HORSE_ARMOR, 1, 1);                                                            
            if(p.getVehicle() != null)
            {
            Horse h = (Horse) p.getVehicle();
            h.setMetadata("HorseIron", new FixedMetadataValue(plugin, true));        
            h.getInventory().addItem(new ItemStack(Material.IRON_BARDING));
            removeItem(h);
            }
        }
        else if(p.getItemInHand().getType() == Material.NETHER_STALK)
        {
        p.playSound(p.getLocation(), Sound.ITEM_BREAK, 7, 1);      
        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 150, 20));
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 150, 20));
        }
        else if(p.getItemInHand().getType() == Material.DIAMOND_BARDING)
        {
        p.playSound(p.getLocation(), Sound.HORSE_ARMOR, 1, 1);                                                           
        Horse h = (Horse) p.getVehicle();
        h.getInventory().addItem(new ItemStack(Material.DIAMOND_BARDING));
        h.setMetadata("HorseDiamond", new FixedMetadataValue(plugin, true));        
        removeItem(h);        
        }
        else if(p.getItemInHand().getType() == Material.SKULL_ITEM && !p.getItemInHand().containsEnchantment(Enchantment.KNOCKBACK))
        {
            WitherSkull w = p.launchProjectile(WitherSkull.class);
            w.setVelocity(p.getEyeLocation().getDirection().multiply(4));
        }
        else if(p.getItemInHand().getType() == Material.SKULL_ITEM && !p.getItemInHand().containsEnchantment(Enchantment.KNOCKBACK))
        {
            WitherSkull w = p.launchProjectile(WitherSkull.class);
            w.setCharged(true);
            w.setVelocity(p.getEyeLocation().getDirection().multiply(8));
        }

        if(!exception)
        {

            p.getInventory().removeItem(p.getInventory().getItemInHand());
            p.updateInventory();

        }
        }
    }
}
