/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

import org.bukkit.*;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import tk.ColonelHedgehog.Dash.API.Entity.Racer;
import tk.ColonelHedgehog.Dash.API.Event.EDRaceBeginEvent;
import tk.ColonelHedgehog.Dash.API.Track.Track;
import tk.ColonelHedgehog.Dash.Assets.GameState;
import tk.ColonelHedgehog.Dash.Assets.Ranking;
import tk.ColonelHedgehog.Dash.Assets.VoteBoard;
import tk.ColonelHedgehog.Dash.Core.Main;
import tk.ColonelHedgehog.Dash.Lib.ValueComparatorTrack;

import java.util.*;

import static org.bukkit.entity.EntityType.HORSE;
import static tk.ColonelHedgehog.Dash.Events.PlayerInteractEntityListener.setName;
import static tk.ColonelHedgehog.Dash.Events.PlayerInteractListener.addLore;

/**
 * @author Robert
 */
public class PlayerJoinListener implements Listener
{
    public static HashMap<Location, UUID> SpawnPoints = new HashMap<>();
    public static Main plugin = Main.plugin;

    public static Objective placeObj = null; // Same as above but it creates a objective called timerObj
    public static Objective pl[]; //Creates a objective called o

    public static String Prefix = "§9§l[§3Equestrian§bDash§9§l]§r: ";
    public static boolean countdownstarted = false;
    public static int tries = 1;
    public static boolean quitexception = false;
    public static boolean racing = false;
    public static ArrayList<Player> racers = new ArrayList<>();
    static int count = 20;
    private Scoreboard board = null;
    private Score scr;

    public static void disableMovement(final Player p/*, Horse horse*/)
    {
        if (!(plugin.getConfig().getBoolean("EditMode")))
        {
            p.setGameMode(GameMode.SURVIVAL);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 999999999));


            final ItemStack it = new ItemStack(Material.SADDLE);
            setName(it, "§9§l§oCustomize §b§l§oColors");
            addLore(it, "§6§oRight click §a§oto customize your horse's colors and style!");

