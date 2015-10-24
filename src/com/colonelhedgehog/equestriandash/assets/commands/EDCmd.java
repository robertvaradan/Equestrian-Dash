package com.colonelhedgehog.equestriandash.assets.commands;

import com.colonelhedgehog.equestriandash.api.powerup.itembox.ItemBox;
import com.colonelhedgehog.equestriandash.api.powerup.itembox.ItemBoxRegistry;
import com.colonelhedgehog.equestriandash.api.track.Marker;
import com.colonelhedgehog.equestriandash.api.track.MarkerHandler;
import com.colonelhedgehog.equestriandash.api.track.Track;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import com.colonelhedgehog.equestriandash.events.WorldLoadListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.util.List;
import java.util.Random;

import static com.colonelhedgehog.equestriandash.events.PlayerJoinListener.Prefix;

/**
 * Created by ColonelHedgehog on 11/7/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class EDCmd implements CommandExecutor
{
    private EquestrianDash plugin = EquestrianDash.getInstance();
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
                            if (plugin.getTrackRegistry().getTrackByID(args[2]) == null)
                            {
                                World w = Bukkit.getWorld(args[2]);
                                if(w != null)
                                {
                                    if(args.length > 3)
                                    {
                                        Track track = new Track(w);
                                        track.initialize(args[3]);
                                        track.initializeItemBoxData();
                                        plugin.getTrackRegistry().registerTrack(track);
                                    }
                                    else
                                    {
                                        Track track = new Track(w);
                                        track.initialize(w.getName());
                                        track.initializeItemBoxData();
                                        plugin.getTrackRegistry().registerTrack(track);
                                    }
                                    sender.sendMessage(EquestrianDash.Prefix + "§aTrack added!");
                                }
                                else
                                {
                                    sender.sendMessage(EquestrianDash.Prefix + "§6The specified world doesn't exist!");
                                }
                            }
                            else
                            {
                                sender.sendMessage(EquestrianDash.Prefix + "§6This world has already been registered as a track.");
                            }
                        }
                        else
                        {
                            sender.sendMessage(EquestrianDash.Prefix + "§4Error: §cToo few arguments! Do §e/ed help §cfor help.");
                        }
                    }
                    else if(args[1].equalsIgnoreCase("del"))
                    {
                        if(args.length > 2)
                        {
                            Track t = plugin.getTrackRegistry().getTrackByID(args[2]);
                            File f = new File(plugin.getDataFolder() + "/Tracks/" + t.getWorld().getName());
                            boolean done = f.delete() || (plugin.getTrackRegistry().getTrackByID(args[2]) != null); // cri cri
                            String trackname = t.getDisplayName();
                            plugin.getTrackRegistry().deregisterTrack(t);
                            if(done)
                            {
                                EquestrianDash.plugin.getLogger().info("Deleted a track named " + trackname + " for world " + t.getWorld() + ".");
                                sender.sendMessage(EquestrianDash.Prefix + "§aWorld \"§e" + t.getWorld().getName() + "§a\" is no longer a track.");
                            }
                            else
                            {
                                sender.sendMessage(EquestrianDash.Prefix + "§cCouldn't delete Track! Does it exist?");
                            }
                        }
                        else
                        {
                            sender.sendMessage(EquestrianDash.Prefix + "§cToo few arguments! Do §e/ed help §cfor help.");
                        }
                    }
                    else if(args[1].equalsIgnoreCase("list"))
                    {
                        sender.sendMessage(EquestrianDash.Prefix + "§b§lAll registered tracks:");
                        List<Track> tracks = plugin.getTrackRegistry().getTracks();
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
                        sender.sendMessage(EquestrianDash.Prefix + "§cUnknown option: \"§e" + args[1] + "§c\". Do §e/ed help §cfor help.");
                    }
                }
                else
                {
                    sender.sendMessage(EquestrianDash.Prefix + "§cToo few arguments! Do §e/ed help§c for help.");
                }
            }
            else if (args[0].equalsIgnoreCase("line1"))
            {
                if (sender instanceof Player)
                {
                    Player p = (Player) sender;
                    p.sendMessage(EquestrianDash.Prefix + "§9Editor: §bPosition 1§3 has been set.");
                    Track t = plugin.getTrackRegistry().getTrackByWorld(p.getWorld());
                    if(t == null)
                    {
                        p.sendMessage(EquestrianDash.Prefix + "§cThis world has not been set as a track! §cUse §e/ed track add (Worldname) (Display_Name)§c to set it as one.");
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
                    sender.sendMessage(EquestrianDash.Prefix + "§4Error: §6You must be a player to perform this command.");
                    return false;
                }
            }
            else if (args[0].equalsIgnoreCase("line2"))
            {
                if (sender instanceof Player)
                {
                    Player p = (Player) sender;
                    p.sendMessage(EquestrianDash.Prefix + "§9Editor: §bPosition 2§3 has been set.");
                    Track t = plugin.getTrackRegistry().getTrackByWorld(p.getWorld());

                    if (t == null)
                    {
                        p.sendMessage(EquestrianDash.Prefix + "§cThis world has not been set as a track! §cUse §e/ed track add (Worldname) (Display_Name)§c to set it as one.");
                        return false;
                    }

                    p.sendMessage(EquestrianDash.Prefix + "§9Editor: §bPosition 2§3 has been set.");
                    t.getTrackData().set("Raceline.Lap2.X", p.getLocation().getBlockX());
                    t.getTrackData().set("Raceline.Lap2.Y", p.getLocation().getBlockY());
                    t.getTrackData().set("Raceline.Lap2.Z", p.getLocation().getBlockZ());
                    t.saveTrackData();

                    return true;
                }
                else
                {
                    sender.sendMessage(EquestrianDash.Prefix + "§4Error: §6You must be a player to perform this command.");
                    return false;
                }
            }
            else if (args[0].equalsIgnoreCase("spawn"))
            {
                if (sender instanceof Player)
                {
                    Player p = (Player) sender;
                    Track t = plugin.getTrackRegistry().getTrackByWorld(p.getWorld());

                    if (t == null)
                    {
                        p.sendMessage(EquestrianDash.Prefix + "§cThis world has not been set as a track! §cUse §e/ed track add (Worldname) (Display_Name)§c to set it as one.");
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
                                p.sendMessage(EquestrianDash.Prefix + "§4Error: §c\"§e" + args[1].substring(5) + "§c\" is not a number!");
                                p.sendMessage(EquestrianDash.Prefix + "§6To set spawns, you need to write §e/ed spawn Spawn§a#§e - where §a# §eis a number. Example: To set the spawn for §bPlayer 1§e, you would do: §e/ed spawn Spawn1");
                                return false;
                            }
                            t.getTrackData().set("Spawn" + size + ".X", p.getLocation().getX());
                            t.getTrackData().set("Spawn" + size + ".Y", p.getLocation().getY());
                            t.getTrackData().set("Spawn" + size + ".Z", p.getLocation().getZ());
                        }
                        else
                        {
                            p.sendMessage(EquestrianDash.Prefix + "§4Error: §6\"§e" + args[1] + "§6\" is not a valid spawnpoint.");
                            return false;
                        }

                        p.sendMessage(Prefix + "§3Spawnpoint for §b" + args[1] + " §3saved.");

                        t.saveTrackData();
                        return true;
                    }
                    else
                    {
                        p.sendMessage(EquestrianDash.Prefix + "§4Error: §6Specify spawn type! §aExample: §6Spawn1, Spawn2, FlareSpawn");
                        return false;
                    }
                }
                else
                {
                    sender.sendMessage(EquestrianDash.Prefix + "§4You must be a player!");
                    return false;
                }
            }
            else if(args[0].equalsIgnoreCase("marker"))
            {
                if (!(sender instanceof Player))
                {
                    sender.sendMessage(EquestrianDash.Prefix + "§cYou can only use edit mode as a player.");
                    return false;
                }

                Player p = (Player) sender;


                Track track = plugin.getTrackRegistry().getTrackByWorld(p.getWorld());

                if(track == null)
                {
                    p.sendMessage(EquestrianDash.Prefix + "§4Error: §cYou're not in a track world!");
                    return false;
                }

                if(args.length <= 1)
                {
                    p.sendMessage(EquestrianDash.Prefix + "§4Error: §cToo few arguments! Try §e/ed help");
                    return false;
                }

                if(args[1].equalsIgnoreCase("edit"))
                {

                    int number;
                    if (args.length >= 3)
                    {
                        try
                        {
                            number = Integer.parseInt(args[2]);
                            p.setMetadata("editorEnabled", new FixedMetadataValue(plugin, true));
                            p.setMetadata("editorNumber", new FixedMetadataValue(plugin, number));
                            p.sendMessage(EquestrianDash.Prefix + "§dEdit mode updated. Marker number: §5" + number);
                            return true;
                        }
                        catch (NumberFormatException nfe)
                        {
                            p.sendMessage(EquestrianDash.Prefix + "§e" + args[2] + "§c is not a number.");
                            return false;
                        }
                    }
                    else
                    {
                        if (p.hasMetadata("editorEnabled") && p.getMetadata("editorEnabled").get(0).asBoolean())
                        {
                            p.setMetadata("editorEnabled", new FixedMetadataValue(plugin, false));
                            p.sendMessage(EquestrianDash.Prefix + "§eMarker editor disabled.");
                        }
                        else
                        {
                            p.setMetadata("editorEnabled", new FixedMetadataValue(plugin, true));
                            p.setMetadata("editorNumber", new FixedMetadataValue(plugin, 0));
                            p.sendMessage(EquestrianDash.Prefix + "§dMarker editor enabled.");
                        }
                    }
                }
                else if(args[1].equalsIgnoreCase("list"))
                {
                    MarkerHandler markerHandler = track.getMarkerHandler();

                    if(args.length >= 3)
                    {
                        int time;
                        try
                        {
                            time = (int) (Double.parseDouble(args[2]) * 20);
                        }
                        catch(NumberFormatException nfe)
                        {
                            p.sendMessage(EquestrianDash.Prefix + "§4Error: §e" + args[2] + " §cisn't a number.");
                            return false;
                        }

                        if(markerHandler.getVisualized())
                        {
                            p.sendMessage(EquestrianDash.Prefix + "§4Error: §cAlready visualized! Just use §e/ed marker list§c to turn off visualizing.");
                            return false;
                        }
                        markerHandler.setVisualized(true, time);
                        int stime = time/20;
                        p.sendMessage(EquestrianDash.Prefix + "§aVisualizing for §9" + stime + " §asecond" + (stime == 1 ? "" : "s") + "...");
                    }
                    else
                    {
                        boolean vis = markerHandler.getVisualized();
                        vis ^= true;

                        p.sendMessage(EquestrianDash.Prefix + "§aVisualizing set to §9" + vis);
                        markerHandler.setVisualized(vis);
                    }
                }
                else if(args[1].equalsIgnoreCase("teleport") || args[1].equalsIgnoreCase("tp"))
                {
                    int index = 0;
                    int number;

                    if(args.length > 2)
                    {
                        try
                        {
                            number = Integer.parseInt(args[2]);
                        }
                        catch (NumberFormatException nfe)
                        {
                            p.sendMessage(EquestrianDash.Prefix + "§4Error: §cInvalid marker §enumber §cspecified!");
                            return false;
                        }
                    }
                    else
                    {
                        p.sendMessage(EquestrianDash.Prefix + "§4Error: §cToo few arguments!");
                        return false;
                    }

                    if(args.length > 3)
                    {
                        try
                        {
                            index = Integer.parseInt(args[3]) - 1;
                        }
                        catch (NumberFormatException nfe)
                        {
                            p.sendMessage(EquestrianDash.Prefix + "§4Error: §cInvalid marker §eindex §cspecified!");
                            return false;
                        }
                    }

                    List<Marker> markers = track.getMarkerHandler().getMarkersByNumber(number);

                    if(markers.size() <= index || index < 0)
                    {
                        p.sendMessage(EquestrianDash.Prefix + "§4Error: §cCouldn't find a marker with a number of§e" + number + "§c and index of §e" + index + "§c!");
                        return false;
                    }

                    Marker marker = markers.get(index);

                    p.sendMessage(EquestrianDash.Prefix + "§aTeleporting to marker §e" + number + "§a, index §e" + (index + 1) + "§a of §e" + markers.size());
                    marker.setVisualized(true, 40);
                    p.teleport(marker.getLocation());
                }
                else if(args[1].equalsIgnoreCase("delete"))
                {
                    if(args.length <= 2)
                    {
                        int old = new Random().nextInt(50);
                        p.sendMessage(EquestrianDash.Prefix + "§4Error: §cToo few arguments! You need to specify a number or range. §6Example: §e" + new Random().nextInt(100) + "§6, or §e" + old + "-" + (old + new Random().nextInt(50) + 1));
                        return false;
                    }

                    boolean range = false;
                    int number = 0;
                    if(args[2].matches("^\\d+-\\d+$"))
                    {
                        range = true;
                    }
                    else
                    {
                        try
                        {
                            number = Integer.parseInt(args[2]);
                        }
                        catch(NumberFormatException nfe)
                        {
                            int old = new Random().nextInt(50);
                            p.sendMessage(EquestrianDash.Prefix + "§4Error: §cThe number specified is not a number or a range! §6Example: §e" + new Random().nextInt(100) + "§6, or §e" + old + "-" + (old + new Random().nextInt(50) + 1));
                            return false;
                        }
                    }

                    if(range)
                    {
                        int start;
                        int end;

                        String[] split = args[2].split("-");

                        start = Integer.parseInt(split[0]);
                        end = Integer.parseInt(split[1]);

                        if(start >= end)
                        {
                            p.sendMessage(EquestrianDash.Prefix + "§4Error: §cInvalid range specified! First number must be smaller than last.");
                            return false;
                        }


                        List<Marker> found = track.getMarkerHandler().getMarkersInRange(start, end);
                        found.forEach(Marker::delete);

                        p.sendMessage(EquestrianDash.Prefix + "§aDeleted §e" + found.size() + " §amarker" + (found.size() == 1 ? "" : "s") + " within range of §e" + start + " §ato §e" + end + "§a.");
                    }
                    else
                    {
                        List<Marker> found = track.getMarkerHandler().getMarkersByNumber(number);
                        found.forEach(Marker::delete);
                        p.sendMessage(EquestrianDash.Prefix + "§aDeleted §e" + found.size() + " §amarker" + (found.size() == 1 ? "" : "s") + " with §e" + number + " §aas a number.");
                    }
                }
                else
                {
                    p.sendMessage(EquestrianDash.Prefix + "§4Error: §cUnknown argument §c\"§e" + args[1] + "§c\"!");
                }
            }
            else if (args[0].equalsIgnoreCase("killhorses") || args[0].equalsIgnoreCase("kh"))
            {
                plugin.getServer().getWorlds().forEach(WorldLoadListener::killallHorses);

                sender.sendMessage(EquestrianDash.Prefix + "§cKilled all horses!");
            }
            else if (args[0].equalsIgnoreCase("lobby"))
            {
                if(sender instanceof Player)
                {
                    Player p = (Player) sender;

                    plugin.getConfig().set("Lobby", p.getWorld().getName() + "," + p.getLocation().getX() + "," + p.getLocation().getY() + "," + p.getLocation().getZ() + "," + p.getLocation().getPitch() + "," + p.getLocation().getYaw());
                    plugin.saveConfig();
                    sender.sendMessage(EquestrianDash.Prefix + "§aLobby has been set.");
                }
                else
                {
                    sender.sendMessage(EquestrianDash.Prefix + "§cYou must be a player to use this command.");
                }
            }
            else if(args[0].equalsIgnoreCase("itembox") || args[0].equalsIgnoreCase("ib"))
            {
                if(sender instanceof Player)
                {
                    Player p = (Player) sender;
                    Location loc = p.getLocation().getBlock().getLocation().add(0.5, 0, 0.5);
                    ItemBox locb = plugin.getItemBoxRegistry().getByLocation(loc);
                    ItemBoxRegistry itemBoxRegistry = plugin.getItemBoxRegistry();

                    if(locb == null)
                    {
                        ItemBox ib = new ItemBox(loc, (EnderCrystal) loc.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL));
                        itemBoxRegistry.register(true, ib);
                        p.sendMessage(EquestrianDash.Prefix + "§dCreated new §bItem Box at §a" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + "§b.");
                    }
                    else
                    {
                        plugin.getItemBoxRegistry().deregister(true, locb);
                        locb.despawn();
                        ItemBox ib = new ItemBox(loc, (EnderCrystal) loc.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL));
                        itemBoxRegistry.register(true, ib);
                        p.sendMessage(EquestrianDash.Prefix + "§dOverwrote old §bItem Box at §a" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + "§b.");
                    }
                }
                else
                {
                    sender.sendMessage(EquestrianDash.Prefix + "§cYou must be a player to use this command.");
                }
            }
            else if (args[0].equalsIgnoreCase("reload"))
            {
                plugin.reloadConfigs();
                sender.sendMessage(EquestrianDash.Prefix + "§aAll configs were reloaded.");
            }
            else if(args[0].equalsIgnoreCase("help"))
            {
                int page = 1;

                if(args.length > 1)
                {
                    try
                    {
                        page = Integer.parseInt(args[1]);
                    }
                    catch (NumberFormatException nfe)
                    {
                        sender.sendMessage(EquestrianDash.Prefix + "§cInvalid page, \"§e" + args[1] + "§c.\" Redirecting to page §e1§c...");
                        Bukkit.dispatchCommand(sender, "ed help 1");
                        return false;
                    }
                }

                sender.sendMessage(Prefix + "§a§l/ed §b§lCommand Usages §9(Page §e" + (page) + " §9of §e4§9)");

                if(page == 1)
                {
                    sender.sendMessage("§8- §7/ed line1 §8- §fSets the first corner of the raceline.");
                    sender.sendMessage("§8- §7/ed line2 §8- §fSets the second corner of the raceline.");
                    sender.sendMessage("§8- §7/ed spawn Spawn[1 or 2 or 3 ... etc] §8- §fExample: /ed spawn Spawn4");
                    sender.sendMessage("§8- §7/ed spawn FlareSpawn §8- §fSets where the firework will shoot from.");
                    sender.sendMessage("§8- §7/ed marker edit [number] §8- §fEnables/disables marker edit mode using a specified number. All §dbeacons §fplaced are markers.");
                    sender.sendMessage("§8- §7/ed marker edit §8- §fEnables/disables marker edit mode. All §dbeacons §fplaced are markers.");
                }
                else if(page == 2)
                {

                    sender.sendMessage("§8- §7/ed marker list §8- §fEnables/disables visualization of all markers.");
                    sender.sendMessage("§8- §7/ed marker list [time] §8- §fShows all markers for a specific amount of seconds.");
                    sender.sendMessage("§8- §7/ed marker (teleport:tp) [number] {index} §8- §fTeleports you to the marker. Additionally, you can provide an index.");
                    sender.sendMessage("§8- §7/ed marker delete [number or range] §8- §fDeletes all markers within a range (or with the same number).");
                    sender.sendMessage("§8- §7/ed (killhorses:kh) §8- §fKills all horses.");
                    sender.sendMessage("§8- §7/ed track add (world) (display_name) §8- §fAdds a track and its display name (use _s instead of spaces).");
                }
                else if(page == 3)
                {
                    sender.sendMessage("§8- §7/ed track del (world) §8- §fRemoves track data from a world.");
                    sender.sendMessage("§8- §7/ed track list §8- §fLists all tracks and their associated world.");
                    sender.sendMessage("§8- §7/ed (itembox:ib) §8- §fCreates an item box at your location.");
                    sender.sendMessage("§8- §7/ed reload §8- §fReloads this plugin's configs.");
                }
                else
                {
                    sender.sendMessage(EquestrianDash.Prefix + "§cCan't find page §e" + page + "§c. Redirecting to page §e1§c...");
                    Bukkit.dispatchCommand(sender, "ed help 1");
                }
            }
            else
            {
                sender.sendMessage(EquestrianDash.Prefix + "§4Error: §cInvalid command. Showing §e/ed help§c...");
                Bukkit.dispatchCommand(sender, "ed help 1");
            }
        }
        else
        {
            sender.sendMessage(EquestrianDash.Prefix + "§4Error: §6Too few arguments! Do §a/ed help §6for help.");
        }

        return false;
    }
}
