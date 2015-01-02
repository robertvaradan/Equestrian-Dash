/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ColonelHedgehog.Dash.Events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.ChatColor;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import com.ColonelHedgehog.Dash.Core.Main;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Robert
 */
public class PlayerDeathListener implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        event.getDrops().clear();
        event.setDroppedExp(0);
        Player p = event.getEntity();
        if (p.getVehicle() != null)
        {
            if (p.getVehicle() instanceof Horse)
            {
                Horse h = (Horse) p.getVehicle();
                h.setOwner(null);
                h.setPassenger(null);
                h.setHealth(0.0);
            }
        }
        p.getInventory().clear();

        event.setDeathMessage(PlayerJoinListener.Prefix + ChatColor.RED + event.getDeathMessage() + ".");

        if (plugin.getServer().getPluginManager().getPlugin("ProtocolLib") != null)
        {
            forceRespawn(event.getEntity());
        }
    }

    private void forceRespawn(Player p)
    {
        PacketContainer command = new PacketContainer(PacketType.Play.Client.CLIENT_COMMAND);
        command.getClientCommands().write(0, EnumWrappers.ClientCommand.PERFORM_RESPAWN);

        try
        {
            ProtocolLibrary.getProtocolManager().recieveClientPacket(p, command);


        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            throw new IllegalStateException("Unable to send packet " + command, e);
        }
    }

}
