package com.colonelhedgehog.equestriandash.assets.commands;

import com.colonelhedgehog.equestriandash.api.track.Track;
import com.colonelhedgehog.equestriandash.assets.handlers.GameHandler;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by ColonelHedgehog on 11/18/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class VoteCmd implements CommandExecutor
{
    private EquestrianDash plugin = EquestrianDash.getInstance();

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args)
    {
        if(cs instanceof Player)
        {
            Player p = (Player) cs;

            if(plugin.getGameHandler().getGameState() != GameHandler.GameState.WAITING_FOR_PLAYERS)
            {
                p.sendMessage(EquestrianDash.Prefix + "§4Error: §cYou can't vote for maps right now.");
                return false;
            }
            if (!EquestrianDash.plugin.getConfig().getBoolean("Countdown.RandomPick"))
            {
                if (!plugin.getVoteBoard().getVoters().contains(((Player) cs).getUniqueId()))
                {
                    if (args.length > 0)
                    {
                        StringBuilder builder = new StringBuilder();
                        for (String arg : args)
                        {
                            builder.append(" ").append(arg);
                        }

                        Track voted = plugin.getTrackRegistry().getClosest(builder.toString().trim());

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

                        plugin.getVoteBoard().vote(p, voted);

                        plugin.getVoteBoard().updateBoard();

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
