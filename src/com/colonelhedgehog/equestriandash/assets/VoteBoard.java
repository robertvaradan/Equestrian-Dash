package com.colonelhedgehog.equestriandash.assets;

import com.colonelhedgehog.equestriandash.assets.handlers.RacerHandler;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import com.colonelhedgehog.equestriandash.api.track.Track;

import java.util.*;

/**
 * Created by ColonelHedgehog on 11/18/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class VoteBoard
{
    private Scoreboard board;
    private Objective obj;
    private EquestrianDash plugin = EquestrianDash.getInstance();

    private HashMap<Track, Integer> Votes = new HashMap<>();
    private List<UUID> Voters = new ArrayList<>();

    public void createBoard()
    {
        ScoreboardManager voteboard = Bukkit.getScoreboardManager();
        board = voteboard.getNewScoreboard();
        obj = board.registerNewObjective("test", "edvotes");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§aMap Voting");

        for(Track track : plugin.getTrackRegistry().getTracks())
        {
            Votes.put(track, 0);
        }
    }

    public List<UUID> getVoters()
    {
        return Voters;
    }


    public HashMap<Track, Integer> getVotes()
    {
        return Votes;
    }

    public void updateBoard()
    {
        if(obj == null)
        {
            return;
        }

        for (Map.Entry<Track, Integer> entry : Votes.entrySet())
        {
            Score sc = obj.getScore(trimmed("§b" + entry.getKey().getDisplayName()));
            sc.setScore(entry.getValue());
        }

        RacerHandler racerHandler = plugin.getRacerHandler();

        for (Player on : racerHandler.getPlayers())
        {
            on.setScoreboard(board);
        }
    }

    public void vote(Player voter, Track choice)
    {
        Score sc = obj.getScore(trimmed("§b" + choice.getDisplayName()));
        //sc.setScore(sc.getScore() + 1);


        Voters.add(voter.getUniqueId());
        Votes.put(choice, sc.getScore() + 1);
    }

    private String trimmed(String in)
    {
        if(in.length() > 16)
        {
            return in.substring(0, 15) + "…";
        }
        return in;
    }
}