            new BukkitRunnable()
            {

                @Override
                public void run()
                {
                    // .-.
                    p.getInventory().addItem(it);
                    Horse horse = (Horse) p.getWorld().spawnEntity(p.getLocation(), HORSE);
                    horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
                    horse.setVariant(Horse.Variant.HORSE);
                    horse.setAdult();
                    horse.setOwner(p);
                    horse.setPassenger(p);
                    //int random1 = (int )(Math.random() * 7 + 1);
                    //int random2 = (int )(Math.random() * 10 + 1);

                    //online.sendMessage(Main.Prefix + "Horse is " + horse + " at " + horse.getLocation());
                    horse.setMaxHealth(20.0);
                    horse.setJumpStrength(0.75);
                    if (p.getGameMode() != GameMode.CREATIVE)
                    {
                        horse.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 999999999));
                    }
                    cancel(); //Cancels the timer
                }

            }.runTaskTimer(plugin, 20L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);
        }
        else
        {
            p.setGameMode(GameMode.CREATIVE);
            p.sendMessage(Main.Prefix + "§aYou are in §9Edit Mode.");
            p.removePotionEffect(PotionEffectType.SLOW);
        }
    }

    public static void enableMovement(Player p, Horse horse)
    {
        p.removePotionEffect(PotionEffectType.SLOW);
        horse.removePotionEffect(PotionEffectType.SLOW);
        p.getInventory().remove(new ItemStack(Material.SADDLE));
        //horse.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999999, 999999999));
    }

    public static void getHorseColor(Player p, Horse horse, int res)
    {

        if (res == 1 && p.hasPermission("equestriandash.horsecolors.white"))
        {
            horse.setColor(Horse.Color.WHITE);
        }
        else if (res == 2 && p.hasPermission("equestriandash.horsecolors.black"))
        {
            horse.setColor(Horse.Color.BLACK);
        }
        else if (res == 3 && p.hasPermission("equestriandash.horsecolors.brown"))
        {
            horse.setColor(Horse.Color.BROWN);
        }
        else if (res == 4 && p.hasPermission("equestriandash.horsecolors.chestnut"))
        {
            horse.setColor(Horse.Color.CHESTNUT);
        }
        else if (res == 5 && p.hasPermission("equestriandash.horsecolors.creamy"))
        {
            horse.setColor(Horse.Color.CREAMY);
        }
        else if (res == 6 && p.hasPermission("equestriandash.horsecolors.dark_brown"))
        {
            horse.setColor(Horse.Color.DARK_BROWN);
        }
        else if (res == 7 && p.hasPermission("equestriandash.horsecolors.gray"))
        {
            horse.setColor(Horse.Color.GRAY);
        }
        else
        {
            horse.setColor(Horse.Color.WHITE);
        }
    }

    public static void getHorsePattern(Player p, Horse horse, int res)
    {
        if (res == 1 && p.hasPermission("equestriandash.horsestyles.black_dots"))
        {
            horse.setStyle(Horse.Style.BLACK_DOTS);
        }
        else if (res == 2 && p.hasPermission("equestriandash.horsestyles.none"))
        {
            horse.setStyle(Horse.Style.NONE);
        }
        else if (res == 3 && p.hasPermission("equestriandash.horsestyles.white"))
        {
            horse.setStyle(Horse.Style.WHITE);
        }
        else if (res == 4 && p.hasPermission("equestriandash.horsestyles.whitefield"))
        {
            horse.setStyle(Horse.Style.WHITEFIELD);
        }
        else if (res == 5 && p.hasPermission("equestriandash.horsestyles.white_Dots"))
        {
            horse.setStyle(Horse.Style.WHITE_DOTS);
        }
        else if (res == 6 && p.hasPermission("equestriandash.horsestyles.skeleton"))
        {
            horse.setVariant(Horse.Variant.SKELETON_HORSE);
        }
        else if (res == 7 && p.hasPermission("equestriandash.horsestyles.zombie"))
        {
            horse.setVariant(Horse.Variant.UNDEAD_HORSE);
        }
        else
        {
            horse.setStyle(Horse.Style.NONE);
        }
    }

    public void startCounter(final Player p, final int countTo)
    {

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        Objective o = board.registerNewObjective("test", "edcountdown");
        o.setDisplayName("§9§lPre-Race");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        scr = o.getScore("§3Starting in...");

        if (countTo != 0)
        {
            count = countTo;
        }
        GameState.setState(GameState.State.COUNT_DOWN_TO_START);

        Bukkit.broadcastMessage(Prefix + "§3Game starting in §b" + countTo + " §3seconds..");


        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if (p.getServer().getOnlinePlayers().length < plugin.getConfig().getInt("Players.MinPlayers"))
                {
                    cancel();
                    plugin.getServer().broadcastMessage(Prefix + "§6§oToo many players have left.");
                    PlayerJoinListener.racing = false;
                    countdownstarted = false;
                    tries++;
                    count = countTo;
                }

                if (count <= 1)
                {

                    //Start game method
                    int fx = GameState.getCurrentTrack().getTrackData().getInt("Fw.X");
                    int fy = GameState.getCurrentTrack().getTrackData().getInt("Fw.Y");
                    int fz = GameState.getCurrentTrack().getTrackData().getInt("Fw.Z");
                    Location flareloc = new Location(GameState.getCurrentTrack().getWorld(), fx, fy, fz);

                    Firework fw = p.getWorld().spawn(flareloc, Firework.class);
                    FireworkMeta data = fw.getFireworkMeta();
                    //p.sendMessage(Main.Prefix + "Firework spawned at " + flareloc);
                    data.addEffects(FireworkEffect.builder().withColor(Color.GREEN).with(Type.BALL_LARGE).build());
                    data.setPower(1);
                    fw.setFireworkMeta(data);
                    GameState.setState(GameState.State.RACE_IN_PROGRESS);
                    Bukkit.broadcastMessage(Prefix + ChatColor.GREEN + "§l§oGO!");

                    EDRaceBeginEvent callable = new EDRaceBeginEvent();
                    Bukkit.getPluginManager().callEvent(callable);
                    p.getInventory().setArmorContents(new ItemStack[]{});

                    board.clearSlot(DisplaySlot.SIDEBAR);
                    placePlayers();

                    for (Player online : Bukkit.getServer().getOnlinePlayers())
                    {
                        if (online.getVehicle() != null)
                        {
                            enableMovement(online, (Horse) online.getVehicle());
                            online.setMetadata("lastLocX", new FixedMetadataValue(plugin, p.getLocation().getX()));
                            online.setMetadata("lastLocY", new FixedMetadataValue(plugin, p.getLocation().getY()));
                            online.setMetadata("lastLocZ", new FixedMetadataValue(plugin, p.getLocation().getZ()));
                            online.setMetadata("lastLocPitch", new FixedMetadataValue(plugin, p.getLocation().getPitch()));
                            online.setMetadata("lastLocYaw", new FixedMetadataValue(plugin, p.getLocation().getYaw()));

                        }
                        online.playSound(online.getLocation(), Sound.NOTE_PLING, 3, 2);
                        online.getInventory().clear();
                        online.closeInventory();

                    }

                    new BukkitRunnable()
                    {

                        @Override
                        public void run()
                        {
                            if (Bukkit.getOnlinePlayers().length >= 1)
                            {
                                placePlayers();
                            }
                        }

                    }.runTaskTimer(plugin, 20L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);

                    cancel(); //Cancels the timer
                }
                else
                {
                    for (Player o : Bukkit.getOnlinePlayers())
                    {
                        o.setScoreboard(board);
                    }
                    count = (count - 1);
                    scr.setScore(count); //Making it so after "Time:" it displays the int countdown(So how long it has left in seconds.)
                    //Bukkit.getServer().broadcastMessage(Prefix + "§9Race begins in: §b" + count);
                    if (count <= 3 && count != 0)
                    {
                        for (Player onl : plugin.getServer().getOnlinePlayers())
                        {
                            onl.playSound(onl.getLocation(), Sound.NOTE_PLING, 3, 1);
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event)
    {
        if (!(plugin.getConfig().getBoolean("EditMode")))
        {
            Player p = event.getPlayer();
            String[] s = Main.plugin.getConfig().getString("Lobby").split(",");
            Location teleport = new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]), Float.parseFloat(s[5]), Float.parseFloat(s[4]));
            p.teleport(teleport);


            if (GameState.getState() != GameState.State.WAITING_FOR_PLAYERS)
            {
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        event.getPlayer().kickPlayer(Main.Prefix + "§cThis game is in progress!");
                    }
                }.runTask(plugin);

                return;
            }

            Player player = event.getPlayer();

            //Main.buildRaceline(player);

            racers.add(player);
            player.setMetadata("trackPlace", new FixedMetadataValue(plugin, 1));
            player.setMetadata("playerLap", new FixedMetadataValue(plugin, 0));
            player.setMetadata("markerPos", new FixedMetadataValue(plugin, 0));
            player.setMetadata("ebled", new FixedMetadataValue(plugin, false));
            player.setMetadata("colorKey", new FixedMetadataValue(plugin, 1));
            player.setMetadata("patternKey", new FixedMetadataValue(plugin, 2));
            player.setMetadata("choosingColor", new FixedMetadataValue(plugin, false));
            player.setMetadata("choosingStyle", new FixedMetadataValue(plugin, false));
            player.setMetadata("playerInLine", new FixedMetadataValue(plugin, false));
            player.setMetadata("inving", new FixedMetadataValue(plugin, false));
            player.setMetadata("invSpinning", new FixedMetadataValue(plugin, false));

            //activateSuperCharged(player);

            event.setJoinMessage(Prefix + ChatColor.AQUA + player.getName() + " §3is now competing!");
            VoteBoard.updateBoard();
            int players = player.getServer().getOnlinePlayers().length;
            //player.sendMessage(Prefix + ChatColor.GOLD + "Joined! §c§l§oRight-click with the saddle to customize horse colors!");
            //int maxplayers = plugin.getConfig().getInt("Config.Players.Max");
            //Player[] players = player.getServer().getOnlinePlayers().equals();
            //player.sendMessage(Main.Prefix + "§6Thanks for joining!");

            if (players < Bukkit.getMaxPlayers())
            {

                p.sendMessage(Main.Prefix + "§aWelcome! §eVote for the map with /vote.");

                final int minplayers = plugin.getConfig().getInt("Players.MinPlayers");
                if (Bukkit.getOnlinePlayers().length == minplayers)
                {
                    Bukkit.broadcastMessage(Main.Prefix + "§bEnough players have been gathered to start the game!");
                    for (Player o : Bukkit.getOnlinePlayers())
                    {
                        o.playSound(o.getLocation(), Sound.ORB_PICKUP, 3, 2);
                    }
                    new BukkitRunnable()
                    {
                        int count = plugin.getConfig().getInt("Countdown.CountTo");

                        @Override
                        public void run()
                        {
                            count--;

                            if (count > 0 && Bukkit.getOnlinePlayers().length >= minplayers)
                            {
                                if (plugin.getConfig().getIntegerList("Countdown.WarnAt").contains(count))
                                {
                                    String suf = "s";

                                    if (count == 1)
                                    {
                                        suf = "";
                                    }

                                    Bukkit.broadcastMessage(Main.Prefix + "§eVoting ends in §a" + count + " second" + suf + "§e.");
                                    for (Player o : Bukkit.getOnlinePlayers())
                                    {
                                        o.playSound(o.getLocation(), Sound.ORB_PICKUP, 3, 2);
                                    }
                                }
                            }
                            else if (Bukkit.getOnlinePlayers().length >= minplayers)
                            {
                                cancel();
                                Track chosen = null;
                                if (!plugin.getConfig().getBoolean("Countdown.RandomPick") && !VoteBoard.getVotes().isEmpty() && Main.getTrackRegistery().getTracks().size() > 1)
                                {
                                    ValueComparatorTrack bvc = new ValueComparatorTrack(VoteBoard.getVotes());
                                    TreeMap<Track, Integer> sorted_map = new TreeMap<>(bvc);
                                    sorted_map.putAll(VoteBoard.getVotes());
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
                                    List<Track> tracks = Main.getTrackRegistery().getTracks();
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

                                Bukkit.broadcastMessage(Main.Prefix + "§aStarting race on map: \"§b" + chosen.getDisplayName() + "\"");
                                GameState.setCurrentTrack(chosen);

                                int c = 1;

                                FileConfiguration data = chosen.getTrackData();
                                for (Player on : Bukkit.getOnlinePlayers())
                                {
                                    if (data.contains("Spawn" + c))
                                    {
                                        Location go = new Location(chosen.getWorld(), data.getDouble("Spawn" + c + ".X"), data.getDouble("Spawn" + c + ".Y"), data.getDouble("Spawn" + c + ".Z"));
                                        mountPlayer(on);
                                        on.teleport(go);
                                    }
                                    else
                                    {
                                        on.kickPlayer(Main.Prefix + "§cData missmatch!\n The player count higher than the amount of listed spawns.");
                                        plugin.getLogger().severe("ERROR: You didn't list enough spawns to meet the amount of players allowed!");
                                    }
                                    c++;
                                }
                                cancel();
                            }
                            else
                            {
                                cancel();
                                Bukkit.broadcastMessage(Main.Prefix + "§cToo many players have left.");
                            }
                        }
                    }.runTaskTimer(plugin, 0, 20);
                }
            }
            else
            {
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        event.getPlayer().kickPlayer(Main.Prefix + "§cThis game is full!");
                    }
                }.runTask(plugin);
            }
        }
        else
        {
            event.getPlayer().sendMessage(Main.Prefix + "§aYou are in §9Edit Mode.");
        }
    }

    public void mountPlayer(Player player)
    {
        disableMovement(player);


        startRace(player);
        countdownstarted = true;


    }

    //Adding all online players to the ArrayList

    public void startRace(Player p)
    {
        int countto = plugin.getConfig().getInt("Countdown.StartTime");

        startCounter(p, countto);
    }


    public void placePlayers()
    {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        board.clearSlot(DisplaySlot.SIDEBAR);
        Objective objective = board.registerNewObjective("test", "playerplaces");
        objective.setDisplayName("§6Player §9Places");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        generateTags();

        for (Player p : Bukkit.getOnlinePlayers())
        {
            String dispname = getPlaceColor(p.getWorld().getPlayers().indexOf(p)) + p.getName();
            if (p.getName().length() > 14)
            {
                dispname = getPlaceColor(p.getWorld().getPlayers().indexOf(p)) + p.getName().substring(0, 11) + "...";
            }


            Score score = objective.getScore(dispname);


            //p.sendMessage(Main.Prefix + "§5§nApplying the score: " + inc);
            score.setScore(PlayerMoveListener.evalPlace(p));
            Ranking.Scores.put(p.getUniqueId(), PlayerMoveListener.evalPlace(p));


            p.setScoreboard(board);
        }
    }

    private String getPlaceColor(int inc)
    {
        if (inc == 0)
        {
            return "§c";
        }
        else if (inc == 1)
        {
            return "§b";
        }
        else if (inc == 2)
        {
            return "§a";
        }
        else if (inc == 3)
        {
            return "§e";
        }
        else if (inc == 4)
        {
            return "§6";
        }
        else if (inc == 5)
        {
            return "§d";
        }
        else if (inc == 6)
        {
            return "§5";
        }
        else if (inc == 7)
        {
            return "§9";
        }

        return "§1";
    }

    public void generateTags()
    {
        int i = 0;
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = sb.registerNewObjective("custom", "ednametags");
        obj.setDisplayName("Place");

        obj.setDisplaySlot(DisplaySlot.BELOW_NAME);

        for (Map.Entry<UUID, Integer> entry : Ranking.getPlayersArranged().entrySet())
        {
            Player p = Bukkit.getPlayer(entry.getKey());

            String score = "NULL";
            if (i == 0)
            {
                score = ("§c§l1st Place");
            }
            else if (i == 1)
            {
                score = ("§6§l2nd Place");
            }
            else if (i == 2)
            {
                score = ("§e§l3rd Place");
            }
            else if (i == 3)
            {
                score = ("§a§l4th Place");
            }
            else if (i == 4)
            {
                score = ("§b§l5th Place");
            }
            else if (i == 5)
            {
                score = ("§9§l6th Place");
            }
            else if (i == 6)
            {
                score = ("§5§l7th Place");
            }
            else if (i == 7)
            {
                score = ("§8§l8th Place");
            }
            else if (i >= 8)
            {
                score = ("§8§l" + (i + 1) + "th Place");
            }

            Score scr = obj.getScore(score);
            scr.setScore(new Racer(p).getScore());
            p.setScoreboard(sb);

            i++;
        }
    }
}