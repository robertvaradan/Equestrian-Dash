package com.colonelhedgehog.equestriandash.api.entity;

import com.colonelhedgehog.equestriandash.assets.Ranking;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.Bukkit;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

/**
 * Created by ColonelHedgehog on 11/8/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * These sources are not for sale.
 */
public class Racer
{
    private UUID puuid;
    private EquestrianDash plugin = EquestrianDash.getInstance();

    /**
     *
     * @param p The player to initialize as a Racer.
     */
    public Racer(Player p)
    {
        this.puuid = p.getUniqueId();
    }

    /**
     * @param speed Sets the speed of the Horse based on a double.
     *              0.225 is EquestrianDash's default speed.
     */
    public void setHorseSpeed(double speed)
    {
        plugin.getPropertyHandler().setNMSHorseSpeed((Horse) getPlayer().getVehicle(), speed);
    }

    /**
     * @return Horse - The horse the player is riding, or null if he's not
     *         riding one.
     */
    public Horse getHorse()
    {
        Player player = getPlayer();
        return player.getVehicle() != null && player.getVehicle() instanceof Horse ? (Horse) player.getVehicle() : null;
    }

    /**
     * @return Player - The Player object from the UUID.
     */
    public Player getPlayer()
    {
        return Bukkit.getPlayer(puuid);
    }

    /**
     * @return int - The evaluated score of the Player.
     */
    public int getScore()
    {
        return plugin.getPositionTask().getPlace(getPlayer().getUniqueId());
    }

    /**
     * @return int - The evaluated lap.
     */
    public int getLap()
    {
        Player player = getPlayer();
        return player.getMetadata("playerLap").get(0).asInt();
    }

    /**
     * @return int - The number of the nearest level marker.
     */
    public int getMarkerPosition()
    {
        Player player = getPlayer();
        return player.getMetadata("markerPos").get(0).asInt();
    }

    /**
     * @param lap The lap number.
     */
    public void setLap(int lap)
    {
        Player player = getPlayer();
        player.setMetadata("playerLap", new FixedMetadataValue(EquestrianDash.plugin, lap));
    }

    /**
     * @param position The marker position. Note, this will update when the player nears a marker.
     */
    public void setMarkerPostion(int position)
    {
        Player player = getPlayer();
        player.setMetadata("markerPos", new FixedMetadataValue(EquestrianDash.plugin, position));
    }

    /**
     * @return boolean - Whether or not the player is standing inside the race line.
     */
    public boolean isInLine()
    {
        Player player = getPlayer();
        return player.getMetadata("playerInLine").get(0).asBoolean();
    }

    /**
     * @return Racer - Returns the current object.
     */
    public Racer getRacer()
    {
        return this;
    }

    /**
     * @return boolean - Returns whether or not the player is cycling through powerups.
     */
    public boolean inventoryIsSpinning()
    {
        Player player = getPlayer();
        return player.hasMetadata("invSpinning") && player.getMetadata("invSpinning").get(0).asBoolean();
    }

    /**
     * @return int - Returns the stored ranking of the player.
     */
    public int getRank()
    {
        return Ranking.getRank(getPlayer());
    }
}
