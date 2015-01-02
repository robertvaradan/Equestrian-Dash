package com.ColonelHedgehog.Dash.API.Event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by ColonelHedgehog on 11/8/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class EDRaceBeginEvent extends Event
{
    private static HandlerList handlers = new HandlerList();

    public EDRaceBeginEvent()
    {
        // Not much to do o_o
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}
