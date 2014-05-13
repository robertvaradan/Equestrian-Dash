/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

/**
 *
 * @author Robert
 */
public class Shoot implements Listener
{
    public static Main plugin;
    public Shoot(Main ins)
    {
        Shoot.plugin = ins;
    }
    
    @EventHandler
    public static void onShoot(EntityShootBowEvent event)
    {
        Entity e = event.getEntity();
        Projectile p = (Projectile) event.getProjectile();
    }    
}
