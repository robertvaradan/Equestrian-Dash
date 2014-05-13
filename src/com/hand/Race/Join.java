/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import static com.hand.Race.Inter.addLore;
import static com.hand.Race.ItemBox.setName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import static org.bukkit.entity.EntityType.HORSE;
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
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 *
 * @author Robert
 */
public class Join implements Listener
{
    public static Main plugin;
    public Join(Main ins)
    {
        Join.plugin = ins;
    }

    public static Scoreboard placeBoard = null; //Creates a scoreboard called timerBoard(You will see what thats used for later)
    public static Objective placeObj = null; // Same as above but it creates a objective called timerObj
    public static Objective pl[]; //Creates a objective called o

    public static boolean RaceEnded = false;
    public static String Prefix = "§9§l[§3Equestrian §bDash§9§l]§r: ";
    
    
    public static void disableMovement(final Player p/*, Horse horse*/)
    {
        if(p.getGameMode() != GameMode.CREATIVE)
        {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 999999999));        
        }
        
        
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
        horse.setOwner(p);
        horse.setPassenger(p);
        //int random1 = (int )(Math.random() * 7 + 1);
        //int random2 = (int )(Math.random() * 10 + 1);
        
        //online.sendMessage("Horse is " + horse + " at " + horse.getLocation());        
        horse.setMaxHealth(20.0);
        horse.setJumpStrength(0.75);
        if(p.getGameMode() != GameMode.CREATIVE)
        {
                horse.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 999999999));            
        }
            cancel(); //Cancels the timer
        }
      
    }.runTaskTimer(plugin, 20L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);
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
    
    if(res == 1 && p.hasPermission("EquestrianDash.HorseColors.White"))
    {
        horse.setColor(Horse.Color.WHITE);
    }
    else if(res == 2 && p.hasPermission("EquestrianDash.HorseColors.Black"))
    {
        horse.setColor(Horse.Color.BLACK);                
    }
    else if(res == 3 && p.hasPermission("EquestrianDash.HorseColors.Brown"))
    {
        horse.setColor(Horse.Color.BROWN);                                
    }
    else if(res == 4 && p.hasPermission("EquestrianDash.HorseColors.Chestnut"))
    {
        horse.setColor(Horse.Color.CHESTNUT);                                                
    }
    else if(res == 5 && p.hasPermission("EquestrianDash.HorseColors.Creamy"))
    {
        horse.setColor(Horse.Color.CREAMY);                                                
    }
    else if(res == 6 && p.hasPermission("EquestrianDash.HorseColors.Dark_Brown"))
    {
        horse.setColor(Horse.Color.DARK_BROWN);                                                           
    }
    else if(res == 7 && p.hasPermission("EquestrianDash.HorseColors.Gray"))
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
    if(res == 1 && p.hasPermission("EquestrianDash.HorseStyles.Black_Dots"))
    {
        horse.setStyle(Horse.Style.BLACK_DOTS);
    }
    else if(res == 2 && p.hasPermission("EquestrianDash.HorseStyles.None"))
    {
        horse.setStyle(Horse.Style.NONE);                
    }
    else if(res == 3 && p.hasPermission("EquestrianDash.HorseStyles.White"))
    {
        horse.setStyle(Horse.Style.WHITE);                                
    }
    else if(res == 4 && p.hasPermission("EquestrianDash.HorseStyles.Whitefield"))
    {
        horse.setStyle(Horse.Style.WHITEFIELD);                                                
    }
    else if(res == 5 && p.hasPermission("EquestrianDash.HorseStyles.White_Dots"))
    {
        horse.setStyle(Horse.Style.WHITE_DOTS);                                                
    }
    else if(res == 6 && p.hasPermission("EquestrianDash.HorseStyles.Skeleton"))
    {
        horse.setVariant(Horse.Variant.SKELETON_HORSE);                                                           
    }
    else if(res == 7 && p.hasPermission("EquestrianDash.HorseStyles.Zombie"))
    {
        horse.setVariant(Horse.Variant.UNDEAD_HORSE);                                                           
    }
    else
    {
    horse.setStyle(Horse.Style.NONE);                        
    }
}

