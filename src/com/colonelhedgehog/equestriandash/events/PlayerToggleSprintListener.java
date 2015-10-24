/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.colonelhedgehog.equestriandash.events;

import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;

/**
 * @author Robert
 */
public class PlayerToggleSprintListener implements Listener
{
    public static EquestrianDash plugin = EquestrianDash.plugin;

    @EventHandler
    public void onSprint(PlayerToggleSprintEvent event)
    {

    }

}
