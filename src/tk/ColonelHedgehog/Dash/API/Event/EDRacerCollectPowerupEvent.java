package tk.ColonelHedgehog.Dash.API.Event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import tk.ColonelHedgehog.Dash.API.Entity.ItemBox;
import tk.ColonelHedgehog.Dash.API.Entity.Racer;
import tk.ColonelHedgehog.Dash.API.Powerup.Powerup;

/**
 * Created by ColonelHedgehog on 11/8/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class EDRacerCollectPowerupEvent extends Event
{
    private static HandlerList handlers = new HandlerList();

    private Racer racer;
    private Powerup powerup;

    public EDRacerCollectPowerupEvent(Racer racer, Powerup powerup, ItemBox itembox)
    {
        this.racer = racer;
        this.powerup = powerup;
    }

    public Racer getRacer()
    {
        return this.racer;
    }

    public Powerup getPowerup()
    {
        return this.powerup;
    }



    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }
}
