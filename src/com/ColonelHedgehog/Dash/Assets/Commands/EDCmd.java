package com.ColonelHedgehog.Dash.Assets.Commands;

import com.ColonelHedgehog.Dash.API.Powerup.ItemBox.ItemBox;
import com.ColonelHedgehog.Dash.API.Track.Track;
import com.ColonelHedgehog.Dash.Core.Main;
import com.ColonelHedgehog.Dash.Events.WorldLoadListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.util.List;

import static com.ColonelHedgehog.Dash.Events.PlayerJoinListener.Prefix;

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
            if (args[0].equals("track"))
            {
                if(args.length > 1)
                {
                    if(args[1].equalsIgnoreCase("add"))
                    {
                        if(args.length > 2)
                        {
                            if (Main.getTrackRegistry().getTrackByID(args[2]) == null)
                            {
                                World w = Bukkit.getWorld(args[2]);
                                if(w != null)
                                {
                                    if(args.length > 3)
                                    {
                                        Track track = new Track(w);
                                        track.initialize(args[3]);
                                        Main.getTrackRegistry().registerTrack(track);
                                    }
                                    else
                                    {
                                        Track track = new Track(w);
                                        track.initialize(w.getName());
                                        Main.getTrackRegistry().registerTrack(track);
                                    }
                                    sender.sendMessage(Main.Prefix + "§aTrack added!");
                                }
                                else
                                {
                                    sender.sendMessage(Main.Prefix + "§6The specified world doesn't exist!");
                                }
                            }
                            else
                            {
                                sender.sendMessage(Main.Prefix + "§6This world has already been registered as a track.");
                            }
                        }
                        else
                        {
                            sender.sendMessage(Main.Prefix + "§4Error: §cToo few arguments! Do §e/ed help §cfor help.");
                        }
                    }
                    else if(args[1].equalsIgnoreCase("del"))
                    {
                        if(args.length > 2)
                        {
                            Track t = Main.getTrackRegistry().getTrackByID(args[2]);
                            File f = new File(plugin.getDataFolder() + "/Tracks/" + t.getWorld().getName());
                            boolean done = f.delete() || (Main.getTrackRegistry().getTrackByID(args[2]) != null); // cri cri
                            String trackname = t.getDisplayName();
                            Main.getTrackRegistry().deregisterTrack(t);
                            if(done)
                            {
                                Main.plugin.getLogger().info("Deleted a track named " + trackname + " for world " + t.getWorld() + ".");
                                sender.sendMessage(Main.Prefix + "§aWorld \"§e" + t.getWorld().getName() + "§a\" is no longer a track.");
                            }
                            else
                            {
                                sender.sendMessage(Main.Prefix + "§cCouldn't delete Track! Does it exist?");
                            }
                        }
                        else
                        {
                            sender.sendMessage(Main.Prefix + "§cToo few arguments! Do §e/ed help §cfor help.");
                        }
                    }
                    else if(args[1].equalsIgnoreCase("list"))
                    {
                        sender.sendMessage(Main.Prefix + "§b§lAll registered tracks:");
                        List<Track> tracks = Main.getTrackRegistry().getTracks();
                        for(Track track : tracks)
                        {
                            sender.sendMessage("§8- §9Name: \"§b" + track.getDisplayName() + "§9\", World: §b" + track.getWorld().getName());
                        }

                        if(tracks.isEmpty())
                        {
                            sender.sendMessage("§8- §cNo tracks found!");
                        }
                    }
                    else
                    {
                        sender.sendMessage(Main.Prefix + "§cUnknown option: \"§e" + args[1] + "§c\". Do §e/ed help §cfor help.");
                    }
                }
                else
                {
                    sender.sendMessage(Main.Prefix + "§cToo few arguments! Do §e/ed help§c for help.");
                }
            }
            else if (args[0].equalsIgnoreCase("line1"))  // If the player typed /basic then do the following...
            {
                if (sender instanceof Player)
                {
                    Player p = (Player) sender;
                    p.sendMessage(Main.Prefix + "§9Editor: §bPosition 1§3 has been set.");
                    Track t = Main.getTrackRegistry().getTrackByWorld(p.getWorld());
                    if(t == null)
                    {
                        p.sendMessage(Main.Prefix + "§cThis world has not been set as a track! §cUse §e/ed track add (Worldname) (Display_Name)§c to set it as one.");
                        return false;
                    }

                    t.getTrackData().set("Raceline.Lap1.X", p.getLocation().getBlockX());
                    t.getTrackData().set("Raceline.Lap1.Y", p.getLocation().getBlockY());
                    t.getTrackData().set("Raceline.Lap1.Z", p.getLocation().getBlockZ());
                    t.saveTrackData();

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
                    Track t = Main.getTrackRegistry().getTrackByWorld(p.getWorld());

                    if (t == null)
                    {
                        p.sendMessage(Main.Prefix + "§cThis world has not been set as a track! §cUse §e/ed track add (Worldname) (Display_Name)§c to set it as one.");
                        return false;
                    }

                    p.sendMessage(Main.Prefix + "§9Editor: §bPosition 2§3 has been set.");
                    t.getTrackData().set("Raceline.Lap2.X", p.getLocation().getBlockX());
                    t.getTrackData().set("Raceline.Lap2.Y", p.getLocation().getBlockY());
                    t.getTrackData().set("Raceline.Lap2.Z", p.getLocation().getBlockZ());
                    t.saveTrackData();

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
                    Track t = Main.getTrackRegistry().getTrackByWorld(p.getWorld());

                    if (t == null)
                    {
                        p.sendMessage(Main.Prefix + "§cThis world has not been set as a track! §cUse §e/ed track add (Worldname) (Display_Name)§c to set it as one.");
                        return false;
                    }

                    if (args.length >= 2)
                    {
                        if (args[1].equalsIgnoreCase("FlareSpawn"))
                        {
                            t.getTrackData().set("Fw.X", p.getLocation().getX());
                            t.getTrackData().set("Fw.Y", p.getLocation().getY());
                            t.getTrackData().set("Fw.Z", p.getLocation().getZ());
                        }
                        else if (args[1].toLowerCase().startsWith("spawn"))
                        {
                            int size;
                            try
                            {
                                size = Integer.parseInt(args[1].substring(5));
                            }
                            catch(NumberFormatException nfe)
                            {
                                p.sendMessage(Main.Prefix + "§4Error: §c\"§e" + args[1].substring(5) + "§c\" is not a number!");
                                p.sendMessage(Main.Prefix + "§6To set spawns, you need to write §e/ed spawn Spawn§a#§e - where §a# §eis a number. Example: To set the spawn for §bPlayer 1§e, you would do: §e/ed spawn Spawn1");
                                return false;
                            }
                            t.getTrackData().set("Spawn" + size + ".X", p.getLocation().getX());
                            t.getTrackData().set("Spawn" + size + ".Y", p.getLocation().getY());
                            t.getTrackData().set("Spawn" + size + ".Z", p.getLocation().getZ());
                        }
                        else
                        {
                            p.sendMessage(Main.Prefix + "§4Error: §6\"§e" + args[1] + "§6\" is not a valid spawnpoint.");
                            return false;
                        }

                        p.sendMessage(Prefix + "§3Spawnpoint for §b" + args[1] + " §3saved.");

                        t.saveTrackData();
                        return true;
                    }
                    else
                    {
                        p.sendMessage(Main.Prefix + "§4Error: §6Specify spawn type! §aExample: §6Spawn1, Spawn2, FlareSpawn");
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
            else if (args[0].equalsIgnoreCase("lobby"))
            {
                if(sender instanceof Player)
                {
                    Player p = (Player) sender;

                    plugin.getConfig().set("Lobby", p.getWorld().getName() + "," + p.getLocation().getX() + "," + p.getLocation().getY() + "," + p.getLocation().getZ() + "," + p.getLocation().getPitch() + "," + p.getLocation().getYaw());
                    plugin.saveConfig();
                    sender.sendMessage(Main.Prefix + "§aLobby has been set.");
                }
                else
                {
                    sender.sendMessage(Main.Prefix + "§cYou must be a player to use this command.");
                }
            }
            else if(args[0].equalsIgnoreCase("itembox") || args[0].equalsIgnoreCase("ib"))
            {
                if(sender instanceof Player)
                {
                    Player p = (Player) sender;
                    Location loc = p.getLocation().getBlock().getLocation().add(0.5, 0, 0.5);
                    ItemBox locb = Main.getItemBoxRegistry().getByLocation(loc);
                    if(locb == null)
                    {
                        ItemBox ib = new ItemBox(loc, (EnderCrystal) loc.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL));
                        Main.getItemBoxRegistry().register(ib, true);
                        p.sendMessage(Main.Prefix + "§dCreated new §bItem Box at §a" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + "§b.");
                    }
                    else
                    {
                        Main.getItemBoxRegistry().deregister(locb, true);
                        ItemBox ib = new ItemBox(loc, (EnderCrystal) loc.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL));
                        Main.getItemBoxRegistry().register(ib, true);
                        p.sendMessage(Main.Prefix + "§dOverwrote old §bItem Box at §a" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + "§b.");
                    }
                }
                else
                {
                    sender.sendMessage(Main.Prefix + "§cYou must be a player to use this command.");
                }
            }
            else if (args[0].equalsIgnoreCase("reload"))
            {
                plugin.reloadConfig();
                Main.getItemBoxRegistry().reloadBoxData();
                for(Track t : Main.getTrackRegistry().getTracks())
                {
                    t.reloadTrackData();
                }

                for(World w : Bukkit.getWorlds())
                {
                    for (ItemBox ib : Main.getItemBoxRegistry().getByWorld(w))
                    {
                        ib.despawn();
                    }
                    for(Entity e : w.getEntities())
                    {
                        if(e instanceof EnderCrystal)
                        {
                            e.remove();
                            plugin.getLogger().info("Removing stray Ender Crystal at " + e.getLocation().getX() + ", " + e.getLocation().getY() + ", " + e.getLocation().getZ() + " in \"" + w.getName() + "\".");
                        }
                    }
                }

                Main.getInstance().setupItemBoxes();

                for (World w : Bukkit.getWorlds())
                {
                    for (ItemBox ib : Main.getItemBoxRegistry().getByWorld(w))
                    {
                        ib.spawn(false);
                    }
                }
                sender.sendMessage(Main.Prefix + "§aAll configs were reloaded.");
            }
            else
            {
                sender.sendMessage(Prefix + "§a§l/ed §b§lCommand Usages:");
                sender.sendMessage("§8- §7/ed line1 §8- §fSets the first corner of the raceline.");
                sender.sendMessage("§8- §7/ed line2 §8- §fSets the second corner of the raceline.");
                sender.sendMessage("§8- §7/ed spawn Spawn[1 or 2 or 3 ... etc] §8- §fExample: /ed spawn Spawn4");
                sender.sendMessage("§8- §7/ed spawn FlareSpawn §8- §fSets where the firework will shoot from.");
                sender.sendMessage("§8- §7/ed edit [number] §8- §fEnables/disables marker edit mode. All signs placed are markers.");
                sender.sendMessage("§8- §7/ed (killhorses:kh) §8- §fKills all horses.");
                sender.sendMessage("§8- §7/ed track add (world) (display_name) §8- §fAdds a track and its display name (use _s instead of spaces)");
                sender.sendMessage("§8- §7/ed track del (world) §8- §fRemoves track data from a world.");
                sender.sendMessage("§8- §7/ed track list §8- §fLists all tracks and their asociated world.");
                sender.sendMessage("§8- §7/ed (itembox:ib) §8- §fCreates an item box at your location.");
                sender.sendMessage("§8- §7/ed reload §8- §fReloads this plugin's configs.");
            }
        }
        else
        {
            sender.sendMessage(Main.Prefix + "§4Error: §6Too few arguments! Do §a/ed help §6for help.");
        }
        return false;
    }
}
