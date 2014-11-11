/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

import org.bukkit.*;
import org.bukkit.FireworkEffect.Type;
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
import tk.ColonelHedgehog.Dash.Core.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.bukkit.entity.EntityType.HORSE;
import static tk.ColonelHedgehog.Dash.Events.PlayerInteractEntityListener.setName;
import static tk.ColonelHedgehog.Dash.Events.PlayerInteractListener.addLore;

/**
 *
 * @author Robert
 */
public class PlayerJoinListener implements Listener
{
    public static Main plugin = Main.plugin;

    public static Scoreboard placeBoard = null; //Creates a scoreboard called timerBoard(You will see what thats used for later)
    public static Objective placeObj = null; // Same as above but it creates a objective called timerObj
    public static Objective pl[]; //Creates a objective called o

    public static boolean RaceEnded = false;
    public static String Prefix = "§9§l[§3Equestrian§bDash§9§l]§r: ";
    
    
    public static void disableMovement(final Player p/*, Horse horse*/)
    {
        if (!(plugin.getConfig().getBoolean("Config.EditMode")))
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
        }
    }
    
    public static void enableMovement(Player p, Horse horse)
    {
        p.removePotionEffect(PotionEffectType.SLOW);
        horse.removePotionEffect(PotionEffectType.SLOW);
        p.getInventory().remove(new ItemStack(Material.SADDLE));
        //horse.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999999, 999999999));
    }    

    static int count = 20;
    public static boolean countdownstarted = false;

public static void getHorseColor(Player p, Horse horse, int res)
{
    
    if(res == 1 && p.hasPermission("equestriandash.horsecolors.white"))
    {
        horse.setColor(Horse.Color.WHITE);
    }
    else if(res == 2 && p.hasPermission("equestriandash.horsecolors.black"))
    {
        horse.setColor(Horse.Color.BLACK);                
    }
    else if(res == 3 && p.hasPermission("equestriandash.horsecolors.brown"))
    {
        horse.setColor(Horse.Color.BROWN);                                
    }
    else if(res == 4 && p.hasPermission("equestriandash.horsecolors.chestnut"))
    {
        horse.setColor(Horse.Color.CHESTNUT);                                                
    }
    else if(res == 5 && p.hasPermission("equestriandash.horsecolors.creamy"))
    {
        horse.setColor(Horse.Color.CREAMY);                                                
    }
    else if(res == 6 && p.hasPermission("equestriandash.horsecolors.dark_brown"))
    {
        horse.setColor(Horse.Color.DARK_BROWN);                                                           
    }
    else if(res == 7 && p.hasPermission("equestriandash.horsecolors.gray"))
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
    if(res == 1 && p.hasPermission("equestriandash.horsestyles.black_dots"))
    {
        horse.setStyle(Horse.Style.BLACK_DOTS);
    }
    else if(res == 2 && p.hasPermission("equestriandash.horsestyles.none"))
    {
        horse.setStyle(Horse.Style.NONE);                
    }
    else if(res == 3 && p.hasPermission("equestriandash.horsestyles.white"))
    {
        horse.setStyle(Horse.Style.WHITE);                                
    }
    else if(res == 4 && p.hasPermission("equestriandash.horsestyles.whitefield"))
    {
        horse.setStyle(Horse.Style.WHITEFIELD);                                                
    }
    else if(res == 5 && p.hasPermission("equestriandash.horsestyles.white_Dots"))
    {
        horse.setStyle(Horse.Style.WHITE_DOTS);                                                
    }
    else if(res == 6 && p.hasPermission("equestriandash.horsestyles.skeleton"))
    {
        horse.setVariant(Horse.Variant.SKELETON_HORSE);                                                           
    }
    else if(res == 7 && p.hasPermission("equestriandash.horsestyles.zombie"))
    {
        horse.setVariant(Horse.Variant.UNDEAD_HORSE);                                                           
    }
    else
    {
    horse.setStyle(Horse.Style.NONE);                        
    }
}

public static int tries = 1;

