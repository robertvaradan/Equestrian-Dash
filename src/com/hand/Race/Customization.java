/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import static com.hand.Race.Inter.addLore;
import static com.hand.Race.ItemBox.setName;
import static com.hand.Race.Join.Prefix;
import static com.hand.Race.Join.getHorseColor;
import static com.hand.Race.Join.getHorsePattern;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Robert
 */
public class Customization implements Listener
{
    public static Main plugin;
    public Customization(Main ins)
    {
        Customization.plugin = ins;
    }
    
    @EventHandler
    public static void onInv(InventoryClickEvent event)
    {
        //event.setCancelled(true);
        final Player p = (Player) event.getWhoClicked();
        if(p.getGameMode() != GameMode.CREATIVE)
        {
        event.setCancelled(true);
        ItemStack it = event.getCurrentItem();
        short dat = it.getDurability();
        int res = -1;
        boolean pass = true;
        if(p.getMetadata("choosingColor").get(0).asBoolean() && p.hasMetadata("choosingColor"))
        {
            if(it == new ItemStack(Material.WOOL, 1, (byte) 0))
            {
                res = 1;
            }
            else if(it.getType() == Material.COAL_BLOCK)
            {
                res = 2;            
            }
            else if(it.getType() == Material.WOOL)
            {
                res = 3;            
            }
            else if(it.getType() == Material.WOOD && dat == 1)
            {
                res = 4;            
            }
            else if(it.getType() == Material.QUARTZ_BLOCK && dat == 1)
            {
                res = 5;            
            }
            else if(it.getType() == Material.WOOD && dat == 5)
            {
                res = 6;            
            }
            else if(it.getType() == Material.STONE)
            {
                res = 7;            
            }
                p.playSound(p.getLocation(), Sound.HORSE_SADDLE, 3, 1);      
                p.sendMessage(Prefix + "§a§oHorse color " + it.getItemMeta().getDisplayName() + " §aselected.");
                p.closeInventory();
                new BukkitRunnable() 
                {

                    @Override
                    public void run() 
                    {
                      // .-.
                        chooseVarient(p);
                        cancel(); //Cancels the timer
                    }

                }.runTaskTimer(plugin, 3L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);

                p.setMetadata("choosingColor", new FixedMetadataValue(plugin, false));
                p.setMetadata("colorKey", new FixedMetadataValue(plugin, res));
                Horse h = (Horse) p.getVehicle();
                getHorseColor(p, h, p.getMetadata("colorKey").get(0).asInt());

        }
        else if(p.hasMetadata("choosingStyle") && p.getMetadata("choosingStyle").get(0).asBoolean())
        {
            if(it.getType() == Material.COAL_ORE)
            {
            res = 1;
            }
            else if(it.getType() == Material.GLASS)
            {
            res = 2;
            }
            else if(it.getType() == Material.QUARTZ_BLOCK)
            {
            res = 3;
            }
            else if(it.getType() == Material.STAINED_CLAY)
            {
            res = 4;
            }
            else if(it.getType() == Material.IRON_BLOCK)
            {
            res = 5;
            }
            else if(it.getType() == Material.BONE)
            {
            res = 6;
            }
            else if(it.getType() == Material.ROTTEN_FLESH)
            {
            res = 7;
            }
                p.playSound(p.getLocation(), Sound.HORSE_ARMOR, 3, 1);      
                p.sendMessage(Prefix + "§a§oHorse style " + it.getItemMeta().getDisplayName() + " §aselected.");
                p.setMetadata("choosingColor", new FixedMetadataValue(plugin, false));
                p.closeInventory();
                p.setMetadata("patternKey", new FixedMetadataValue(plugin, res));
                Horse h = (Horse) p.getVehicle();
                getHorsePattern(p, h, p.getMetadata("patternKey").get(0).asInt());

            
        }
        }
    }    

    public static void chooseVarient(Player p)
    {
            if(p.getVehicle() != null)
            {
            Horse h = (Horse) p.getVehicle();
            Inventory Custom = Bukkit.createInventory(h, 9, "§9§lCustomizing: §a§oColors");

            p.setMetadata("choosingStyle", new FixedMetadataValue(plugin, true));
            int res = -1;
            // Style: BLACK_DOTS
            ItemStack White = new ItemStack(Material.COAL_ORE);
            setName(White, "§8§l§oBlack Dots");
            addLore(White, "§9§oHorse style");
            // Style: NONE
            ItemStack Black = new ItemStack(Material.GLASS);
            setName(Black, "§6§l§oNo Style");    
            addLore(Black, "§9§oHorse style");
            // Style: WHITE
            ItemStack Brown = new ItemStack(Material.QUARTZ_BLOCK);
            setName(Brown, "§l§oWhite");    
            addLore(Brown, "§9§oHorse style");
            // Style: WHITEFIELD
            ItemStack Chestnut = new ItemStack(Material.STAINED_CLAY);
            setName(Chestnut, "§3§l§oWhitefield");    
            addLore(Chestnut, "§9§oHorse style");
            // Style: WHITE_DOTS
            ItemStack Creamy = new ItemStack(Material.IRON_BLOCK);
            setName(Creamy, "§l§oWhite Dots");    
            addLore(Creamy, "§9§oHorse style");
            // Style: SKELETON_HORSE
            ItemStack DarkBrown = new ItemStack(Material.ROTTEN_FLESH);
            setName(DarkBrown, "§2§l§oZombie Horse");    
            addLore(DarkBrown, "§d§oHorse variant");
            // Style: UNDEAD_HORSE
            ItemStack Gray = new ItemStack(Material.BONE);
            setName(Gray, "§7§l§oSkeleton Horse");    
            addLore(Gray, "§d§oHorse variant");

            if(p.hasPermission("EquestrianDash.HorseStyles.Black_Dots"))
            {
            Custom.setItem(1, White);
            }

            if(p.hasPermission("EquestrianDash.HorseStyles.None"))
            {            
            Custom.setItem(2, Black);
            }
            
            if(p.hasPermission("EquestrianDash.HorseStyles.White"))
            {
            Custom.setItem(3, Brown);
            }
            
            if(p.hasPermission("EquestrianDash.HorseStyles.Whitefield"))
            {
            Custom.setItem(4, Chestnut);
            }
            
            if(p.hasPermission("EquestrianDash.HorseStyles.White_Dots"))
            {
            Custom.setItem(5, Creamy);
            }
            
            if(p.hasPermission("EquestrianDash.HorseStyles.Skeleton"))
            {
            Custom.setItem(6, DarkBrown);
            }
            
            if(p.hasPermission("EquestrianDash.HorseStyles.Zombie"))
            {
            Custom.setItem(7, Gray);
            }
            
            p.openInventory(Custom);        
    }
    }
}
