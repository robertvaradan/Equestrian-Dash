/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Robert
 */
public class ItemBox implements Listener
{
    public static Main plugin;
    public ItemBox(Main ins)
    {
        ItemBox.plugin = ins;
    }
    
    @EventHandler
    public static void onBox(PlayerInteractEntityEvent event)
    {    
        if(event.getRightClicked() instanceof EnderCrystal && !event.getPlayer().getMetadata("pCooldown").get(0).asBoolean())
        {
            giveReward((Player) event.getPlayer(), event.getRightClicked(), event.getRightClicked().getLocation().getBlockX(), event.getRightClicked().getLocation().getBlockY(), event.getRightClicked().getLocation().getBlockZ());
        }        
    }
    
    public static void giveReward(final Player p, final Entity e, int cx, int cy, int cz)
    {
                final double nx = cx + 0.25;
                final double ny = cy - 1;
                final double nz = cz + 0.25;
                //e.getServer().broadcastMessage("Was an ender crystal!");
                Firework fw = e.getWorld().spawn(e.getLocation(), Firework.class);
                FireworkMeta data = fw.getFireworkMeta();
                data.addEffects(FireworkEffect.builder().withColor(Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PURPLE).with(FireworkEffect.Type.STAR).build());
                data.setPower(0);
                fw.setFireworkMeta(data);  
                    
    int random = (int )(Math.random() * 16 + 1);
    if(random == 1)
    {
    ItemStack it = new ItemStack(Material.NETHER_STALK);
    setName(it, "§0§l§o???");
    p.getInventory().setItemInHand(it);  
    String rewardname = "some Netherwart (what?)";
    p.sendMessage(Join.Prefix + "§6You got " + ChatColor.GREEN + "" + rewardname + "§6!");
    }
    else if(random == 2)
    {
    ItemStack it = new ItemStack(Material.SUGAR);
    setName(it, "§l§oSpeed Up!");
    p.getInventory().setItemInHand(it); 
    String rewardname = "some Sugar";
    p.sendMessage(Join.Prefix + "§6You got " + ChatColor.GREEN + "" + rewardname + "§6!");    
    }
    else if(random == 3)
    {
    ItemStack it = new ItemStack(Material.SUGAR);
    setName(it, "§l§oSpeed Up!");    
    p.getInventory().setItemInHand(it);
    String rewardname = "some Sugar";
    p.sendMessage(Join.Prefix + "§6You got " + ChatColor.GREEN + "" + rewardname + "§6!");    
    }
    else if(random == 4)
    {
    ItemStack it = new ItemStack(Material.FERMENTED_SPIDER_EYE);
    String rewardname = "a Fermented Spider Eye";
    setName(it, "§7§l§oBecome a Ghost!");    
    p.getInventory().setItemInHand(it); 
    p.sendMessage("§6You got " + ChatColor.GREEN + "" + rewardname + "§6!");    
    }
    else if(random == 5)
    {
    ItemStack it = new ItemStack(Material.DIAMOND);
    setName(it, "§b§l§oExplode Forward!");        
    p.getInventory().setItemInHand(it);
    String rewardname = "a Diamond";
    p.sendMessage(Join.Prefix + "§6You got " + ChatColor.GREEN + "" + rewardname + "§6!");    
    }
    else if(random == 6)
    {
    ItemStack it = new ItemStack(Material.BLAZE_ROD);
    setName(it, "§b§l§oSpawn Firey Friends!");            
    p.getInventory().setItemInHand(it);
    String rewardname = "a Blaze Rod";
    p.sendMessage(Join.Prefix + "§6You got " + ChatColor.GREEN + "" + rewardname + "§6!");    
    }    
    else if(random == 7)
    {
    ItemStack it = new ItemStack(Material.BLAZE_ROD);
    setName(it, "§b§l§oSpawn Firey Friends!");                
    p.getInventory().setItemInHand(it);
    String rewardname = "a Blaze Rod";
    p.sendMessage(Join.Prefix + "§6You got " + ChatColor.GREEN + "" + rewardname + "§6!");    
    }    
    else if(random == 8)
    {
    ItemStack arr = new ItemStack(Material.ARROW, 4);
    setName(arr, "§4§oH§6§o§e§oe§4§oa§6§ot §c§oSeekers");                
    p.getInventory().addItem(arr);
    ItemStack item = new ItemStack(Material.BOW, 1);
    item.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 3);
    setName(item, "§9§l§oSnipe Enemies!");                
    p.getInventory().setItemInHand(item);
    String rewardname = "a Bow and Heatseaking Arrows";
    p.sendMessage(Join.Prefix + "§6You got " + ChatColor.GREEN + "" + rewardname + "§6!");    
    }    
    else if(random == 9)
    {
    ItemStack it = new ItemStack(Material.TNT);
    setName(it, "§4§l§oMake 'em go §c§l§o§nBOOM§4§l§o!");                    
    p.getInventory().setItemInHand(it);
    String rewardname = "some TNT";    
    p.sendMessage("§6You got " + ChatColor.GREEN + "" + rewardname + "§6!");    
    }    
    else if(random == 10)
    {
    ItemStack it = new ItemStack(Material.TNT);
    setName(it, "§4§l§oMake 'em go §c§l§o§nBOOM§4§l§o!");                    
    p.getInventory().setItemInHand(it);
    String rewardname = "some TNT";        
    p.sendMessage(Join.Prefix + "§6You got " + ChatColor.GREEN + "" + rewardname + "§6!");    
    }    
    else if(random == 11)
    {
    ItemStack it = new ItemStack(Material.LEASH);
    setName(it, "§6§l§oGrapple Horses!");                        
    p.getInventory().setItemInHand(it);
    String rewardname = "a Lead";        
    p.sendMessage(Join.Prefix + "§6You got " + ChatColor.GREEN + "" + rewardname + "§6!");    
    }    
    else if(random == 12)
    {
    ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
    item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 4);
    setName(item, "§3§l§oKnock Them Away!");                            
    p.getInventory().setItemInHand(item);
    String rewardname = "a Diamond Sword";            
    p.sendMessage(Join.Prefix + "§6You got " + ChatColor.GREEN + "" + rewardname + "§6!");    
    }
    else if(random == 13)
    {
    ItemStack it = new ItemStack(Material.DIAMOND_BARDING);
    setName(it, "§a§l§oDeflects Fire and Arrows!");                                
    p.getInventory().setItemInHand(it);
    String rewardname = "a Diamond Barding";  
    p.sendMessage(Join.Prefix + "§6You got " + ChatColor.GREEN + "" + rewardname + "§6!");    
    }    
    else if(random == 14)
    {
    ItemStack it = new ItemStack(Material.IRON_BARDING);
    setName(it, "§8§l§oDeflects Arrows!");                                    
    p.getInventory().setItemInHand(it);
    String rewardname = "an Iron Barding";                    
    p.sendMessage(Join.Prefix + "§6You got " + ChatColor.GREEN + "" + rewardname + "§6!");
    }    
    else if(random == 15)
    {
    ItemStack it = new ItemStack(Material.IRON_BARDING);
    setName(it, "§8§l§oDeflects Arrows!");                                    
    p.getInventory().setItemInHand(it);
    String rewardname = "an Iron Barding";                    
    p.sendMessage(Join.Prefix + "§6You got " + ChatColor.GREEN + "" + rewardname + "§6!");
    }
    else if(random == 16)
    {
    ItemStack it = new ItemStack(Material.NETHER_STALK);
    setName(it, "§0§l§o???");    
    p.getInventory().setItemInHand(it);  
    String rewardname = "some Netherwart (what?)";
    p.sendMessage(Join.Prefix + "§6You got " + ChatColor.GREEN + "" + rewardname + "§6!");
    }    

    e.remove();
    new BukkitRunnable() 
    {
        
        @Override
        public void run() 
        {
          // .-.
            Location loc = new Location(e.getWorld(), nx, ny, nz);
            p.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
            cancel(); //Cancels the timer
        }
      
    }.runTaskTimer(plugin, 100L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);
    

    }    
    
    public static void setName(ItemStack is, String name)
    {
        ItemMeta m = is.getItemMeta();
        m.setDisplayName(name);
        is.setItemMeta(m);
    }

}
