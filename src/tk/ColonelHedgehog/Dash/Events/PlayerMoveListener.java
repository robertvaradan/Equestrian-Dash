/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import tk.ColonelHedgehog.Dash.API.Entity.Racer;
import tk.ColonelHedgehog.Dash.API.Event.EDRaceEndEvent;
import tk.ColonelHedgehog.Dash.API.Event.EDRacerLapEvent;
import tk.ColonelHedgehog.Dash.Assets.GameState;
import tk.ColonelHedgehog.Dash.Assets.Ranking;
import tk.ColonelHedgehog.Dash.Core.Main;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static tk.ColonelHedgehog.Dash.Core.Main.LapCuboid;

/**
 * @author Robert
 */
public class PlayerMoveListener implements Listener
{
    public static Main plugin = Main.plugin;
    public static Player[] RPlace;
    public static ArrayList<Racer> EDFinished = new ArrayList<>();
    public static ArrayList<Integer> playerScores = new ArrayList<>(); // You can add all the ints in the world to this arraylist.

    public static void updateRespawn(Player p)
    {
        Entity e = p.getVehicle();
        Block block = e.getLocation().getBlock().getRelative(BlockFace.DOWN);

        Location loc1 = e.getLocation().add(1, -1, 1);
        Location loc2 = e.getLocation().add(-1, -1, 1);
        Location loc3 = e.getLocation().add(1, -1, -1);
        Location loc4 = e.getLocation().add(-1, -1, -1);
        Location loc5 = e.getLocation().add(0, -1, -1);
        Location loc6 = e.getLocation().add(1, -1, 0);
        Location loc7 = e.getLocation().add(-1, -1, 0);
        Location loc8 = e.getLocation().add(0, -1, 1);

        Location tloc1 = e.getLocation().add(2, -1, 2);
        Location tloc2 = e.getLocation().add(-2, -1, 2);
        Location tloc3 = e.getLocation().add(2, -1, -2);
        Location tloc4 = e.getLocation().add(-2, -1, -2);
        Location tloc5 = e.getLocation().add(0, -1, -2);
        Location tloc6 = e.getLocation().add(2, -1, 0);
        Location tloc7 = e.getLocation().add(-2, -2, 0);
        Location tloc8 = e.getLocation().add(0, -1, 2);

        Location eloc1 = e.getLocation().add(3, -1, 3);
        AtomicReference<Location> eloc2 = new AtomicReference<>(e.getLocation().add(-3, -1, 3));
        Location eloc3 = e.getLocation().add(3, -1, -3);
        Location eloc4 = e.getLocation().add(-3, -1, -3);
        Location eloc5 = e.getLocation().add(3, -1, -3);
        Location eloc6 = e.getLocation().add(3, -1, 0);
        Location eloc7 = e.getLocation().add(-3, -1, 0);
        Location eloc8 = e.getLocation().add(0, -1, 3);
        if (!block.isLiquid() && block.getType() != Material.AIR && e.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR && loc1.getBlock().getType() != Material.AIR && loc2.getBlock().getType() != Material.AIR && loc3.getBlock().getType() != Material.AIR && loc4.getBlock().getType() != Material.AIR && loc5.getBlock().getType() != Material.AIR && loc6.getBlock().getType() != Material.AIR && loc7.getBlock().getType() != Material.AIR && loc8.getBlock().getType() != Material.AIR)
        {
            if (tloc1.getBlock().getType() != Material.AIR && tloc2.getBlock().getType() != Material.AIR && tloc3.getBlock().getType() != Material.AIR && tloc4.getBlock().getType() != Material.AIR && tloc5.getBlock().getType() != Material.AIR && tloc6.getBlock().getType() != Material.AIR && tloc7.getBlock().getType() != Material.AIR && tloc8.getBlock().getType() != Material.AIR)
            {
                if (!checkFor(Material.PISTON_MOVING_PIECE, p, 7, 7, 7) && eloc1.getBlock().getType() != Material.AIR && eloc2.get().getBlock().getType() != Material.AIR && eloc3.getBlock().getType() != Material.AIR && eloc4.getBlock().getType() != Material.AIR && eloc5.getBlock().getType() != Material.AIR && eloc6.getBlock().getType() != Material.AIR && eloc7.getBlock().getType() != Material.AIR && eloc8.getBlock().getType() != Material.AIR)
                {
                    //p.sendMessage(Main.Prefix + "§6Stage 1: No air");

                    //p.sendMessage(Main.Prefix + "§aStage 3: No lava, saving pos...");
                    p.setMetadata("lastLocX", new FixedMetadataValue(plugin, p.getLocation().getX()));
                    p.setMetadata("lastLocY", new FixedMetadataValue(plugin, p.getLocation().getY()));
                    p.setMetadata("lastLocZ", new FixedMetadataValue(plugin, p.getLocation().getZ()));
                    p.setMetadata("lastLocPitch", new FixedMetadataValue(plugin, p.getLocation().getPitch()));
                    p.setMetadata("lastLocYaw", new FixedMetadataValue(plugin, p.getLocation().getYaw()));
                }
            }
        }

        if (checkFor(Material.LAVA, p, 2, 2, 2) || checkFor(Material.STATIONARY_LAVA, p, 2, 2, 2))
        {
            p.damage(30.00);
        }
    }

