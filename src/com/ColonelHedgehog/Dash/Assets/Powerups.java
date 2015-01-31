/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ColonelHedgehog.Dash.Assets;

import com.ColonelHedgehog.Dash.Core.EquestrianDash;
import com.ColonelHedgehog.Dash.Events.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
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
    public static EquestrianDash plugin = EquestrianDash.plugin;
    
    public static void specialTnT(Player p)
    {
        if(p.getItemInHand().getType() == Material.TNT)
        {
        p.playSound(p.getLocation(), Sound.FUSE, 7, 1);
        TNTPrimed tnt = p.getWorld().spawn(p.getLocation(), TNTPrimed.class);
        tnt.setVelocity(p.getLocation().getDirection().normalize().multiply(2.5));
        tnt.setFuseTicks(60);
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

    public static void evalEffect(Player p)
    {/*

        Material i = p.getItemInHand().getType();
        if(i == Material.SUGAR || i == Material.SKULL_ITEM || i == Material.FERMENTED_SPIDER_EYE || i == Material.DIAMOND || i == Material.SLIME_BALL || i == Material.TNT || i == Material.IRON_BARDING || i == Material.NETHER_STALK || i == Material.DIAMOND_BARDING || i == Material.GRASS)
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
            
            int random = new Random().nextInt(Bukkit.getOnlinePlayers().size());
            Player target = Bukkit.getOnlinePlayers()[random];

            if(target == p)
            {
            target.sendMessage(PlayerJoinListener.Prefix + "§7§l§oYou stole your own item. §nFAIL.");
            }
            else
            {
            target.sendMessage(PlayerJoinListener.Prefix + "§7§l§o" + p.getName() + " stole your item!");
            target.sendMessage(PlayerJoinListener.Prefix + "§7§l§oYou stole " + target.getName() + "'s item!");
            exception = true;
            ItemStack car = new ItemStack(target.getInventory().getItemInHand().getType(), 1);
            p.getInventory().addItem(car);
            target.getInventory().removeItem(car);                        
            }
        }
        else if(p.getItemInHand().getType() == Material.DIAMOND)
        {
            Vehicle v = (Vehicle) p.getVehicle();
            v.setVelocity(v.getLocation().getDirection().multiply(new Vector(2, 1, 2)).add(new Vector(0, 1.5, 0)));
            p.setVelocity(p.getLocation().getDirection().multiply(new Vector(2, 1, 2)).add(new Vector(0, 1.5, 0)));
            p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 7, 1);
        }
        else if(p.getItemInHand().getType() == Material.SLIME_BALL)
        {
            Location l1 = new Location(p.getWorld(), p.getLocation().getBlockX() + 1, p.getLocation().getBlockY(), p.getLocation().getBlockZ());
            Location l2 = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ() - 1);
            Slime s1 = (Slime) p.getWorld().spawnEntity(l1, EntityType.SLIME);
            Slime s2 = (Slime) p.getWorld().spawnEntity(l2, EntityType.SLIME);
            Slime s3 = (Slime) p.getWorld().spawnEntity(l1, EntityType.SLIME);
            Slime s4 = (Slime) p.getWorld().spawnEntity(l2, EntityType.SLIME);
            s1.setMetadata("Creator", new FixedMetadataValue(plugin, p.getName()));
            s2.setMetadata("Creator", new FixedMetadataValue(plugin, p.getName()));
            s3.setMetadata("Creator", new FixedMetadataValue(plugin, p.getName()));
            s4.setMetadata("Creator", new FixedMetadataValue(plugin, p.getName()));

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
        else if(p.getItemInHand().getType() == Material.DIAMOND_BARDING && p.getVehicle() != null)
        {
        p.playSound(p.getLocation(), Sound.HORSE_ARMOR, 1, 1);                                                           
        Horse h = (Horse) p.getVehicle();
        h.getInventory().addItem(new ItemStack(Material.DIAMOND_BARDING));
        h.setMetadata("HorseDiamond", new FixedMetadataValue(plugin, true));
        removeItem(h);        
        }
        else if(p.getItemInHand().getType() == Material.SKULL_ITEM && !"§7§lLong-Ranged Revenge!".equals(p.getItemInHand().getItemMeta().getDisplayName()))
        {
            WitherSkull w = p.launchProjectile(WitherSkull.class);
            w.setYield(4);
            w.setVelocity(p.getEyeLocation().getDirection().multiply(4));
            p.setMetadata("scWitherCount", new FixedMetadataValue(plugin, 1));
            exception = true;
            EntityShootBowListener.createWitherHeads(p, w, false);
        }
        else if(p.getItemInHand().getType() == Material.SKULL_ITEM  && "§1§l§oGive 'Em Nether!".equals(p.getItemInHand().getItemMeta().getDisplayName()))
        {
            WitherSkull w = p.launchProjectile(WitherSkull.class);
            w.setCharged(true);
            w.setVelocity(p.getEyeLocation().getDirection().multiply(8));
            w.setYield(8);
            p.setMetadata("scWitherCount", new FixedMetadataValue(plugin, 1));
            exception = true;
            EntityShootBowListener.createWitherHeads(p, w, true);
        }
        else if(p.getItemInHand().getType() == Material.GRASS)
        {
            activateSuperCharged(p);
            deactiveSuperCharged(p);
        }

        if(!exception)
        {

            p.getInventory().removeItem(p.getInventory().getItemInHand());
        }
        }*/
    }

    public static void activateSuperCharged(final Player p)
    {
        p.setMetadata("superCharged", new FixedMetadataValue(plugin, true));
        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
        p.setMetadata("superColorCount", new FixedMetadataValue(plugin, 1));
        Bukkit.broadcastMessage(PlayerJoinListener.Prefix + "§b§l" + p.getName() + " is §5§lS§9§lU§a§lP§e§lE§6§lR §c§lC§5§lH§9§lA§a§lR§e§lG§6§lE§c§lD§9§l!");
        new BukkitRunnable() 
        {
            @Override
            public void run() 
            {
              // .-.
                if(p.getMetadata("superCharged").get(0).asBoolean())
                {
                int num = 1;
                
                    if(p.hasMetadata("superColorCount"))
                    {
                    num = p.getMetadata("superColorCount").get(0).asInt();
                    }
                    //p.sendMessage(EquestrianDash.Prefix + "SuperCharged Debug: Inversion - " + num);
                
                setColoredArmor(p, num);
                
                
                    if(num < 6)
                    {
                    p.setMetadata("superColorCount", new FixedMetadataValue(plugin, num + 1));
                    }
                    else
                    {
                    p.setMetadata("superColorCount", new FixedMetadataValue(plugin, 1));
                    }

                    ExperienceOrb orb = (ExperienceOrb) p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.EXPERIENCE_ORB);
                    orb.setExperience(0);
                }
                else
                {
                    cancel(); //Cancels the timer   
                }
            }

        }.runTaskTimer(plugin, 0L /* The amount of time until the timer starts */, 5L /*  The delay of each call */);
        
    }
    
    public static void deactiveSuperCharged(final Player p)
    {
        new BukkitRunnable() 
        {
            @Override
            public void run() 
            {
              // .-.
                p.setMetadata("superCharged", new FixedMetadataValue(plugin, false));
                p.getInventory().setBoots(null);
                p.getInventory().setLeggings(null);
                p.getInventory().setChestplate(null);
                p.getInventory().setHelmet(null);
                cancel(); //Cancels the timer
            }

        }.runTaskTimer(plugin, 600L /* The amount of time until the timer starts */, 0L /*  The delay of each call */);
    }
    
    public static void setColoredArmor(Player p, int num)
    {
        Color color = Color.PURPLE;
        
        if(num == 1)
        {
        color = Color.PURPLE;            
        }
        else if(num == 2)
        {
        color = Color.BLUE;            
        }
        else if(num == 3)
        {
        color = Color.LIME;            
        }
        else if(num == 4)
        {
        color = Color.YELLOW;            
        }
        else if(num == 5)
        {
        color = Color.ORANGE;            
        }
        else if(num == 6)
        {
        color = Color.RED;            
        }
        

        
        
            ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
            LeatherArmorMeta im = (LeatherArmorMeta) boots.getItemMeta();
            im.setColor(color);
            boots.setItemMeta(im);
            
            ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
            LeatherArmorMeta im2 = (LeatherArmorMeta) legs.getItemMeta();
            im2.setColor(color);
            legs.setItemMeta(im);


            ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta im3 = (LeatherArmorMeta) chest.getItemMeta();
            im3.setColor(color);
            chest.setItemMeta(im3);

            ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
            LeatherArmorMeta im4 = (LeatherArmorMeta) helm.getItemMeta();
            im4.setColor(color);
            helm.setItemMeta(im4);
            
            
            p.getInventory().setBoots(boots);
            p.getInventory().setLeggings(legs);
            p.getInventory().setChestplate(chest);
            p.getInventory().setHelmet(helm);
    
    }
}
