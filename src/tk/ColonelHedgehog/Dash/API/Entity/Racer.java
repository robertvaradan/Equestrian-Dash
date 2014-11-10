package tk.ColonelHedgehog.Dash.API.Entity;

import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import tk.ColonelHedgehog.Dash.Core.Main;
import tk.ColonelHedgehog.Dash.Events.PlayerMoveListener;

/**
 * Created by ColonelHedgehog on 11/8/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public abstract class Racer implements Player
{

    public Horse getHorse()
    {
        return getVehicle() != null && getVehicle() instanceof Horse ? (Horse) getVehicle() : null;
    }

    public int getScore()
    {
        return PlayerMoveListener.evalPlace(this);
    }

    public int getLap()
    {
        return getMetadata("playerLap").get(0).asInt();
    }

    public int getMarkerPosition()
    {
        return getMetadata("markerPos").get(0).asInt();
    }

    public void setLap(int lap)
    {
        setMetadata("playerLap", new FixedMetadataValue(Main.plugin, lap));
    }

    public void setMarkerPostion(int position)
    {
        setMetadata("markerPos", new FixedMetadataValue(Main.plugin, position));
    }

    public boolean isInLine()
    {
        return getMetadata("playerInLine").get(0).asBoolean();
    }
}
