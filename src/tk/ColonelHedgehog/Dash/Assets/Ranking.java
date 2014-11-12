package tk.ColonelHedgehog.Dash.Assets;

import org.bukkit.entity.Player;

/**
 * Created by ColonelHedgehog on 11/11/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class Ranking
{
    public static int getRank(Player p)
    {
        for(String entry : p.getScoreboard().getEntries())
        {
            System.out.println("TESTING: " + entry);
        }

        return 1;
    }
}
