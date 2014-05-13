/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import static com.hand.Race.ItemBox.setName;
import static com.hand.Race.Join.Prefix;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

/**
 *
 * @author Robert
 */
public class Inter implements Listener
{
    public static Main plugin;
    public Inter(Main ins)
    {
        Inter.plugin = ins;
    }
    
    @EventHandler
    public static void onInter(PlayerInteractEvent event)
    {
        Player p = event.getPlayer();
        
            if(p.getItemInHand().getType() == Material.SADDLE && p.getGameMode() != GameMode.CREATIVE && p.getVehicle() != null)
            {
                if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                {
                    p.sendMessage(Prefix + "§b§oOpened horse customization menu!");


                    p.setMetadata("choosingColor", new FixedMetadataValue(plugin, true));
                    p.setMetadata("choosingStyle", new FixedMetadataValue(plugin, false));

                    Inventory Custom = Bukkit.createInventory(p, 9, "§9§lCustomizing: §a§oColors");
                    // Color: WHITE
                    ItemStack White = new ItemStack(Material.WOOL);
                    setName(White, "§l§oWhite");
                    addLore(White, "§9§oHorse color");
                    // Color: BLACK
                    ItemStack Black = new ItemStack(Material.COAL_BLOCK);
                    setName(Black, "§8§l§oBlack");    
                    addLore(Black, "§9§oHorse color");
                    // Color: BROWN
                    ItemStack Brown = new ItemStack(Material.WOOL, 1, (byte) 12);
                    setName(Brown, "§6§l§oBrown");    
                    addLore(Brown, "§9§oHorse color");
                    // Color: CHESTNUT
                    ItemStack Chestnut = new ItemStack(Material.WOOD, 1, (byte) 1);
                    setName(Chestnut, "§e§l§oChestnut");    
                    addLore(Chestnut, "§9§oHorse color");
                    // Color: CREAMY
                    ItemStack Creamy = new ItemStack(Material.QUARTZ_BLOCK, 1, (byte) 1);
                    setName(Creamy, "§7§l§oCreamy");    
                    addLore(Creamy, "§9§oHorse color");
                    // Color: DARK_BROWN
                    ItemStack DarkBrown = new ItemStack(Material.WOOD, 1, (byte) 5);
                    setName(DarkBrown, "§4§l§oDark Brown");    
                    addLore(DarkBrown, "§9§oHorse color");
                    // Color: GRAY
                    ItemStack Gray = new ItemStack(Material.STONE, 1);
                    setName(Gray, "§7§l§oGrey");    
                    addLore(Gray, "§9§oHorse color");

                    if(p.hasPermission("EquestrianDash.HorseColors.White"))
                    {
                    Custom.setItem(1, White);
                    }

                    if(p.hasPermission("EquestrianDash.HorseColors.Black"))
                    {
                    Custom.setItem(2, Black);
                    }

                    if(p.hasPermission("EquestrianDash.HorseColors.Brown"))
                    {
                    Custom.setItem(3, Brown);
                    }

                    if(p.hasPermission("EquestrianDash.HorseColors.Chestnut"))
                    {            
                    Custom.setItem(4, Chestnut);
                    }

                    if(p.hasPermission("EquestrianDash.HorseColors.Creamy"))
                    {
                    Custom.setItem(5, Creamy);
                    }

                    if(p.hasPermission("EquestrianDash.HorseColors.Dark_Brown"))
                    {            
                    Custom.setItem(6, DarkBrown);
                    }

                    if(p.hasPermission("EquestrianDash.HorseColors.Gray"))
                    {
                    Custom.setItem(7, Gray);
                    }
                    Horse h = (Horse) p.getVehicle();
                    h.setVariant(Horse.Variant.HORSE);
                    p.openInventory(Custom);        
                }
            }
    }

    public static void addLore(ItemStack is, String lore)
    {
    ItemMeta meta = (ItemMeta) is.getItemMeta();
    List<String> newlore = new ArrayList<String>();
    newlore.add(lore);
    meta.setLore(newlore);
    is.setItemMeta(meta);
    }
}
