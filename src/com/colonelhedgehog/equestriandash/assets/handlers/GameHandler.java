package com.colonelhedgehog.equestriandash.assets.handlers;

import com.colonelhedgehog.equestriandash.api.entity.Racer;
import com.colonelhedgehog.equestriandash.api.event.EDRaceBeginEvent;
import com.colonelhedgehog.equestriandash.api.event.EDRaceEndEvent;
import com.colonelhedgehog.equestriandash.api.event.EDRacerLapEvent;
import com.colonelhedgehog.equestriandash.api.powerup.itembox.ItemBox;
import com.colonelhedgehog.equestriandash.api.track.Track;
import com.colonelhedgehog.equestriandash.assets.Ranking;
import com.colonelhedgehog.equestriandash.assets.tasks.MovementTask;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import com.colonelhedgehog.equestriandash.events.PlayerJoinListener;
import com.colonelhedgehog.equestriandash.utilities.Formatting;
import com.colonelhedgehog.equestriandash.utilities.ValueComparatorTrack;
import io.puharesource.mc.titlemanager.api.TitleObject;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

import java.util.*;

/**
 * Created by ColonelHedgehog on 11/15/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class GameHandler
{
    private GameState gameState;
    private Track track;
    private EquestrianDash plugin = EquestrianDash.getInstance();
    public ArrayList<Racer> finished = new ArrayList<>();

    public void setState(GameState gameState)
    {
        this.gameState = gameState;
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public Track getCurrentTrack()
    {
        return track;
    }

    public void setCurrentTrack(Track track)
    {
        this.track = track;

        plugin.getTrackRegistry().getTracks().stream().filter(others -> !others.equals(track)).forEach(others -> {
            Bukkit.unloadWorld(others.getWorld(), false);
            Bukkit.getLogger().info("Unloading world \"" + track.getWorld().getName() + "\".");
        });


        String[] s = EquestrianDash.plugin.getConfig().getString("Lobby").split(",");

        Bukkit.unloadWorld(Bukkit.getWorld(s[0]), false);
        plugin.buildRaceline();
        Bukkit.getLogger().info("Unloading world \"" + s[0] + "\".");

        int i = 0;
        for (ItemBox ib : track.getItemBoxes())
        {
            ib.getLocation().getChunk().load(true);
            ib.spawn(false);
            i++;
        }

        EquestrianDash.getInstance().getLogger().info(i + " Item Box" + (i == 1 ? "" : "es") + " were spawned on track: \"" + track.getDisplayName() + "\".");

        //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ed reload");
    }

    BukkitTask task = null;

    public void startGame()
    {
        if(getGameState() != GameState.WAITING_FOR_PLAYERS || task != null)
        {
            plugin.getLogger().info("Could not perform startGame() - Game has already started!");
            return;
        }

        Bukkit.broadcastMessage(EquestrianDash.Prefix + "§bEnough players have been gathered to start the game!");

        RacerHandler racerHandler = plugin.getRacerHandler();

        for (Player o : racerHandler.getPlayers())
        {
            o.playSound(o.getLocation(), Sound.ORB_PICKUP, 3, 2);
        }

        task = new BukkitRunnable()
        {
            int count = plugin.getConfig().getInt("Countdown.CountTo");
            final int minplayers = plugin.getConfig().getInt("Players.MinPlayers");

            @Override
            public void run()
            {
                count--;

                if (count > 0 && racerHandler.getPlayers().size() >= minplayers)
                {
                    if (plugin.getConfig().getIntegerList("Countdown.WarnAt").contains(count))
                    {
                        String suf = "s";

                        if (count == 1)
                        {
                            suf = "";
                        }

                        Bukkit.broadcastMessage(EquestrianDash.Prefix + "§eVoting ends in §a" + count + " second" + suf + "§e.");
                        for (Player o : racerHandler.getPlayers())
                        {
                            o.playSound(o.getLocation(), Sound.ORB_PICKUP, 3, 2);
                        }
                    }
                }
                else if (racerHandler.getPlayers().size() >= minplayers)
                {
                    cancel();
                    Track chosen = null;
                    if (!plugin.getConfig().getBoolean("Countdown.RandomPick") && !plugin.getVoteBoard().getVotes().isEmpty() && plugin.getTrackRegistry().getTracks().size() > 1)
                    {
                        ValueComparatorTrack bvc = new ValueComparatorTrack(plugin.getVoteBoard().getVotes());
                        TreeMap<Track, Integer> sorted_map = new TreeMap<>(bvc);
                        sorted_map.putAll(plugin.getVoteBoard().getVotes());
                        //sorted_map.get(0);

                        for (Map.Entry<Track, Integer> entry : sorted_map.entrySet())
                        {
                            if (chosen == null)
                            {
                                chosen = entry.getKey();
                            }
                        }
                    }
                    else
                    {
                        List<Track> tracks = plugin.getTrackRegistry().getTracks();
                        try
                        {
                            chosen = tracks.get(new Random().nextInt(tracks.size()));
                        }
                        catch (IllegalArgumentException iae)
                        {
                            // iae looks kinda like "bae."
                        }
                    }

                    if (chosen == null)
                    {
                        Bukkit.broadcastMessage("§cInternal Error: No tracks could be selected as no tracks exist! EVERYBODY PANIC! (And tell your server owner)");
                        plugin.getLogger().severe("BAD, BAD, BAD! SHAME ON YOU! No tracks were registered so no tracks could load. :(");
                        return;
                    }

                    Bukkit.broadcastMessage(EquestrianDash.Prefix + "§aStarting race on map: \"§b" + chosen.getDisplayName() + "§a\"");
                    plugin.getGameHandler().setCurrentTrack(chosen);

                    int c = 1;

                    FileConfiguration data = chosen.getTrackData();
                    PropertyHandler propertyHandler = plugin.getPropertyHandler();
                    MovementTask movementTask = plugin.getMovementTask();
                    //movementTask.runTaskTimer(plugin, 10, 10);

                    int fx = plugin.getGameHandler().getCurrentTrack().getTrackData().getInt("Fw.X");
                    int fy = plugin.getGameHandler().getCurrentTrack().getTrackData().getInt("Fw.Y");
                    int fz = plugin.getGameHandler().getCurrentTrack().getTrackData().getInt("Fw.Z");
                    Location flareLoc = new Location(plugin.getGameHandler().getCurrentTrack().getWorld(), fx, fy, fz);

                    Bukkit.getOnlinePlayers().stream().filter((player) -> player.getGameMode() == GameMode.SPECTATOR).forEach((player) -> player.teleport(flareLoc));
                    List<Player> moved = new ArrayList<>();
                    for (Player on : racerHandler.getPlayers())
                    {
                        if (data.contains("Spawn" + c))
                        {
                            Location go = new Location(chosen.getWorld(), data.getDouble("Spawn" + c + ".X"), data.getDouble("Spawn" + c + ".Y"), data.getDouble("Spawn" + c + ".Z"));
                            on.teleport(go);
                            movementTask.players.put(on.getUniqueId(), go);
                            moved.add(on);
                        }
                        else
                        {
                            on.kickPlayer(EquestrianDash.Prefix + "§cData mismatch!\n The player count higher than the amount of listed spawns.");
                            plugin.getLogger().severe("ERROR: You didn't list enough spawns to meet the amount of players allowed!");
                        }

                        on.getInventory().clear();

                        c++;
                    }

                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            for(Player on : moved)
                            {
                                Horse horse = propertyHandler.generateHorse(on, movementTask.players.get(on.getUniqueId()));
                                propertyHandler.disableMovement(on, horse);
                            }

                            plugin.reloadConfigs();
                        }
                    }.runTaskLater(plugin, 1);
                    startCounter();
                }
                else
                {
                    cancel();
                    Bukkit.broadcastMessage(EquestrianDash.Prefix + "§cToo many players have left.");
                    setState(GameState.WAITING_FOR_PLAYERS);
                    task = null;
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }


    private void startCounter()
    {
        final int[] count = new int[1];

        plugin.setLocationTask(plugin.getLocationHandler().generateTask(track.getTrackData().getInt("Schedulers.LocationTask.TicksPerCheck")));

        final PropertyHandler propertyHandler = plugin.getPropertyHandler();

        final ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        final Objective o = board.registerNewObjective("test", "edcountdown");
        o.setDisplayName("§9§lPre-Race");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score countScore = o.getScore("§bStarting in...");

        final int countTo = plugin.getConfig().getInt("Countdown.CountTo");
        count[0] = countTo;
        plugin.getGameHandler().setState(GameHandler.GameState.COUNT_DOWN_TO_START);

        Bukkit.broadcastMessage(EquestrianDash.Prefix + "§3Game starting in §b" + countTo + " §3seconds...");

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                RacerHandler racerHandler = plugin.getRacerHandler();

                if (racerHandler.getPlayers().size() < plugin.getConfig().getInt("Players.MinPlayers"))
                {
                    cancel();
                    plugin.getServer().broadcastMessage(EquestrianDash.Prefix + "§6§oToo many players have left.");
                    count[0] = countTo;
                }

                if (count[0] == 1)
                {
                    cancel();

                    //Start game method
                    int fx = plugin.getGameHandler().getCurrentTrack().getTrackData().getInt("Fw.X");
                    int fy = plugin.getGameHandler().getCurrentTrack().getTrackData().getInt("Fw.Y");
                    int fz = plugin.getGameHandler().getCurrentTrack().getTrackData().getInt("Fw.Z");
                    Location flareloc = new Location(plugin.getGameHandler().getCurrentTrack().getWorld(), fx, fy, fz);

                    Firework fw = track.getWorld().spawn(flareloc, Firework.class);
                    FireworkMeta data = fw.getFireworkMeta();
                    //p.sendMessage(EquestrianDash.Prefix + "Firework spawned at " + flareloc);
                    data.addEffects(FireworkEffect.builder().withColor(Color.GREEN).with(FireworkEffect.Type.BALL_LARGE).build());
                    data.setPower(1);
                    fw.setFireworkMeta(data);
                    plugin.getGameHandler().setState(GameHandler.GameState.RACE_IN_PROGRESS);
                    Bukkit.broadcastMessage(EquestrianDash.Prefix + "§a§l§oGO!");

                    if(EquestrianDash.tm)
                    {
                        TitleObject obj = new TitleObject("§a§lGO!", TitleObject.TitleType.TITLE).setStay(40).setFadeIn(0).setFadeOut(40);

                        racerHandler.racers.forEach((racer) -> obj.send(racer.getPlayer()));
                    }

                    EDRaceBeginEvent callable = new EDRaceBeginEvent();
                    Bukkit.getPluginManager().callEvent(callable);

                    board.clearSlot(DisplaySlot.SIDEBAR);

                    for (Player online : Bukkit.getServer().getOnlinePlayers())
                    {
                        online.getInventory().setArmorContents(new ItemStack[]{});

                        if (online.getVehicle() != null)
                        {
                            propertyHandler.enableMovement(online);
                            plugin.getRacerHandler().lastLocation.put(online.getUniqueId(), online.getLocation());
                        }

                        online.playSound(online.getLocation(), Sound.NOTE_PLING, 3, 2);
                        online.getInventory().clear();
                        online.closeInventory();
                    }
                }
                else
                {
                    for (Player o : racerHandler.getPlayers())
                    {
                        o.setScoreboard(board);
                    }

                    count[0] = (count[0] - 1);
                    countScore.setScore(count[0]);
                    //Bukkit.getServer().broadcastMessage(Prefix + "§9Race begins in: §b" + count);
                    if (count[0] <= 3 && count[0] != 0)
                    {
                        if(EquestrianDash.tm)
                        {
                            String pref = count[0] == 3 ? "§c" : count[0] == 2 ? "§6" : "§e";
                            TitleObject obj = new TitleObject(pref + "§l" + count[0], TitleObject.TitleType.TITLE).setStay(0).setFadeIn(0).setFadeOut(20);
                            racerHandler.racers.forEach((racer) -> obj.send(racer.getPlayer()));
                        }

                        for (Player onl : plugin.getServer().getOnlinePlayers())
                        {
                            onl.playSound(onl.getLocation(), Sound.NOTE_PLING, 3, 1);
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);
    }

    public void endGame(boolean force)
    {
        if (finished.size() == plugin.getConfig().getInt("RaceOver.EndAfter") || force)
        {
            Bukkit.broadcastMessage(EquestrianDash.Prefix + "§aThe race has ended! §bAll required players have finished.");
            LinkedHashMap<UUID, Integer> arranged = Ranking.getPlayersArranged();
            ArrayList<Racer> haveNotFinished = new ArrayList<>();
            LinkedHashMap<Racer, Integer> actualSorted = new LinkedHashMap<>();

            for (Map.Entry<UUID, Integer> entry : arranged.entrySet())
            {
                Racer racer = plugin.getRacerHandler().getRacer(Bukkit.getPlayer(entry.getKey()));
                if(finished.contains(racer))
                {
                    continue;
                }
                haveNotFinished.add(racer);
            }

            int index = 1;
            for (Racer racer : finished)
            {
                actualSorted.put(racer, index);
                index++;
            }

            for (Racer racer : haveNotFinished)
            {
                actualSorted.put(racer, index);
                index++;
            }


            Bukkit.broadcastMessage("§9§l====================");

            boolean found = false;
            for (Map.Entry<Racer, Integer> entry : actualSorted.entrySet())
            {
                Racer racer = entry.getKey();

                if(!finished.contains(racer))
                {
                    if (!found)
                    {
                        Bukkit.broadcastMessage("§7§l====================");
                        found = true;
                    }
                }

                int rank = entry.getValue();
                Bukkit.broadcastMessage(Formatting.getPlaceColor(rank) + "§l" + rank + Formatting.getPlaceSuffix(rank) + " §8> " + Formatting.getPlaceColor(rank) + racer.getPlayer().getName());
            }

            Bukkit.broadcastMessage("§9§l====================");

        }

        EDRaceEndEvent callable = new EDRaceEndEvent(finished);
        Bukkit.getPluginManager().callEvent(callable);

        if (plugin.getConfig().getBoolean("RaceOver.Restart.Enabled"))
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    RacerHandler racerHandler = plugin.getRacerHandler();

                    for (Player on : racerHandler.getPlayers())
                    {
                        on.kickPlayer(EquestrianDash.Prefix + "§cThe game has ended.");
                    }

                    restart();
                }
            }.runTaskLater(plugin, plugin.getConfig().getLong("RaceOver.Restart.Delay"));
        }
        plugin.getGameHandler().setState(GameHandler.GameState.RACE_ENDED);
    }

    private void restart()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
            }
        }.runTaskLater(plugin, 10);
    }

    public ArrayList<Racer> getFinished()
    {
        return finished;
    }

    public void doLap(Player p)
    {
        Entity v = p.getVehicle();
        RacerHandler racerHandler = plugin.getRacerHandler();

        v.setVelocity(new org.bukkit.util.Vector(0, 0, 1.5));
        p.setVelocity(new org.bukkit.util.Vector(0, 0, 1.5));
        int lap = p.getMetadata("playerLap").get(0).asInt() + 1;
        p.setMetadata("playerLap", new FixedMetadataValue(plugin, lap));

        if (lap == 1)
        {
            p.setMetadata("playerInLine", new FixedMetadataValue(plugin, true));
            return;
        }
        Location fwloc = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
        Firework fw = p.getWorld().spawn(fwloc, Firework.class);
        FireworkMeta data = fw.getFireworkMeta();
        data.addEffects(FireworkEffect.builder().withColor(Color.BLACK, Color.WHITE).with(FireworkEffect.Type.STAR).build());
        data.setPower(0);
        fw.setFireworkMeta(data);
        fwloc.getWorld().playSound(fwloc, Sound.SUCCESSFUL_HIT, 3, 1);

        EDRacerLapEvent callable2 = new EDRacerLapEvent(racerHandler.getRacer(p), lap);
        Bukkit.getPluginManager().callEvent(callable2);

        if (lap < plugin.getGameHandler().getCurrentTrack().getTrackData().getInt("Laps"))
        {
            p.sendMessage(PlayerJoinListener.Prefix + "§9§lLAP §a§l" + (lap) + "§9§l!");
            if (EquestrianDash.tm)
            {
                new TitleObject("§9§lLAP §a§l" + (lap) + "§9§l!", "").setStay(60).setFadeIn(10).setFadeOut(10).send(p);
            }
            messageExcept(p, PlayerJoinListener.Prefix + "§b" + p.getName() + " §9is on §aLap " + (lap));
        }
        else if (lap == plugin.getGameHandler().getCurrentTrack().getTrackData().getInt("Laps"))
        {
            p.sendMessage(PlayerJoinListener.Prefix + "§8§l§n§oF§f§l§n§oI§8§l§n§oN§f§l§n§oA§8§l§n§oL §f§l§n§oL§8§l§n§oA§f§l§n§oP§8§l§n§o!");
            if (EquestrianDash.tm)
            {
                new TitleObject("§8§l§n§oF§f§l§n§oI§8§l§n§oN§f§l§n§oA§8§l§n§oL §f§l§n§oL§8§l§n§oA§f§l§n§oP§8§l§n§o!", "").setStay(60).setFadeIn(10).setFadeOut(10).send(p);
            }
            messageExcept(p, PlayerJoinListener.Prefix + "§b" + p.getName() + " §9is on his §cFINAL LAP!");
        }
        else if (lap > plugin.getGameHandler().getCurrentTrack().getTrackData().getInt("Laps"))
        {
            finishPlayer(p);
        }

        p.setMetadata("playerInLine", new FixedMetadataValue(plugin, true));
    }

    public void finishPlayer(Player p)
    {
        p.setMetadata("finished", new FixedMetadataValue(plugin, true));
        Ranking.scores.put(p.getUniqueId(), 999999999 - finished.size());
        plugin.getPositionTask().setPlace(p.getUniqueId(), 999999999 - finished.size());

        //plugin.getLogger().info("Ranking.Scores: " + Ranking.scores.get(p.getUniqueId()));
        //plugin.getLogger().info("posTaskPlace: " + plugin.getPositionTask().getPlace(p.getUniqueId()));

        finished.add(plugin.getRacerHandler().getRacer(p));

        String det = finished.size() + Formatting.getPlaceSuffix(finished.size());
        messageExcept(p, PlayerJoinListener.Prefix + "§b§l" + p.getName().toUpperCase() + " §ehas finished in §d§l" + det.toUpperCase() + "§e!");
        p.sendMessage(PlayerJoinListener.Prefix + "§c§oYou've finished §d§l" + det.toUpperCase() + "§c§o!");

        if (EquestrianDash.tm)
        {
            new TitleObject("§c§lFINISHED!", "§b§lYou've finished in §a§l" + det + "!").setStay(60).setFadeIn(10).setFadeOut(10).send(p);
        }

        if (finished.size() == plugin.getConfig().getInt("RaceOver.EndAfter"))
        {
            endGame(false);
            plugin.getLocationTask().cancel();
            plugin.getPositionTask().cancel();
        }
    }
    public void messageExcept(Player p, String Message)
    {
        plugin.getServer().getOnlinePlayers().stream().filter(on -> !on.getName().equalsIgnoreCase(p.getName())).forEach(on -> {
            on.sendMessage(Message);
        });
    }

    public enum GameState
    {
        WAITING_FOR_PLAYERS, RACE_ENDED, RACE_IN_PROGRESS, COUNT_DOWN_TO_START
    }
}
