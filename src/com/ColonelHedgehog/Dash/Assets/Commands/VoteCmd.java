package com.ColonelHedgehog.Dash.Assets.Commands;

import com.ColonelHedgehog.Dash.Assets.VoteBoard;
import com.ColonelHedgehog.Dash.Core.EquestrianDash;
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
            if (!EquestrianDash.plugin.getConfig().getBoolean("Countdown.RandomPick"))
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

                        Track voted = EquestrianDash.getTrackRegistry().getClosest(builder.toString().trim());

                        if (voted == null)
                        {
                            cs.sendMessage(EquestrianDash.Prefix + "§4Error: §cUnknown track: \"§e" + builder.toString().trim() + "§c\".");
                            return false;
                        }

                        cs.sendMessage(EquestrianDash.Prefix + "§aVoted for \"§b" + voted.getDisplayName() + "§a\"!");

                        if (!EquestrianDash.plugin.getConfig().getBoolean("AnonymousVotes"))
                        {
                            Bukkit.broadcastMessage(EquestrianDash.Prefix + "§e" + cs.getName() + " §ahas voted for §b" + voted.getDisplayName() + "§a.");
                        }

                        Player p = (Player) cs;

                        VoteBoard.vote(p, voted);

                        VoteBoard.updateBoard();

                    }
                    else
                    {
                        cs.sendMessage(EquestrianDash.Prefix + "§4Error: §cNo track specified!");
                    }
                }
                else
                {
                    cs.sendMessage(EquestrianDash.Prefix + "§cYou've already voted!");
                }
            }
            else
            {
                cs.sendMessage(EquestrianDash.Prefix + "§6Voting for maps is not enabled on this server.");
            }
        }
        else
        {
            cs.sendMessage(EquestrianDash.Prefix + "§4Error: §cYou are not a player.");
        }
        return false;
    }
}
