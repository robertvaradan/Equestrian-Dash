package tk.ColonelHedgehog.Dash.API.Entity;

import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import tk.ColonelHedgehog.Dash.Assets.Ranking;
import tk.ColonelHedgehog.Dash.Core.Main;
import tk.ColonelHedgehog.Dash.Events.PlayerMoveListener;

/**
 * Created by ColonelHedgehog on 11/8/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class Racer
{
    private Player p;

    public Racer(Player p)
    {
        this.p = p;
    }

    public Horse getHorse()
    {
        return p.getVehicle() != null && p.getVehicle() instanceof Horse ? (Horse) p.getVehicle() : null;
    }

    public Player getPlayer()
    {
        return p;
    }

    public int getScore()
    {
        return PlayerMoveListener.evalPlace(p);
    }

    public int getLap()
    {
        return p.getMetadata("playerLap").get(0).asInt();
    }

    public int getMarkerPosition()
    {
        return p.getMetadata("markerPos").get(0).asInt();
    }

    public void setLap(int lap)
    {
        p.setMetadata("playerLap", new FixedMetadataValue(Main.plugin, lap));
    }

    public void setMarkerPostion(int position)
    {
        p.setMetadata("markerPos", new FixedMetadataValue(Main.plugin, position));
    }

    public boolean isInLine()
    {
        return p.getMetadata("playerInLine").get(0).asBoolean();
    }

    public Racer getRacer()
    {
        return this;
    }

    public boolean inventoryIsSpinning()
    {
        return p.getMetadata("invSpinning").get(0).asBoolean();
    }

    public int getRank()
    {
        return Ranking.getRank(this.getPlayer());
    }
}
