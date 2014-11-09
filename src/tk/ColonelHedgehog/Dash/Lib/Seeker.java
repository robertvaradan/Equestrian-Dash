/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Lib;

import java.util.Iterator;

import tk.ColonelHedgehog.Dash.Core.Main;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.util.Vector;

/**
 *
 * @author Robert
 */
public class Seeker implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onSeek(EntityShootBowEvent event)
    {
        double minAngle = 6.2831853071795862D;
        Entity minEntity = null;
        Player player = (Player) event.getEntity();
        for (@SuppressWarnings("rawtypes")
        Iterator iterator = player.getNearbyEntities(64D, 64D, 64D)
        .iterator(); iterator.hasNext();) {
        Entity entity = (Entity) iterator.next();
        if (player.hasLineOfSight(entity)
        && (entity instanceof LivingEntity)
        && !entity.isDead()) {
        Vector toTarget = entity.getLocation().toVector()
        .clone()
        .subtract(player.getLocation().toVector());
        double angle = event.getProjectile().getVelocity()
        .angle(toTarget);
        if (angle < minAngle) {
        minAngle = angle;
        minEntity = entity;
        }
    }
}
 
if (minEntity != null) {
new TrackTask((Arrow) event.getProjectile(), (LivingEntity) minEntity, plugin);
}
        
    }
}
