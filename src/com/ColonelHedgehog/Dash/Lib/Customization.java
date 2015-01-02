/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ColonelHedgehog.Dash.Lib;

import com.ColonelHedgehog.Dash.Events.PlayerInteractListener;
import com.ColonelHedgehog.Dash.Events.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import com.ColonelHedgehog.Dash.Core.Main;

import static com.ColonelHedgehog.Dash.Events.PlayerInteractEntityListener.setName;

/**
 *
 * @author Robert
 */
public class Customization implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onInv(InventoryClickEvent event)
    {
        //event.setCancelled(true);
        if(event.getInventory().getName().contains("§9§lCustomizing:"))
        {
            final Player p = (Player) event.getWhoClicked();
            if (p.getGameMode() != GameMode.CREATIVE && event.getCurrentItem() != null)
            {
                event.setCancelled(true);
                ItemStack it = event.getCurrentItem();
                short dat = it.getDurability();
                int res = -1;
                if (p.hasMetadata("choosingColor") && p.getMetadata("choosingColor").get(0).asBoolean())
                {
                    if (it == new ItemStack(Material.WOOL, 1, (byte) 0))
                    {
                        res = 1;
                    }
                    else if (it.getType() == Material.COAL_BLOCK)
                    {
                        res = 2;
                    }
                    else if (it.getType() == Material.WOOL)
                    {
                        res = 3;
                    }
                    else if (it.getType() == Material.WOOD && dat == 1)
                    {
                        res = 4;
                    }
                    else if (it.getType() == Material.QUARTZ_BLOCK && dat == 1)
                    {
                        res = 5;
                    }
                    else if (it.getType() == Material.WOOD && dat == 5)
                    {
                        res = 6;
                    }
                    else if (it.getType() == Material.STONE)
                    {
                        res = 7;
                    }

                    if(!it.hasItemMeta() || !it.getItemMeta().hasDisplayName())
                    {
                        event.setCancelled(true);
                        return;
                    }
                    else if(it.getItemMeta().getDisplayName().contains("Customize"))
                    {
                        event.setCancelled(true);
                        return;
                    }

                    p.playSound(p.getLocation(), Sound.HORSE_SADDLE, 3, 1);
                    p.sendMessage(PlayerJoinListener.Prefix + "§a§oHorse color " + it.getItemMeta().getDisplayName() + " §aselected.");
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            p.closeInventory();
                        }

                    }.runTask(plugin);

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
                    //Horse h = (Horse) p.getVehicle();
                    //getHorseColor(p, h, p.getMetadata("colorKey").get(0).asInt());

                }
                else if (p.hasMetadata("choosingStyle") && p.getMetadata("choosingStyle").get(0).asBoolean())
                {
                    if (it.getType() == Material.COAL_ORE)
                    {
                        res = 1;
                    }
                    else if (it.getType() == Material.GLASS)
                    {
                        res = 2;
                    }
                    else if (it.getType() == Material.QUARTZ_BLOCK)
                    {
                        res = 3;
                    }
                    else if (it.getType() == Material.STAINED_CLAY)
                    {
                        res = 4;
                    }
                    else if (it.getType() == Material.IRON_BLOCK)
                    {
                        res = 5;
                    }
                    else if (it.getType() == Material.BONE)
                    {
                        res = 6;
                    }
                    else if (it.getType() == Material.ROTTEN_FLESH)
                    {
                        res = 7;
                    }

                    if (!it.hasItemMeta() || !it.getItemMeta().hasDisplayName())
                    {
                        event.setCancelled(true);
                        return;
                    }
                    else if (it.getItemMeta().getDisplayName().contains("Customize"))
                    {
                        event.setCancelled(true);
                        return;
                    }

                    p.playSound(p.getLocation(), Sound.HORSE_ARMOR, 3, 1);
                    p.sendMessage(PlayerJoinListener.Prefix + "§a§oHorse style " + it.getItemMeta().getDisplayName() + " §aselected.");
                    p.setMetadata("choosingColor", new FixedMetadataValue(plugin, false));
                    final int finalRes = res;
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            p.closeInventory();
                            p.setMetadata("patternKey", new FixedMetadataValue(plugin, finalRes));
                            //Horse h = (Horse) p.getVehicle();
                            //getHorsePattern(p, h, p.getMetadata("patternKey").get(0).asInt());
                        }
                    }.runTask(plugin);


                }
            }
            else
            {
                if(event.getWhoClicked().getGameMode() != GameMode.CREATIVE)
                {
                    event.setCancelled(true);
                }
            }
        }
        else if(event.getWhoClicked().getGameMode() != GameMode.CREATIVE)
        {
            event.setCancelled(true);
        }
    }    

    public static void chooseVarient(Player p)
    {

        Inventory Custom = Bukkit.createInventory(p, 9, "§9§lCustomizing: §6§oStyle");

        p.setMetadata("choosingStyle", new FixedMetadataValue(plugin, true));
        // Style: BLACK_DOTS
        ItemStack White = new ItemStack(Material.COAL_ORE);
        setName(White, "§8§l§oBlack Dots");
        PlayerInteractListener.addLore(White, "§9§oHorse style");
        // Style: NONE
        ItemStack Black = new ItemStack(Material.GLASS);
        setName(Black, "§6§l§oNo Style");
        PlayerInteractListener.addLore(Black, "§9§oHorse style");
        // Style: WHITE
        ItemStack Brown = new ItemStack(Material.QUARTZ_BLOCK);
        setName(Brown, "§l§oWhite");
        PlayerInteractListener.addLore(Brown, "§9§oHorse style");
        // Style: WHITEFIELD
        ItemStack Chestnut = new ItemStack(Material.STAINED_CLAY);
        setName(Chestnut, "§3§l§oWhitefield");
        PlayerInteractListener.addLore(Chestnut, "§9§oHorse style");
        // Style: WHITE_DOTS
        ItemStack Creamy = new ItemStack(Material.IRON_BLOCK);
        setName(Creamy, "§l§oWhite Dots");
        PlayerInteractListener.addLore(Creamy, "§9§oHorse style");
        // Style: SKELETON_HORSE
        ItemStack DarkBrown = new ItemStack(Material.ROTTEN_FLESH);
        setName(DarkBrown, "§2§l§oZombie Horse");
        PlayerInteractListener.addLore(DarkBrown, "§d§oHorse variant");
        // Style: UNDEAD_HORSE
        ItemStack Gray = new ItemStack(Material.BONE);
        setName(Gray, "§7§l§oSkeleton Horse");
        PlayerInteractListener.addLore(Gray, "§d§oHorse variant");

        if(p.hasPermission("equestriandash.horsestyles.black_dots"))
        {
        Custom.setItem(1, White);
        }

        if(p.hasPermission("equestriandash.horsestyles.none"))
        {
        Custom.setItem(2, Black);
        }

        if(p.hasPermission("equestriandash.horsestyles.white"))
        {
        Custom.setItem(3, Brown);
        }

        if(p.hasPermission("equestriandash.horsestyles.whitefield"))
        {
        Custom.setItem(4, Chestnut);
        }

        if(p.hasPermission("equestriandash.horsestyles.white_dots"))
        {
        Custom.setItem(5, Creamy);
        }

        if(p.hasPermission("equestriandash.horsestyles.skeleton"))
        {
        Custom.setItem(6, DarkBrown);
        }

        if(p.hasPermission("equestriandash.horsestyles.zombie"))
        {
        Custom.setItem(7, Gray);
        }

        p.openInventory(Custom);

    }
}
