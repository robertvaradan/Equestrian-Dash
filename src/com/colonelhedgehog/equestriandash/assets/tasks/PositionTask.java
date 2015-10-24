package com.colonelhedgehog.equestriandash.assets.tasks;

import com.colonelhedgehog.equestriandash.api.track.Marker;
import com.colonelhedgehog.equestriandash.api.track.Track;
import com.colonelhedgehog.equestriandash.assets.Ranking;
import com.colonelhedgehog.equestriandash.assets.handlers.GameHandler;
import com.colonelhedgehog.equestriandash.assets.handlers.RacerHandler;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import com.colonelhedgehog.equestriandash.utilities.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.UUID;

public class PositionTask extends BukkitRunnable
{
    private HashMap<UUID, Integer> places;
    private EquestrianDash plugin = EquestrianDash.getInstance();

    public PositionTask()
    {
        places = new HashMap<>();
    }

    public void setPlace(UUID uuid, int place)
    {
        //plugin.getLogger().info(Bukkit.getPlayer(uuid).getName() + ": PLAYSE: " + place);
        places.put(uuid, place);
    }

    public int getPlace(UUID uuid)
    {
        if(!places.containsKey(uuid))
        {
            setPlace(uuid, 0);
        }

        return places.get(uuid);
    }

    @Override
    public void run()
    {
        //plugin.getLogger().info("TASK RUNN: " + getTaskId());
        GameHandler gameHandler = plugin.getGameHandler();
        Track track = gameHandler.getCurrentTrack();

        if(track == null)
        {
            return;
        }

        FileConfiguration trackData = track.getTrackData();
        int radius = trackData.getInt("MarkerCheckRadius"); // Actually it's more like a cube but whatever.

        if (plugin.getGameHandler().getGameState() != GameHandler.GameState.RACE_IN_PROGRESS)
        {
            return;
        }

        RacerHandler racerHandler = plugin.getRacerHandler();

        for (Player p : racerHandler.getPlayers())
        {
            if(p.hasMetadata("finished") && p.getMetadata("finished").get(0).asBoolean())
            {
                continue;
            }

            Location playerLoc = p.getLocation();
            int pX = playerLoc.getBlockX();
            int pY = playerLoc.getBlockY();
            int pZ = playerLoc.getBlockZ();

            trinaryLoop: // Freakin' cool, just learned about this!
            for (int x = -radius; x <= radius; x++)
            {
                for (int y = -radius; y <= radius; y++)
                {
                    for (int z = -radius; z <= radius; z++)
                    {
                        Block b = p.getWorld().getBlockAt(pX + x, pY + y, pZ + z);

                        Track currentTrack = plugin.getGameHandler().getCurrentTrack();

                        Marker marker = currentTrack.getMarkerHandler().getMarkerAtLocation(b.getLocation());

                        int ppos;
                        if (marker == null)
                        {
                            continue;
                        }

                        ppos = marker.getNumber();

                        int fetchedCount = track.getTrackData().getInt("Markers");
                        int markers = fetchedCount > 0 ? fetchedCount : track.getMarkerHandler().getMarkers().size();
                        int plap = p.getMetadata("playerLap").get(0).asInt();

                        p.setMetadata("markerPos", new FixedMetadataValue(plugin, ppos));
                        setPlace(p.getUniqueId(), plap == 0 ? 0 : (plap * markers) + ppos);
                        break trinaryLoop;
                    }
                }
            }
        }
        //places.put(p.getUniqueId(), 0);
        placePlayers();
    }

    public void placePlayers()
    {
        //plugin.getLogger().info("Placing players!");
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();


        board.clearSlot(DisplaySlot.SIDEBAR);

        Objective objective = board.registerNewObjective("playerplaces", "dummy");
        Objective nameplate = board.registerNewObjective("nameplates", "dummy");

        objective.setDisplayName("§6Player §9Places");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        RacerHandler racerHandler = plugin.getRacerHandler();

        nameplate.setDisplayName("§9§l/ §f" + racerHandler.getPlayers().size());
        nameplate.setDisplaySlot(DisplaySlot.BELOW_NAME);

        GameHandler gameHandler = plugin.getGameHandler();

        for (Player p : racerHandler.getPlayers())
        {

            Ranking.scores.put(p.getUniqueId(), getPlace(p.getUniqueId()));

            int place = getPlace(p.getUniqueId());
            int rank = Ranking.getRank(p);
            //plugin.getLogger().info("PLACE: " + place);
            //plugin.getLogger().info("RANKING: " + rank);

            String dispname = Formatting.getPlaceColor(rank) + p.getName();

            dispname = (dispname.length() > 16 ? dispname.substring(0, 15) + "…" : dispname);

            Score score = objective.getScore(dispname);

            //p.sendMessage(EquestrianDash.Prefix + "§5§nApplying the score: " + inc);
            score.setScore(place);



            //plugin.getLogger().info(p.getName() + " Rank: " + index);

            /*String name = Formatting.getPlaceColor(index) + "" + index + "" + Formatting.getPlaceSuffix(index);
            Score namescore = nameplate.getScore(p.getName() + " - " + name);
            namescore.setScore(index);*/
            Score namescore = nameplate.getScore(p.getName());
            namescore.setScore(rank);
            p.setScoreboard(board);
        }
    }
}
