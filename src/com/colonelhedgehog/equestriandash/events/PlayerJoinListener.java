/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.colonelhedgehog.equestriandash.events;

import com.colonelhedgehog.equestriandash.api.entity.Racer;
import com.colonelhedgehog.equestriandash.assets.handlers.GameHandler;
import com.colonelhedgehog.equestriandash.assets.handlers.RacerHandler;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import io.puharesource.mc.titlemanager.api.TitleObject;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Robert
 */
public class PlayerJoinListener implements Listener
{
    public static HashMap<Location, UUID> SpawnPoints = new HashMap<>();
    public static EquestrianDash plugin = EquestrianDash.plugin;
    public static String Prefix = "§9§l[§3Equestrian§bDash§9§l]§r: ";

    @EventHandler
    public void onJoin(final PlayerJoinEvent event)
    {
        Player p = event.getPlayer();

        String[] s = EquestrianDash.plugin.getConfig().getString("Lobby").split(",");
        Location teleport = new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]), Float.parseFloat(s[5]), Float.parseFloat(s[4]));
        p.teleport(teleport);

        if (!(plugin.getConfig().getBoolean("EditMode")))
        {
            if (plugin.getGameHandler().getGameState() != GameHandler.GameState.WAITING_FOR_PLAYERS)
            {
                if (plugin.getConfig().getBoolean("NotPlaying.ForceSpectate"))
                {
                    p.sendMessage(EquestrianDash.Prefix + "§cThis game is in progress!");

                    if (EquestrianDash.tm)
                    {
                        new TitleObject("§aThis game is in progress!", "§eYou have joined as a spectator.").send(p);
                        p.setGameMode(GameMode.SPECTATOR);
                    }
                }
                else if(plugin.getConfig().getBoolean("NotPlaying.ForceKick"))
                {
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            p.kickPlayer("§cThis game is in progress!");
                        }
                    }.runTask(plugin);
                }

                return;
            }

            final ItemStack it = new ItemStack(Material.SADDLE);
            setName(it, "§9§l§oCustomize §b§l§oColors");
            PlayerInteractListener.addLore(it, "§6§oRight click §a§oto customize your horse's colors and style!");
            p.getInventory().addItem(it);

            p.removePotionEffect(PotionEffectType.SLOW);
            p.setHealth(p.getMaxHealth());
            p.setWalkSpeed(0.2F);
            p.setLevel(0);
            p.setExp(0);

            p.setFoodLevel(20);
            p.setSaturation(10);
            p.setExhaustion(0F);

            p.setTotalExperience(0);
            p.setGameMode(GameMode.ADVENTURE);


            //EquestrianDash.buildRaceline(player);
            RacerHandler racerHandler = plugin.getRacerHandler();
            racerHandler.racers.add(new Racer(p));
            p.setMetadata("trackPlace", new FixedMetadataValue(plugin, 1));
            p.setMetadata("playerLap", new FixedMetadataValue(plugin, 0));
            p.setMetadata("markerPos", new FixedMetadataValue(plugin, 0));
            p.setMetadata("ebled", new FixedMetadataValue(plugin, false));
            p.setMetadata("colorKey", new FixedMetadataValue(plugin, 1));
            p.setMetadata("patternKey", new FixedMetadataValue(plugin, 2));
            p.setMetadata("choosingColor", new FixedMetadataValue(plugin, false));
            p.setMetadata("choosingStyle", new FixedMetadataValue(plugin, false));
            p.setMetadata("playerInLine", new FixedMetadataValue(plugin, false));
            p.setMetadata("inving", new FixedMetadataValue(plugin, false));
            p.setMetadata("invSpinning", new FixedMetadataValue(plugin, false));

            //activateSuperCharged(player);

            event.setJoinMessage(Prefix + ChatColor.AQUA + p.getName() + " §3is now competing!");
            plugin.getVoteBoard().updateBoard();
            int players = p.getServer().getOnlinePlayers().size();
            //player.sendMessage(Prefix + ChatColor.GOLD + "Joined! §c§l§oRight-click with the saddle to customize horse colors!");
            //int maxplayers = plugin.getConfig().getInt("Config.Players.Max");
            //Player[] players = player.getServer().getOnlinePlayers().equals();
            //player.sendMessage(EquestrianDash.Prefix + "§6Thanks for joining!");

            if (players < Bukkit.getMaxPlayers())
            {
                p.sendMessage(EquestrianDash.Prefix + "§aWelcome! §eVote for the map with /vote.");
                int minplayers = plugin.getConfig().getInt("Players.MinPlayers");

                if (racerHandler.getPlayers().size() == minplayers)
                {
                    plugin.getGameHandler().startGame();
                }
            }
            else
            {
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        p.kickPlayer(EquestrianDash.Prefix + "§cThis game is full!");
                    }
                }.runTask(plugin);
            }
        }
        else
        {
            p.sendMessage(EquestrianDash.Prefix + "§aYou are in §9Edit Mode.");
        }
    }

    private void setName(ItemStack is, String name)
    {
        ItemMeta m = is.getItemMeta();
        m.setDisplayName(name);
        is.setItemMeta(m);
    }
}