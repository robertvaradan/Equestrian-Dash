package com.ColonelHedgehog.Dash.Events.System;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;

/**
 * Created by ColonelHedgehog on 12/26/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class ServerStopListener extends Thread
{
    @Override
    public void run()
    {
        for (World w : Bukkit.getWorlds())
        {
            for (Entity e : w.getEntities())
            {
                if (e instanceof EnderCrystal)
                {
                    e.remove();
                }
            }
        }
    }
}
