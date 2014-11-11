package tk.ColonelHedgehog.Dash.Assets.Commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import tk.ColonelHedgehog.Dash.Core.Main;
import tk.ColonelHedgehog.Dash.Events.WorldLoadListener;

import static tk.ColonelHedgehog.Dash.Events.PlayerJoinListener.Prefix;

/**
 * Created by ColonelHedgehog on 11/7/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class EDCmd implements CommandExecutor
{
    private Main plugin = Main.plugin;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length > 0)
        {
            if (args[0].equalsIgnoreCase("line1"))  // If the player typed /basic then do the following...
            {
                if (sender instanceof Player)
                {
                    Player p = (Player) sender;
                    p.sendMessage(Main.Prefix + "§9Editor: §bPosition 1§3 has been set.");
                    plugin.getConfig().set("Config.Raceline.Lap1.X", p.getLocation().getBlockX());
                    plugin.getConfig().set("Config.Raceline.Lap1.Y", p.getLocation().getBlockY());
                    plugin.getConfig().set("Config.Raceline.Lap1.Z", p.getLocation().getBlockZ());
                    plugin.saveConfig();

                    return true;
                }
                else
                {
                    sender.sendMessage(Main.Prefix + "§4Error: §6You must be a player to perform this command.");
                    return false;
                }
            }
            else if (args[0].equalsIgnoreCase("line2"))  // If the player typed /basic then do the following...
            {
                if (sender instanceof Player)
                {
                    Player p = (Player) sender;
                    p.sendMessage(Main.Prefix + "§9Editor: §bPosition 2§3 has been set.");
                    plugin.getConfig().set("Config.Raceline.Lap2.X", p.getLocation().getBlockX());
                    plugin.getConfig().set("Config.Raceline.Lap2.Y", p.getLocation().getBlockY());
                    plugin.getConfig().set("Config.Raceline.Lap2.Z", p.getLocation().getBlockZ());
                    plugin.saveConfig();

                    return true;
                }
                else
                {
                    sender.sendMessage(Main.Prefix + "§4Error: §6You must be a player to perform this command.");
                    return false;
                }
            }
            else if (args[0].equalsIgnoreCase("spawn"))  // If the player typed /basic then do the following...
            {
                if (sender instanceof Player)
                {
                    Player p = (Player) sender;
                    if (args.length >= 2)
                    {
                        if (args[1].equalsIgnoreCase("FlareSpawn"))
                        {
                            plugin.getConfig().set("Config.Fw.X", p.getLocation().getX());
                            plugin.getConfig().set("Config.Fw.Y", p.getLocation().getY());
                            plugin.getConfig().set("Config.Fw.Z", p.getLocation().getZ());
                        }
                        else if (args[1].equalsIgnoreCase("Spawn1"))
                        {
                            plugin.getConfig().set("Config.Spawn1.X", p.getLocation().getX());
                            plugin.getConfig().set("Config.Spawn1.Y", p.getLocation().getY());
                            plugin.getConfig().set("Config.Spawn1.Z", p.getLocation().getZ());
                        }
                        else if (args[1].equalsIgnoreCase("Spawn2"))
                        {
                            plugin.getConfig().set("Config.Spawn2.X", p.getLocation().getX());
                            plugin.getConfig().set("Config.Spawn2.Y", p.getLocation().getY());
                            plugin.getConfig().set("Config.Spawn2.Z", p.getLocation().getZ());
                        }
                        else if (args[1].equalsIgnoreCase("Spawn3"))
                        {
                            plugin.getConfig().set("Config.Spawn3.X", p.getLocation().getX());
                            plugin.getConfig().set("Config.Spawn3.Y", p.getLocation().getY());
                            plugin.getConfig().set("Config.Spawn3.Z", p.getLocation().getZ());
                        }
                        else if (args[1].equalsIgnoreCase("Spawn4"))
                        {
                            plugin.getConfig().set("Config.Spawn4.X", p.getLocation().getX());
                            plugin.getConfig().set("Config.Spawn4.Y", p.getLocation().getY());
                            plugin.getConfig().set("Config.Spawn4.Z", p.getLocation().getZ());
                        }
                        else if (args[1].equalsIgnoreCase("Spawn5"))
                        {
                            plugin.getConfig().set("Config.Spawn5.X", p.getLocation().getX());
                            plugin.getConfig().set("Config.Spawn5.Y", p.getLocation().getY());
                            plugin.getConfig().set("Config.Spawn5.Z", p.getLocation().getZ());
                        }
                        else if (args[1].equalsIgnoreCase("Spawn6"))
                        {
                            plugin.getConfig().set("Config.Spawn6.X", p.getLocation().getX());
                            plugin.getConfig().set("Config.Spawn6.Y", p.getLocation().getY());
                            plugin.getConfig().set("Config.Spawn6.Z", p.getLocation().getZ());
                        }
                        else if (args[1].equalsIgnoreCase("Spawn7"))
                        {
                            plugin.getConfig().set("Config.Spawn7.X", p.getLocation().getX());
                            plugin.getConfig().set("Config.Spawn7.Y", p.getLocation().getY());
                            plugin.getConfig().set("Config.Spawn7.Z", p.getLocation().getZ());
                        }
                        else if (args[1].equalsIgnoreCase("Spawn8"))
                        {
                            plugin.getConfig().set("Config.Spawn8.X", p.getLocation().getX());
                            plugin.getConfig().set("Config.Spawn8.Y", p.getLocation().getY());
                            plugin.getConfig().set("Config.Spawn8.Z", p.getLocation().getZ());
                        }
                        else
                        {
                            p.sendMessage(Main.Prefix + "§4Error: §6\"§e" + args[1] + "§6\" is not a valid spawnpoint.");
                            return false;
                        }

                        p.sendMessage(Prefix + "§3Spawnpoint for §b" + args[1] + " §3saved.");

                        plugin.saveConfig();
                        return true;
                    }
                    else
                    {
                        p.sendMessage(Main.Prefix + "§4Error: §6Specifiy spawn type! §aExample: §6Spawn1, Spawn2, FlareSpawn");
                        return false;
                    }
                }
                else
                {
                    sender.sendMessage(Main.Prefix + "§4You must be a player!");
                    return false;
                }
            }
            else if(args[0].equalsIgnoreCase("edit"))
            {
                if(!(sender instanceof Player))
                {
                    sender.sendMessage(Main.Prefix + "§cYou can only use edit mode as a player.");
                    return false;
                }

                Player p = (Player) sender;

                int number;
                if(args.length >= 2)
                {
                    try
                    {
                        number = Integer.parseInt(args[1]);
                        p.setMetadata("editorEnabled", new FixedMetadataValue(plugin, true));
                        p.setMetadata("editorNumber", new FixedMetadataValue(plugin, number));
                        p.sendMessage(Main.Prefix + "§dEdit mode updated. Marker number: §5" + number);
                        return true;
                    }
                    catch (NumberFormatException nfe)
                    {
                        p.sendMessage(Main.Prefix + "§e" + args[1] + "§c is not a number.");
                        return false;
                    }
                }
                else
                {
                    if(!p.hasMetadata("editorEnabled") || p.getMetadata("editorEnabled").get(0).asBoolean())
                    {
                        p.setMetadata("editorEnabled", new FixedMetadataValue(plugin, false));
                        p.setMetadata("editorNumber", new FixedMetadataValue(plugin, 0));
                        p.sendMessage(Main.Prefix + "§eMarker editor disabled.");
                    }
                    else if(p.hasMetadata("editorEnabled"))
                    {
                        p.setMetadata("editorEnabled", new FixedMetadataValue(plugin, true));
                        p.sendMessage(Main.Prefix + "§dMarker editor enabled.");
                    }
                }


            }
            else if (args[0].equalsIgnoreCase("killhorses") || args[0].equalsIgnoreCase("kh"))
            {
                for(World w : plugin.getServer().getWorlds())
                {
                    WorldLoadListener.killallHorses(w);
                }
                sender.sendMessage(Main.Prefix + "§cKilled all horses!");
            }
            else if (args[0].equalsIgnoreCase("reload"))
            {
                plugin.reloadConfig();
                sender.sendMessage(Main.Prefix + "§aConfig reloaded.");
            }
            else
            {
                sender.sendMessage(Prefix + "§a§l/ed §b§lCommand Usages:");
                sender.sendMessage("§8- §7/ed line1 §8- §fSets the first corner of the raceline.");
                sender.sendMessage("§8- §7/ed line2 §8- §fSets the second corner of the raceline.");
                sender.sendMessage("§8- §7/ed spawn Spawn[1-8] §8- §fExample: /ed spawn Spawn4");
                sender.sendMessage("§8- §7/ed spawn FlareSpawn §8- §fSets where the firework will shoot from.");
                sender.sendMessage("§8- §7/ed edit [number] §8- §fEnables/disables marker edit mode. All signs placed are markers.");
                sender.sendMessage("§8- §7/ed (killhorses:kh) §8- §fKills all horses.");
                sender.sendMessage("§8- §7/ed reload §8- §fReloads this plugin's config.");
            }
        }
        else
        {
            sender.sendMessage(Main.Prefix + "§4Error: §6Too few arguments! Do §a/ed help §6for help.");
        }
        return false;
    }
}