public static void startCounter(final Player p, Horse h, final int countTo)
{
    final Objective o; //Creates a objective called o
    Scoreboard timerBoard = null; //Creates a scoreboard called timerBoard(You will see what thats used for later)
    Objective timerObj = null; // Same as above but it creates a objective called timerObj
        Scoreboard board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        o = board.registerNewObjective("timer", "dummy"); //Registering the objective needed for the timer
        o.setDisplayName("§9§lEquestrian §b§lDash"); // Setting the title for the scoreboard. This would look like: TCGN | Walls
        o.setDisplaySlot(DisplaySlot.SIDEBAR); //Telling the scoreboard where to display when we tell it to display

        timerBoard = board; //Setting timerBoard equal to board.
        timerObj = o; //Setting timerObj equal to o. This makes it so we can access it by typing plugin.timerObj
//int count = 60; //Time in seconds (Can reference config)
    if(countTo != 0)
    {
        count = countTo;
    }
    
Bukkit.broadcastMessage(Prefix + "§3Game starting in §b" + countTo + " §3seconds..");
final Score score = o.getScore(Bukkit.getOfflinePlayer("§3Time: ")); //Making a offline player called "Time:" with a green name and adding it to the scoreboard

        for(Player online : Bukkit.getServer().getOnlinePlayers()) 
        {
            online.setScoreboard(timerBoard);
        }

new BukkitRunnable() {
  @Override
  public void run() 
  {
      if(p.getServer().getOnlinePlayers().length < plugin.getConfig().getInt("Path.Players.Min"))
        {
        cancel();
        plugin.getServer().broadcastMessage(Prefix + "§6§oToo many players have left. Waiting for more players to join...");
        Join.racing = false;
        count = countTo;
        }
      
      if(count <= 1) 
      {
    
          //Start game method
        int fx = plugin.getConfig().getInt("Path.Fw.X");
        int fy = plugin.getConfig().getInt("Path.Fw.Y");
        int fz = plugin.getConfig().getInt("Path.Fw.Z");
        Location flareloc = new Location(p.getWorld(), fx, fy, fz);
          
                Firework fw = p.getWorld().spawn(flareloc, Firework.class);
                FireworkMeta data = fw.getFireworkMeta();
                //p.sendMessage("Firework spawned at " + flareloc);
                data.addEffects(FireworkEffect.builder().withColor(Color.GREEN).with(Type.BALL_LARGE).build());
                data.setPower(1);
                fw.setFireworkMeta(data);
                Join.racing = true;
                Bukkit.broadcastMessage(Prefix + ChatColor.GREEN + "§l§oGO!");
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3, 2);
                o.setDisplaySlot(null);
        for(Player online : Bukkit.getServer().getOnlinePlayers()) 
        {
        enableMovement(online, (Horse) online.getVehicle());
        online.setMetadata("lastLocX", new FixedMetadataValue(plugin, p.getLocation().getX()));
        online.setMetadata("lastLocY", new FixedMetadataValue(plugin, p.getLocation().getY()));
        online.setMetadata("lastLocZ", new FixedMetadataValue(plugin, p.getLocation().getZ()));
        online.setMetadata("lastLocPitch", new FixedMetadataValue(plugin, p.getLocation().getPitch()));
        online.setMetadata("lastLocYaw", new FixedMetadataValue(plugin, p.getLocation().getYaw()));
        
                
        this.cancel(); //Cancels the timer

        }
        
            new BukkitRunnable() 
            {

                @Override
                public void run() 
                {
                    placePlayers();
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
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3, 1);
        }
      }
  }
}.runTaskTimer(plugin, 0L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);
}
   
    
    @EventHandler
    public static void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        
        racers.add(player);
        player.setMetadata("trackPlace", new FixedMetadataValue(plugin, 1));
        player.setMetadata("pCooldown", new FixedMetadataValue(plugin, false));
        player.setMetadata("playerLap", new FixedMetadataValue(plugin, 0));
        player.setMetadata("markerEditorEnabled", new FixedMetadataValue(plugin, false));
        player.setMetadata("colorKey", new FixedMetadataValue(plugin, -1));
        player.setMetadata("patternKey", new FixedMetadataValue(plugin, -1));
        player.setMetadata("choosingColor", new FixedMetadataValue(plugin, false));
        player.setMetadata("choosingStyle", new FixedMetadataValue(plugin, false));
        
        event.setJoinMessage(Prefix + "" + ChatColor.AQUA + "" + player.getDisplayName() + " §3is now competing!");
        int players = player.getServer().getOnlinePlayers().length;
        player.sendMessage(Prefix + ChatColor.GOLD + "Joined! §c§l§oSneak to customize horse colors!");
        int maxplayers = plugin.getConfig().getInt("Path.Players.Max");
        //Player[] players = player.getServer().getOnlinePlayers().equals();
        //player.sendMessage("§6Thanks for joining!");
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
    
    public static void mountPlayer(Player player, int id)
    {
        player.sendMessage(Prefix + ChatColor.GOLD + "Mounting. You are racer number: " + ChatColor.BLUE + "1");
        player.setMetadata("playerID", new FixedMetadataValue(plugin, id));

        int sx = plugin.getConfig().getInt("Path.Spawn" + id + ".X");
        int sy = plugin.getConfig().getInt("Path.Spawn" + id + ".Y");
        int sz = plugin.getConfig().getInt("Path.Spawn" + id + ".Z");
        int minplayers = plugin.getConfig().getInt("Path.Players.Min");
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
    public static ArrayList<Entity> horses = new ArrayList<>();
 
    //Adding all online players to the ArrayList


    public static void startRace(Player p)
    {
    int minplayers = plugin.getConfig().getInt("Path.Players.Min");
    int maxplayers = plugin.getConfig().getInt("Path.Players.Max");
    int countto = plugin.getConfig().getInt("Path.Countdown");
    p.sendMessage(Prefix + "§6" + minplayers + " out of a maximum of " + maxplayers + ChatColor.GOLD + " have been gathered." + ChatColor.GREEN + " Starting race!");

    
    startCounter(p, (Horse) p.getVehicle(), countto);    
    }

    
    public static Player getPlayerInPlace(int place)
    {
        int playerplace = 0;    
        
        List<Double> playerScores = new ArrayList<Double>();    

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

    public static int findLargest(int[] numbers)
    {  
    int largest = numbers[0];  
    for(int i = 1; i < numbers.length; i++)
    {  
        if(numbers[i] > largest)
        {  
            largest = numbers[i];  
        }  
    }  
    return largest;
}
    
    
    public static void placePlayers()
    {
                
        String[] Placement = new String[] { "--", "--", "--", "--", "--", "--", "--", "--" };

        //plugin.getServer().broadcastMessage("§3---------- §b§l CURRENT PLACING §3----------");
            Placement[0] = getPlayerInPlace(1).getDisplayName();
            Placement[1] = getPlayerInPlace(2).getDisplayName();
            Placement[2] = getPlayerInPlace(3).getDisplayName();
            Placement[3] = getPlayerInPlace(4).getDisplayName();
            Placement[4] = getPlayerInPlace(5).getDisplayName();
            Placement[5] = getPlayerInPlace(6).getDisplayName();
            Placement[6] = getPlayerInPlace(7).getDisplayName();
            Placement[7] = getPlayerInPlace(8).getDisplayName();
            
        /*    plugin.getServer().broadcastMessage("§e§oFirst place: §c§o" + Placement[0]);
            plugin.getServer().broadcastMessage("§7§oSecond place: §c§o" + Placement[1]);
            plugin.getServer().broadcastMessage("§6§oThird place: §c§o" + Placement[2]);
            plugin.getServer().broadcastMessage("§9§oFourth place: §c§o" + Placement[3]);
            plugin.getServer().broadcastMessage("§3§oFifth place: §c§o" + Placement[4]);
            plugin.getServer().broadcastMessage("§5§oSixth place: §c§o" + Placement[5]);
            plugin.getServer().broadcastMessage("§5§oSeventh place: §c§o" + Placement[6]);
            plugin.getServer().broadcastMessage("§5§oEight place: §c§o" + Placement[7]);

        plugin.getServer().broadcastMessage("§3---------------------------------------");
        */
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        board.clearSlot(DisplaySlot.SIDEBAR);
        Objective objective = board.registerNewObjective("test", "playerplaces");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§6Player §9Places");
        int inc = 1;
        for(Player p : plugin.getServer().getOnlinePlayers())
        {
        String editeddisp = Placement[inc - 1].substring(0, Math.min(Placement[inc - 1].length(), 11));
        String suff = "";
        if(Placement[inc - 1].length() > 16)
        {
            suff = "...";
        }
        

        Score score = objective.getScore(Bukkit.getOfflinePlayer(getPlaceColor(inc) + editeddisp + suff));
        //p.sendMessage("§5§nApplying the score: " + inc);
        score.setScore(inc);
        inc++;
        
        p.setScoreboard(board);        
        }
    }

    public static String getPlaceColor(int inc)
    {
        if(inc == 1)
        {
            return "§e";
        }
        else if(inc == 2)
        {
            return "§7";
        }
        else if(inc == 3)
        {
            return "§6";
        }
        else if(inc == 4)
        {
            return "§9";
        }
        else if(inc == 5)
        {
            return "§3";
        }
        else if(inc == 6)
        {
            return "§5";
        }
        else if(inc == 7)
        {
            return "§5";
        }
        else if(inc == 8)
        {
            return "§5";
        }
        
        return "§1";  
    }

    public static void generateTags()
    {
            String[] Placement = new String[] { "--", "--", "--", "--", "--", "--", "--", "--" };

        //plugin.getServer().broadcastMessage("§3---------- §b§l CURRENT PLACING §3----------");
            Placement[0] = getPlayerInPlace(1).getDisplayName();
            Placement[1] = getPlayerInPlace(2).getDisplayName();
            Placement[2] = getPlayerInPlace(3).getDisplayName();
            Placement[3] = getPlayerInPlace(4).getDisplayName();
            Placement[4] = getPlayerInPlace(5).getDisplayName();
            Placement[5] = getPlayerInPlace(6).getDisplayName();
            Placement[6] = getPlayerInPlace(7).getDisplayName();
            Placement[7] = getPlayerInPlace(8).getDisplayName();

                ScoreboardManager manager = Bukkit.getScoreboardManager();
                Scoreboard board = manager.getNewScoreboard();
                board.clearSlot(DisplaySlot.BELOW_NAME);
                Objective objective = board.registerNewObjective("test", "placeBelowName");
                objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
                
        for (int i = 0; i < 7; i++) 
        {
            for(Player on : Bukkit.getOnlinePlayers())
            {
                if(on.getDisplayName().equals(Placement[i]))
                {
                    Score score = objective.getScore(Bukkit.getOfflinePlayer(getPlaceColor(i) + "Place: "));
                    //p.sendMessage("§5§nApplying the score: " + inc);
                    score.setScore(i + 1);                    
                }
            }
        }
    
        for(Player p : Bukkit.getOnlinePlayers())
        {
            p.setScoreboard(board);
        }
    }
}