public void startCounter(final Player p, final int countTo)
{
    final Objective o; //Creates a objective called o
    Scoreboard timerBoard; //Creates a scoreboard called timerBoard(You will see what thats used for later)
    final Scoreboard board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        board.clearSlot(DisplaySlot.SIDEBAR);
        o = board.registerNewObjective("test", "timerInstance" + tries);
        //Registering the objective needed for the timer
        o.setDisplayName("§9§lEquestrian§b§lDash"); // Setting the title for the scoreboard. This would look like: TCGN | Walls
        o.setDisplaySlot(DisplaySlot.SIDEBAR); //Telling the scoreboard where to display when we tell it to display

        timerBoard = board; //Setting timerBoard equal to board.
    //int count = 60; //Time in seconds (Can reference config)
    if(countTo != 0)
    {
        count = countTo;
    }
    
Bukkit.broadcastMessage(Prefix + "§3Game starting in §b" + countTo + " §3seconds..");
final Score score = o.getScore("§3Time: "); //Making a offline player called "Time:" with a green name and adding it to the scoreboard

        for(Player online : Bukkit.getServer().getOnlinePlayers()) 
        {
            online.setScoreboard(timerBoard);
        }

new BukkitRunnable() {
  @Override
  public void run() 
  {
      if(p.getServer().getOnlinePlayers().length < plugin.getConfig().getInt("Config.Players.Min"))
        {
        cancel();
        plugin.getServer().broadcastMessage(Prefix + "§6§oToo many players have left. Waiting for more players to join...");
        PlayerJoinListener.racing = false;
        countdownstarted = false;
        tries++;
        count = countTo;
        }
      
      if(count <= 1) 
      {
    
          //Start game method
        int fx = plugin.getConfig().getInt("Config.Fw.X");
        int fy = plugin.getConfig().getInt("Config.Fw.Y");
        int fz = plugin.getConfig().getInt("Config.Fw.Z");
        Location flareloc = new Location(p.getWorld(), fx, fy, fz);
          
                Firework fw = p.getWorld().spawn(flareloc, Firework.class);
                FireworkMeta data = fw.getFireworkMeta();
                //p.sendMessage(Main.Prefix + "Firework spawned at " + flareloc);
                data.addEffects(FireworkEffect.builder().withColor(Color.GREEN).with(Type.BALL_LARGE).build());
                data.setPower(1);
                fw.setFireworkMeta(data);
                PlayerJoinListener.racing = true;
                Bukkit.broadcastMessage(Prefix + ChatColor.GREEN + "§l§oGO!");
        p.getInventory().clear();
        p.getInventory().setArmorContents(new ItemStack[]{});
        
        board.clearSlot(DisplaySlot.SIDEBAR);
        for(Player online : Bukkit.getServer().getOnlinePlayers()) 
        {
        if(online.getVehicle() != null)
        {
        enableMovement(online, (Horse) online.getVehicle());
        online.setMetadata("lastLocX", new FixedMetadataValue(plugin, p.getLocation().getX()));
        online.setMetadata("lastLocY", new FixedMetadataValue(plugin, p.getLocation().getY()));
        online.setMetadata("lastLocZ", new FixedMetadataValue(plugin, p.getLocation().getZ()));
        online.setMetadata("lastLocPitch", new FixedMetadataValue(plugin, p.getLocation().getPitch()));
        online.setMetadata("lastLocYaw", new FixedMetadataValue(plugin, p.getLocation().getYaw()));
        
        }
        online.playSound(online.getLocation(), Sound.NOTE_PLING, 3, 2);
        this.cancel(); //Cancels the timer

        }
        
            new BukkitRunnable() 
            {

                @Override
                public void run() 
                {
                    if(Bukkit.getOnlinePlayers().length >= 1)
                    {
                    placePlayers();
                    }
                }

            }.runTaskTimer(plugin, 20L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);

      }
      else
      {
        count = (count - 1);
        score.setScore(count); //Making it so after "Time:" it displays the int countdown(So how long it has left in seconds.)
        //Bukkit.getServer().broadcastMessage(Prefix + "§9Race begins in: §b" + count);
        if(count <= 3 && count != 0)
        {
            for(Player onl : plugin.getServer().getOnlinePlayers())
            {
            onl.playSound(onl.getLocation(), Sound.NOTE_PLING, 3, 1);
            }
        }
      }
  }
}.runTaskTimer(plugin, 0L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);
}
   
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        
        Main.buildRaceline(player);
        
        racers.add(player);
        player.setMetadata("trackPlace", new FixedMetadataValue(plugin, 1));
        player.setMetadata("pCooldown", new FixedMetadataValue(plugin, false));
        player.setMetadata("playerLap", new FixedMetadataValue(plugin, 0));
        player.setMetadata("markerPos", new FixedMetadataValue(plugin, 0));
        player.setMetadata("ebled", new FixedMetadataValue(plugin, false));
        player.setMetadata("colorKey", new FixedMetadataValue(plugin, -1));
        player.setMetadata("patternKey", new FixedMetadataValue(plugin, -1));
        player.setMetadata("choosingColor", new FixedMetadataValue(plugin, false));
        player.setMetadata("choosingStyle", new FixedMetadataValue(plugin, false));
        player.setMetadata("playerInLine", new FixedMetadataValue(plugin, false));
        player.setMetadata("inving", new FixedMetadataValue(plugin, false));
        //activateSuperCharged(player);
        
        event.setJoinMessage(Prefix + ChatColor.AQUA + player.getName() + " §3is now competing!");
        int players = player.getServer().getOnlinePlayers().length;
        player.sendMessage(Prefix + ChatColor.GOLD + "Joined! §c§l§oRight-click with the saddle to customize horse colors!");
        int maxplayers = plugin.getConfig().getInt("Config.Players.Max");
        //Player[] players = player.getServer().getOnlinePlayers().equals();
        //player.sendMessage(Main.Prefix + "§6Thanks for joining!");
        if(players < maxplayers)
        {
            mountPlayer(player, players);
        }
        
        else
        {
        player.kickPlayer(Prefix + "§4This game is full.");
        }
    }  
    
    public static boolean quitexception = false;
    
    public void mountPlayer(Player player, int id)
    {
        player.sendMessage(Prefix + ChatColor.GOLD + "Mounting. You are racer number: " + ChatColor.BLUE + "1");
        player.setMetadata("playerID", new FixedMetadataValue(plugin, id));

        int sx = plugin.getConfig().getInt("Config.Spawn" + id + ".X");
        int sy = plugin.getConfig().getInt("Config.Spawn" + id + ".Y");
        int sz = plugin.getConfig().getInt("Config.Spawn" + id + ".Z");
        int minplayers = plugin.getConfig().getInt("Config.Players.Min");
        Location startpoint = new Location(player.getWorld(), sx, sy, sz);

        player.teleport(startpoint);
        disableMovement(player);
        
        if(id == minplayers && !countdownstarted)
        {
        startRace(player);
        countdownstarted = true;
        }
        else if(id < minplayers)
        {
            plugin.getServer().broadcastMessage(Prefix + "§a" + id + " §6out of the required " + "§a" + minplayers + " §6competing. Waiting for more to join...");
        }

    }
    public static boolean racing = false;
    public static ArrayList<Player> racers = new ArrayList<>();

    //Adding all online players to the ArrayList


    public void startRace(Player p)
    {
    int minplayers = plugin.getConfig().getInt("Config.Players.Min");
    int maxplayers = plugin.getConfig().getInt("Config.Players.Max");
    int countto = plugin.getConfig().getInt("Config.Countdown");
    p.sendMessage(Prefix + "§6" + minplayers + " out of a maximum of " + maxplayers + ChatColor.GOLD + " have been gathered." + ChatColor.GREEN + " Starting race!");

    
    startCounter(p, countto);
    }

    
    public static Player getPlayerInPlace(int place)
    {

        List<Double> playerScores = new ArrayList<>();    

        for(Player p : Bukkit.getOnlinePlayers())
        {
        double val = p.getMetadata("playerScore").get(0).asDouble();
        playerScores.add(val);
        }
        
        Collections.sort(playerScores);
        
        Player toreturn = null;
        boolean found = false;
        
        for(Player p : Bukkit.getOnlinePlayers())
        {
            double val = p.getMetadata("playerScore").get(0).asInt();
            if(val == playerScores.get(place - 1) && !found)
            {
            toreturn = p;
            found = true;
            }
            else if(val == playerScores.get(place - 1) && found)
            {
            p.setMetadata("playerScore", new FixedMetadataValue(plugin, val - 0.5));
            }
        }
        
        return toreturn;
    }


    public void placePlayers()
    {
                
        String[] Placement;
        
        int size = Bukkit.getOnlinePlayers().length;
        
        Placement = new String[size];
        //Bukkit.getServer().broadcastMessage("§bCreated string with size of: " + size);

        //plugin.getServer().broadcastMessage("§3---------- §b§l CURRENT PLACING §3----------");
            for (int i = 0; i < (Bukkit.getOnlinePlayers().length); i++)
            {
            Placement[i] = getPlayerInPlace(i + 1).getName();
            }
           
        /*    plugin.getServer().broadcastMessage("§e§oFirst place: §c§o" + Placement[0]);
            plugin.getServer().broadcastMessage("§7§oSecond place: §c§o" + Placement[1]);
            plugin.getServer().broadcastMessage("§6§oThird place: §c§o" + Placement[2]);
            plugin.getServer().broadcastMessage("§9§oFourth place: §c§o" + Placement[3]);
            plugin.getServer().broadcastMessage("§3§oFifth place: §c§o" + Placement[4]);
            plugin.getServer().broadcastMessage("§5§oSixth place: §c§o" + Placement[5]);
            plugin.getServer().broadcastMessage("§5§oSeventh place: §c§o" + Placement[6]);
            plugin.getServer().broadcastMessage("§5§oEighth place: §c§o" + Placement[7]);

        plugin.getServer().broadcastMessage("§3---------------------------------------");
        */
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard board = manager.getNewScoreboard();
            board.clearSlot(DisplaySlot.SIDEBAR);
            Objective objective = board.registerNewObjective("test", "playerplaces");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName("§6Player §9Places");

        for(Player p : plugin.getServer().getOnlinePlayers())
            {
                String dispname = getPlaceColor(p.getWorld().getPlayers().indexOf(p)) + p.getName();
            if(p.getName().length() > 14)
            {
                dispname = getPlaceColor(p.getWorld().getPlayers().indexOf(p)) + p.getName().substring(0, 11) + "...";
            }


            Score score = objective.getScore(dispname);


            //p.sendMessage(Main.Prefix + "§5§nApplying the score: " + inc);
            score.setScore(PlayerMoveListener.evalPlace(p));


                p.setScoreboard(board);
        }
    }

    private String getPlaceColor(int inc)
    {
        if(inc == 0)
        {
            return "§c";
        }
        else if(inc == 1)
        {
            return "§b";
        }
        else if(inc == 2)
        {
            return "§a";
        }
        else if(inc == 3)
        {
            return "§e";
        }
        else if(inc == 4)
        {
            return "§6";
        }
        else if(inc == 5)
        {
            return "§d";
        }
        else if(inc == 6)
        {
            return "§5";
        }
        else if(inc == 7)
        {
            return "§9";
        }
        
        return "§1";  
    }

    public void generateTags()
    {
            String[] Placement = new String[] { "--", "--", "--", "--", "--", "--", "--", "--" };

        //plugin.getServer().broadcastMessage("§3---------- §b§l CURRENT PLACING §3----------");
            /*Placement[0] = getPlayerInPlace(1).getName();
            Placement[1] = getPlayerInPlace(2).getName();
            Placement[2] = getPlayerInPlace(3).getName();
            Placement[3] = getPlayerInPlace(4).getName();
            Placement[4] = getPlayerInPlace(5).getName();
            Placement[5] = getPlayerInPlace(6).getName();
            Placement[6] = getPlayerInPlace(7).getName();
            Placement[7] = getPlayerInPlace(8).getName();*/

                ScoreboardManager manager = Bukkit.getScoreboardManager();
                Scoreboard board = manager.getNewScoreboard();
                board.clearSlot(DisplaySlot.BELOW_NAME);
                Objective objective = board.registerNewObjective("test", "placeBelowName");
                objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
                
        for (int i = 7; i > 0; i--) 
        {
            for(Player on : Bukkit.getOnlinePlayers())
            {
                if(on.getName().equals(Placement[i]))
                {
                    Score score = objective.getScore(getPlaceColor(i) + "Score: ");
                    //p.sendMessage(Main.Prefix + "§5§nApplying the score: " + inc);
                    score.setScore(on.getMetadata("playerLap").get(0).asInt());
                }
            }
        }
    
        for(Player p : Bukkit.getOnlinePlayers())
        {
            p.setScoreboard(board);
        }
    }    

}