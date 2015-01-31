package com.ColonelHedgehog.Dash.Events;

import com.ColonelHedgehog.Dash.Core.EquestrianDash;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created by ColonelHedgehog on 12/26/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class PlayerCommandPreprocessListener implements Listener
{
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event)
    {
        if(event.getMessage().equals("/summon EnderCrystal"))
        {
            event.getPlayer().sendMessage(EquestrianDash.Prefix + "§6WARNING: §eTo properly save an item box, please be sure to use §a/ed itembox§e.");
            event.setCancelled(true);
        }
        else if((event.getMessage().toLowerCase().startsWith("/reload ") || event.getMessage().equalsIgnoreCase("/reload")) || (event.getMessage().toLowerCase().startsWith("/rl ") || event.getMessage().equalsIgnoreCase("/rl")))
        {
            event.getPlayer().sendMessage(EquestrianDash.Prefix + "§4§lSTOP! §cReload will break this plugin. Do §nNOT §ctry and reload your server while this is running. Restart it instead!");
            event.setCancelled(true);
        }
    }

}
