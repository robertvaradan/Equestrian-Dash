package com.ColonelHedgehog.Dash.API.Entity;

import com.ColonelHedgehog.Dash.Core.EquestrianDash;
import org.bukkit.Bukkit;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import com.ColonelHedgehog.Dash.Assets.Ranking;
import com.ColonelHedgehog.Dash.Events.PlayerMoveListener;

import java.util.UUID;

/**
 * Created by ColonelHedgehog on 11/8/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class Racer
{
    private UUID puuid;

    public Racer(Player p)
    {
        this.puuid = p.getUniqueId();
    }

    public Horse getHorse()
    {
        return Bukkit.getPlayer(puuid).getVehicle() != null && Bukkit.getPlayer(puuid).getVehicle() instanceof Horse ? (Horse) Bukkit.getPlayer(puuid).getVehicle() : null;
    }

    public Player getPlayer()
    {
        return Bukkit.getPlayer(puuid);
    }

    public int getScore()
    {
        return PlayerMoveListener.evalPlace(Bukkit.getPlayer(puuid));
    }

    public int getLap()
    {
        return Bukkit.getPlayer(puuid).getMetadata("playerLap").get(0).asInt();
    }

    public int getMarkerPosition()
    {
        return Bukkit.getPlayer(puuid).getMetadata("markerPos").get(0).asInt();
    }

    public void setLap(int lap)
    {
        Bukkit.getPlayer(puuid).setMetadata("playerLap", new FixedMetadataValue(EquestrianDash.plugin, lap));
    }

    public void setMarkerPostion(int position)
    {
        Bukkit.getPlayer(puuid).setMetadata("markerPos", new FixedMetadataValue(EquestrianDash.plugin, position));
    }

    public boolean isInLine()
    {
        return Bukkit.getPlayer(puuid).getMetadata("playerInLine").get(0).asBoolean();
    }

    public Racer getRacer()
    {
        return this;
    }

    public boolean inventoryIsSpinning()
    {
        return Bukkit.getPlayer(puuid).hasMetadata("invSpinning") && Bukkit.getPlayer(puuid).getMetadata("invSpinning").get(0).asBoolean();
    }

    public int getRank()
    {
        return Ranking.getRank(this.getPlayer());
    }
}
