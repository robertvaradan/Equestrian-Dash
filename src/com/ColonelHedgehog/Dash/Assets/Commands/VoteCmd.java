package com.ColonelHedgehog.Dash.Assets.Commands;

import com.ColonelHedgehog.Dash.Assets.VoteBoard;
import com.ColonelHedgehog.Dash.Core.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.ColonelHedgehog.Dash.API.Track.Track;

/**
 * Created by ColonelHedgehog on 11/18/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class VoteCmd implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args)
    {
        if(cs instanceof Player)
        {
            if (!Main.plugin.getConfig().getBoolean("Countdown.RandomPick"))
            {
                if (!VoteBoard.getVoters().contains(((Player) cs).getUniqueId()))
                {
                    if (args.length > 0)
                    {
                        StringBuilder builder = new StringBuilder();
                        for (String arg : args)
                        {
                            builder.append(" " + arg);
                        }

                        Track voted = Main.getTrackRegistry().getClosest(builder.toString());

                        if (voted == null)
                        {
                            cs.sendMessage(Main.Prefix + "§4Error: §cUnknown track: \"§e" + builder.toString() + "§c\".");
                            return false;
                        }

                        cs.sendMessage(Main.Prefix + "§aVoted for \"§b" + voted.getDisplayName() + "§a\"!");

                        if (!Main.plugin.getConfig().getBoolean("AnonymousVotes"))
                        {
                            Bukkit.broadcastMessage(Main.Prefix + "§e" + cs.getName() + " §ahas voted for §b" + voted.getDisplayName() + "§a.");
                        }

                        Player p = (Player) cs;

                        VoteBoard.vote(p, voted);

                        VoteBoard.updateBoard();

                    }
                    else
                    {
                        cs.sendMessage(Main.Prefix + "§4Error: §cNo track specified!");
                    }
                }
                else
                {
                    cs.sendMessage(Main.Prefix + "§cYou've already voted!");
                }
            }
            else
            {
                cs.sendMessage(Main.Prefix + "§6Voting for maps is not enabled on this server.");
            }
        }
        else
        {
            cs.sendMessage(Main.Prefix + "§4Error: §cYou are not a player.");
        }
        return false;
    }
}