    public static void plCooldown(final Player p)
    {
        Main.getCooldownHandler().placeInCooldown(p, 5000L);
    }

    public static int evalPlace(Player p)
    {
        Location playerLoc = p.getLocation();
        int pX = playerLoc.getBlockX();
        int pY = playerLoc.getBlockY();
        int pZ = playerLoc.getBlockZ();


        for (int x = -7; x <= 7; x++)
        {
            for (int y = -7; y <= 7; y++)
            {
                for (int z = -7; z <= 7; z++)
                {
                    Block b = p.getWorld().getBlockAt(pX + x, pY + y, pZ + z);
                    if (b.getType() == Material.SIGN || b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN && b.getState() != null)
                    {


                        BlockState state = b.getState();

                        Sign sign = (Sign) state;
                        String signline1 = sign.getLine(0);
                        String signline2 = sign.getLine(1);
                        String replace = signline2.replace("", "");
                        //p.sendMessage(Main.Prefix + "Debug: Was a sign. " + signline1 + " and " + signline2);


                        if ("#".equals(signline1) && isInteger(replace))
                        {
                            //p.sendMessage(Main.Prefix + "Debug: Line 2 is int.");
                            int markers = GameState.getCurrentTrack().getTrackData().getInt("Editor.Markers");
                            int plap = p.getMetadata("playerLap").get(0).asInt();
                            p.setMetadata("markerPos", new FixedMetadataValue(plugin, Integer.parseInt(replace)));

                            int ppos = p.getMetadata("markerPos").get(0).asInt();


                            int racenum = (plap * markers) + ppos;


                            if (ppos > 1)
                            {
                                //p.sendMessage(Main.Prefix + "Debug: Lap was bigger than 1.");
                                return racenum;
                            }
                            else
                            {
                                //p.sendMessage(Main.Prefix + "Debug: Lap was smaller than 1.");
                                return racenum;
                            }
                        }
                        else
                        {
                            int ppos = p.getMetadata("markerPos").get(0).asInt();
                            int markers = plugin.getConfig().getInt("Editor.Markers");
                            int plap = p.getMetadata("playerLap").get(0).asInt();


                            int racenum = (plap * markers) + ppos;

                            if (!"#".equals(signline1))
                            {
                                //p.sendMessage(Main.Prefix + "Debug: Line 1 was not a #.");
                            }
                            else if (isInteger(signline2))
                            {
                                //p.sendMessage(Main.Prefix + "Debug: Line 2 was not an int.");
                            }
                            return racenum;
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static boolean isInteger(String s)
    {
        try
        {
            Integer.parseInt(s);
        }
        catch (NumberFormatException e)
        {
            //plugin.getServer().broadcastMessage("§6" + s + " §awas not an int.");
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static boolean checkFor(Material m, Player p, int rX, int rY, int rZ)
    {

        Location playerLoc = p.getLocation();
        int pX = playerLoc.getBlockX();
        int pY = playerLoc.getBlockY();
        int pZ = playerLoc.getBlockZ();


        for (int x = -(rX); x <= rX; x++)
        {
            for (int y = -(rY); y <= rY; y++)
            {
                for (int z = -(rZ); z <= rZ; z++)
                {
                    Block b = p.getWorld().getBlockAt((int) pX + x, (int) pY + y, (int) pZ + z);

                    if (b.getType() == m)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isInRaceline(Player p)
    {
        return LapCuboid != null && LapCuboid.contains(p.getLocation());
    }

    public static void messageExcept(Player p, String Message)
    {
        for (Player on : plugin.getServer().getOnlinePlayers())
        {
            if (!on.getName().equalsIgnoreCase(p.getName()))
            {
                on.sendMessage(Message);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static void firstPlace(Player p)
    {
        fireWorkLoops(p);
        //delayStopPlayer(p);
        p.playEffect(p.getLocation(), Effect.RECORD_PLAY, 2257);
    }

    public static void delayStopPlayer(final Player p)
    {
        new BukkitRunnable()
        {

            @Override
            public void run()
            {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 999999999));
                if (p.getVehicle() != null)
                {
                    LivingEntity v = (LivingEntity) p.getVehicle();
                    v.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 999999999));
                }
                cancel(); //Cancels the timer
            }

        }.runTaskTimer(plugin, 60L /* The amount of time until the timer starts */, 0L /*  The delay of each call */);
    }

    public static void fireWorkLoops(final Player p)
    {
        new BukkitRunnable()
        {

            @Override
            public void run()
            {
                //Start game method
                int num1 = getRandom(1, 6);
                int num2 = getRandom(-3, 3);
                int num3 = getRandom(-5, 5);

                Location fwloc = new Location(p.getWorld(), p.getLocation().getX() + num3 / num1 + num2, p.getLocation().getY(), p.getLocation().getZ() + num2 + num3 + num1);


                Firework fw = p.getWorld().spawn(fwloc, Firework.class);
                FireworkMeta data = fw.getFireworkMeta();
                data.addEffects(FireworkEffect.builder().withColor(getRandomColor(num1)).with(FireworkEffect.Type.STAR).build());
                data.setPower(0);
                fw.setFireworkMeta(data);
                if (!p.isOnline())
                {
                    cancel(); //Cancels the timer
                }
            }

        }.runTaskTimer(plugin, 0L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);
    }

    public static int getRandom(int from, int to)
    {
        return (int) ((Math.random() * to) + from);
    }

    public static Color getRandomColor(int rand)
    {
        Color choice = Color.RED;
        if (rand == 1)
        {
            choice = Color.RED;
        }
        else if (rand == 2)
        {
            choice = Color.ORANGE;
        }
        else if (rand == 3)
        {
            choice = Color.YELLOW;
        }
        else if (rand == 4)
        {
            choice = Color.LIME;
        }
        else if (rand == 5)
        {
            choice = Color.BLUE;
        }
        else if (rand == 6)
        {
            choice = Color.PURPLE;
        }
        return choice;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event)
    {
        Player p = event.getPlayer();
        //p.sendMessage(Prefix + "§bYour place is: " + evalPlace(p);da

        if (!plugin.getConfig().getBoolean("EditMode") && isInRaceline(p))
        {
            p.setMetadata("playerScore", new FixedMetadataValue(plugin, evalPlace(p)));

            if (!p.getMetadata("playerInLine").get(0).asBoolean() && p.getVehicle() != null)
            {
                Entity v = p.getVehicle();

                //p.sendMessage(Main.Prefix + "§bTo: " + event.getTo().getZ() + " §6From: " + event.getFrom().getZ() + " §eDiff: " + (event.getTo().getZ() - event.getFrom().getZ()));
                if (p.getGameMode() != GameMode.CREATIVE)
                {
                    if (p.isSprinting())
                    {
                        p.setSprinting(false);
                    }
                }
                if (p.getMetadata("lastLocZ").get(0).asFloat() < event.getTo().getZ() && !EDFinished.contains(new Racer(p)) && GameState.getState() == GameState.State.RACE_IN_PROGRESS)
                {
                    v.setVelocity(new Vector(0, 0.5, 1.5));
                    p.setVelocity(new Vector(0, 0.5, 1.5));
                    int lap = p.getMetadata("playerLap").get(0).asInt() + 1;
                    p.setMetadata("playerLap", new FixedMetadataValue(plugin, lap));
                    if (lap != 1)
                    {
                        Location fwloc = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
                        Firework fw = p.getWorld().spawn(fwloc, Firework.class);
                        FireworkMeta data = fw.getFireworkMeta();
                        data.addEffects(FireworkEffect.builder().withColor(Color.BLACK, Color.WHITE).with(FireworkEffect.Type.STAR).build());
                        data.setPower(0);
                        fw.setFireworkMeta(data);
                        fwloc.getWorld().playSound(fwloc, Sound.SUCCESSFUL_HIT, 3, 1);

                        EDRacerLapEvent callable2 = new EDRacerLapEvent(new Racer(p), lap);
                        Bukkit.getPluginManager().callEvent(callable2);

                        if (lap < GameState.getCurrentTrack().getTrackData().getInt("Laps"))
                        {
                            p.sendMessage(PlayerJoinListener.Prefix + "§9§lLAP §a§l" + (lap) + "!");
                            messageExcept(p, PlayerJoinListener.Prefix + "§b" + p.getName() + " §9is on §aLap " + (lap));
                        }
                        else if (lap == GameState.getCurrentTrack().getTrackData().getInt("Laps"))
                        {
                            p.sendMessage(PlayerJoinListener.Prefix + "§8§l§o§nF§f§l§o§nI§8§l§o§nN§8§l§o§nA§f§l§o§nL §8§l§o§nL§f§l§o§nA§8§l§o§nP§f§l§o§n!");
                            messageExcept(p, PlayerJoinListener.Prefix + "§b" + p.getName() + " §9is on his §cFINAL LAP!");
                        }
                        else if (lap > GameState.getCurrentTrack().getTrackData().getInt("Laps"))
                        {
                            String det = "0th";
                            EDFinished.add(new Racer(p));
                            if (EDFinished.size() == plugin.getConfig().getInt("RaceOver.EndAfter"))
                            {
                                for (Player on : Bukkit.getOnlinePlayers())
                                {
                                    on.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 99999999));

                                }

                                Bukkit.broadcastMessage(Main.Prefix + "§aThe race has ended! §bAll required players have finished.");
                                TreeMap<UUID, Integer> nubs = Ranking.getPlayersArranged();
                                List<Racer> morenubs = new ArrayList<>();
                                for (Map.Entry<UUID, Integer> nub : nubs.entrySet())
                                {
                                    Racer racer = new Racer(Bukkit.getPlayer(nub.getKey()));
                                    if (EDFinished.contains(racer))
                                    {
                                        morenubs.add(racer);
                                    }
                                }

                                EDFinished.removeAll(morenubs);

                                Bukkit.broadcastMessage("§9§l====================");

                                for (Racer r : EDFinished)
                                {
                                    String name = r.getPlayer().getName();
                                    if (EDFinished.indexOf(r) == 0)
                                    {
                                        Bukkit.broadcastMessage("§c§l1st §8> §c" + name);
                                    }
                                    else if (EDFinished.indexOf(r) == 1)
                                    {
                                        Bukkit.broadcastMessage("§6§l2nd §8> §6" + name);
                                    }
                                    else if (EDFinished.indexOf(r) == 2)
                                    {
                                        Bukkit.broadcastMessage("§e§l3rd §8> §e" + name);
                                    }
                                    else if (EDFinished.indexOf(r) == 3)
                                    {
                                        Bukkit.broadcastMessage("§a§l4th §8> §a" + name);
                                    }
                                    else if (EDFinished.indexOf(r) == 4)
                                    {
                                        Bukkit.broadcastMessage("§b§l5th §8> §b" + name);
                                    }
                                    else if (EDFinished.indexOf(r) == 5)
                                    {
                                        Bukkit.broadcastMessage("§9§l6th §8> §9" + name);
                                    }
                                    else if (EDFinished.indexOf(r) == 6)
                                    {
                                        Bukkit.broadcastMessage("§5§l7th §8> §5" + name);
                                    }
                                    else if (EDFinished.indexOf(r) == 7)
                                    {
                                        Bukkit.broadcastMessage("§8§l8th §8> §8" + name);
                                    }
                                    else if (EDFinished.indexOf(r) >= 8)
                                    {
                                        Bukkit.broadcastMessage("§8§l" + (EDFinished.indexOf(r) + 1) + "th §8> §8" + name);
                                    }
                                }

                                Bukkit.broadcastMessage("§7§l====================");

                                for (Map.Entry<UUID, Integer> nub : nubs.entrySet())
                                {
                                    if (!EDFinished.contains(new Racer(Bukkit.getPlayer(nub.getKey()))))
                                    {
                                        String name = Bukkit.getPlayer(nub.getKey()).getName();
                                        if (nub.getValue() == 1)
                                        {
                                            Bukkit.broadcastMessage("§c§l1st §8> §c" + name);
                                        }
                                        else if (nub.getValue() == 2)
                                        {
                                            Bukkit.broadcastMessage("§6§l2nd §8> §6" + name);
                                        }
                                        else if (nub.getValue() == 3)
                                        {
                                            Bukkit.broadcastMessage("§e§l3rd §8> §e" + name);
                                        }
                                        else if (nub.getValue() == 4)
                                        {
                                            Bukkit.broadcastMessage("§a§l4th §8> §a" + name);
                                        }
                                        else if (nub.getValue() == 5)
                                        {
                                            Bukkit.broadcastMessage("§b§l5th §8> §b" + name);
                                        }
                                        else if (nub.getValue() == 6)
                                        {
                                            Bukkit.broadcastMessage("§9§l6th §8> §9" + name);
                                        }
                                        else if (nub.getValue() == 7)
                                        {
                                            Bukkit.broadcastMessage("§5§l7th §8> §5" + name);
                                        }
                                        else if (nub.getValue() == 8)
                                        {
                                            Bukkit.broadcastMessage("§8§l8th §8> §8" + name);
                                        }

                                    }
                                }
                                Bukkit.broadcastMessage("§9§l====================");
                                GameState.setState(GameState.State.RACE_ENDED);

                                EDRaceEndEvent callable = new EDRaceEndEvent(EDFinished);
                                Bukkit.getPluginManager().callEvent(callable);

                                if (plugin.getConfig().getBoolean("RaceOver.Restart.Enabled"))
                                {
                                    new BukkitRunnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            for(Player on : Bukkit.getOnlinePlayers())
                                            {
                                                on.kickPlayer(Main.Prefix + "§cThe game has ended.");
                                            }

                                            restart();
                                        }
                                    }.runTaskLater(plugin, plugin.getConfig().getLong("RaceOver.Restart.EndAfter"));
                                }

                            }
                            if (EDFinished.size() == 1)
                            {
                                det = "1st";
                                firstPlace(p);
                            }
                            else if (EDFinished.size() == 2)
                            {
                                det = "2nd";
                            }
                            else if (EDFinished.size() == 3)
                            {
                                det = "3rd";
                            }
                            else
                            {
                                det = EDFinished.size() + "th";
                            }

                            messageExcept(p, PlayerJoinListener.Prefix + "§c" + p.getName().toUpperCase() + " §eHAS FINISHED §d§l" + det.toUpperCase() + "!");
                            p.sendMessage(PlayerJoinListener.Prefix + "§c§oYOU'VE FINISHED §d§l" + det.toUpperCase() + "!");

                        }
                    }

                    p.setMetadata("playerInLine", new FixedMetadataValue(plugin, true));
                }
                else
                {
                    v.setVelocity(new Vector(0, 0.5, 1.5));
                    p.setVelocity(new Vector(0, 0.5, 1.5));
                    p.sendMessage(Main.Prefix + "§4§oWrong way, cheater! Turn around.");
                }

                //p.sendMessage(Main.Prefix + "§aDebug: §bPlayer in raceline!");
            }
        }
        else
        {
            p.setMetadata("playerInLine", new FixedMetadataValue(plugin, false));
        }

        //if(p.getVehicle() != null)
        //{
        if (p.getVehicle() instanceof Horse)
        {
            updateRespawn(p);
        }
//}

        List<Entity> nearby = p.getNearbyEntities(0.25, 0.25, 0.5);
        for (Entity cry : nearby)
        {
            if (cry instanceof EnderCrystal && p.getGameMode() != GameMode.CREATIVE)
            {
                //p.sendMessage(Main.Prefix + "Debug: Crystal nearby.");

                if (!Main.getCooldownHandler().isCooling(p))
                {
                    //p.sendMessage(Main.Prefix + "Debug: Not cooling down.");

                    p.getInventory().clear();
                    PlayerInteractEntityListener.giveReward(p, cry, cry.getLocation().getBlockX(), cry.getLocation().getBlockY(), cry.getLocation().getBlockZ());
                    cry.getWorld().playEffect(cry.getLocation(), Effect.STEP_SOUND, 20);
                    cry.getWorld().playSound(cry.getLocation(), Sound.GLASS, 3, 1);
                    plCooldown(p);

                }


            }
        }



    /*int place = 8;
    int lap = p.getMetadata("raceLap").get(0).asInt();
    for(int i = 0; i <= Bukkit.getOnlinePlayers().length; i++)
    {
        if(evalPlace(p, lap) > evalPlace(racers.get(i - 1), lap))
        {
            place--;
            RPlace[place] = p;
        }
        else
        {
            place--;
        }
    }*/

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
}
