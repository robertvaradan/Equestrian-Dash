package tk.ColonelHedgehog.Dash.Assets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import tk.ColonelHedgehog.Dash.API.Track.Track;
import tk.ColonelHedgehog.Dash.Core.Main;

import java.util.*;

/**
 * Created by ColonelHedgehog on 11/18/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class VoteBoard
{
    private static ScoreboardManager voteboard;
    private static Scoreboard board;
    private static Objective obj;

    private static HashMap<Track, Integer> Votes = new HashMap<>();
    private static List<UUID> Voters = new ArrayList<>();

    public static void createBoard()
    {
        voteboard = Bukkit.getScoreboardManager();
        board = voteboard.getNewScoreboard();
        obj = board.registerNewObjective("test", "edvotes");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§aMap Voting");

        for(Track track : Main.getTrackRegistery().getTracks())
        {
            Votes.put(track, 0);
        }
    }

    public static List<UUID> getVoters()
    {
        return Voters;
    }


    public static HashMap<Track, Integer> getVotes()
    {
        return Votes;
    }

    public static void updateBoard()
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

        for (Player on : Bukkit.getOnlinePlayers())
        {
            on.setScoreboard(board);
        }
    }

    public static void vote(Player voter, Track choice)
    {
        Score sc = obj.getScore(trimmed("§b" + choice.getDisplayName()));
        //sc.setScore(sc.getScore() + 1);


        Voters.add(voter.getUniqueId());
        Votes.put(choice, sc.getScore() + 1);
    }

    private static String trimmed(String in)
    {
        if(in.length() > 16)
        {
            return in.substring(0, 13) + "...";
        }
        return in;
    }
}
