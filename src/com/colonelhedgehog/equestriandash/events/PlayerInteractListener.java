/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.colonelhedgehog.equestriandash.events;

import com.colonelhedgehog.equestriandash.api.powerup.Powerup;
import com.colonelhedgehog.equestriandash.assets.handlers.RacerHandler;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import com.colonelhedgehog.equestriandash.core.GarbageControl;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Robert
 */
public class PlayerInteractListener implements Listener
{
    public static EquestrianDash plugin = EquestrianDash.plugin;

    public static void addLore(ItemStack is, String lore)
    {
        ItemMeta meta = is.getItemMeta();
        List<String> newlore = new ArrayList<>();
        newlore.add(lore);
        meta.setLore(newlore);
        is.setItemMeta(meta);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent event)
    {
        Player p = event.getPlayer();

        if(event.getClickedBlock() != null && GarbageControl.DespawningIce.contains(event.getClickedBlock().getLocation()))
        {
            return;
        }

        if (p.getItemInHand().getType() == Material.SADDLE && p.getGameMode() != GameMode.CREATIVE)
        {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            {
                p.sendMessage(PlayerJoinListener.Prefix + "§b§oOpened horse customization menu!");

                p.setMetadata("choosingColor", new FixedMetadataValue(plugin, true));
                p.setMetadata("choosingStyle", new FixedMetadataValue(plugin, false));
                //Horse h = (Horse) p.getVehicle();

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

                if (p.hasPermission("equestriandash.horsecolors.white"))
                {
                    Custom.setItem(1, White);
                }

                if (p.hasPermission("equestriandash.horsecolors.black"))
                {
                    Custom.setItem(2, Black);
                }

                if (p.hasPermission("equestriandash.horsecolors.brown"))
                {
                    Custom.setItem(3, Brown);
                }

                if (p.hasPermission("equestriandash.horsecolors.chestnut"))
                {
                    Custom.setItem(4, Chestnut);
                }

                if (p.hasPermission("equestriandash.horsecolors.creamy"))
                {
                    Custom.setItem(5, Creamy);
                }

                if (p.hasPermission("equestriandash.horsecolors.dark_brown"))
                {
                    Custom.setItem(6, DarkBrown);
                }

                if (p.hasPermission("equestriandash.horsecolors.gray"))
                {
                    Custom.setItem(7, Gray);
                }
                p.openInventory(Custom);
            }
        }
        else if (p.getGameMode() != GameMode.CREATIVE)
        {
            for (Powerup powerup : plugin.getPowerupsRegistry().getPowerups())
            {
                if (powerup.getItem().isSimilar(p.getItemInHand()))
                {
                    RacerHandler racerHandler = plugin.getRacerHandler();
                    if (!racerHandler.getRacer(event.getPlayer()).inventoryIsSpinning())
                    {
                        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                        {
                            powerup.doOnRightClick(racerHandler.getRacer(p), event.getAction());
                            if (powerup.cancelledEvents().contains(Powerup.ActionType.ALL) || powerup.cancelledEvents().contains(Powerup.ActionType.RIGHT_CLICK))
                            {
                                event.setCancelled(true);
                            }
                        }
                        else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
                        {
                            powerup.doOnLeftClick(racerHandler.getRacer(p), event.getAction());
                            if (powerup.cancelledEvents().contains(Powerup.ActionType.ALL) || powerup.cancelledEvents().contains(Powerup.ActionType.LEFT_CLICK))
                            {
                                event.setCancelled(true);
                            }
                        }

                        return; // Whichever powerup was loaded first is used. THE-EARLY-BIRD-GETS-THE-WORM RULE IN MOTION! You should be smart enough to make your itemstack different in some respect anyway though.
                    }
                }
            }
        }
    }

    private void setName(ItemStack is, String name)
    {
        ItemMeta m = is.getItemMeta();
        m.setDisplayName(name);
        is.setItemMeta(m);
    }
}
