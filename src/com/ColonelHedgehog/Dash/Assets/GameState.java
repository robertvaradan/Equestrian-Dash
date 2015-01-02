package com.ColonelHedgehog.Dash.Assets;

import com.ColonelHedgehog.Dash.API.Powerup.ItemBox.ItemBox;
import com.ColonelHedgehog.Dash.API.Track.Track;
import com.ColonelHedgehog.Dash.Core.Main;
import org.bukkit.Bukkit;

/**
 * Created by ColonelHedgehog on 11/15/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class GameState
{
    private static State state;
    private static Track track;

    public static void setState(State state)
    {
        GameState.state = state;
    }

    public static State getState()
    {
        return state;
    }

    public static Track getCurrentTrack()
    {
        return track;
    }

    public static void setCurrentTrack(Track track)
    {
        if(track != null)
        {
            GameState.track = track;
        }
        else
        {

            throw new NullPointerException();
        }

        for(Track others: Main.getTrackRegistry().getTracks())
        {
            if(!others.equals(track))
            {
                Bukkit.unloadWorld(others.getWorld(), false);
                Bukkit.getLogger().info("Unloading world \"" + track.getWorld().getName() + "\".");
            }
        }


        String[] s = Main.plugin.getConfig().getString("Lobby").split(",");

        Bukkit.unloadWorld(Bukkit.getWorld(s[0]), false);
        Main.buildRaceline();
        Bukkit.getLogger().info("Unloading world \"" + s[0] + "\".");

        int i = 0;
        for (ItemBox ib : Main.getItemBoxRegistry().getByWorld(track.getWorld()))
        {
            i++;
            ib.spawn(true);
        }

        Main.getInstance().getLogger().info(i + " Item Box" + (i == 1 ? "" : "es") + " were spawned on track: \"" + track.getDisplayName() + "\".");

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ed reload");
    }

    public enum State
    {
        WAITING_FOR_PLAYERS, RACE_ENDED, RACE_IN_PROGRESS, COUNT_DOWN_TO_START
    }
}
