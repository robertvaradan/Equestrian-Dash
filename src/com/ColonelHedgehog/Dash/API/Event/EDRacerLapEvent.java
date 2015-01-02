package com.ColonelHedgehog.Dash.API.Event;

import com.ColonelHedgehog.Dash.API.Entity.Racer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by ColonelHedgehog on 11/8/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class EDRacerLapEvent extends Event
{
    private static HandlerList handlers = new HandlerList();

    private Racer racer;
    private int lap;

    public EDRacerLapEvent(Racer racer, int lap)
    {
        this.racer = racer;
        this.lap = lap;
    }

    public Racer getRacer()
    {
        return this.racer;
    }

    public int getLap()
    {
        return this.lap;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }
}
