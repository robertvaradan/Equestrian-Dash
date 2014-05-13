/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import static com.hand.Race.ItemBox.giveReward;
import static com.hand.Race.Join.Prefix;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Robert
 */
public class Move implements Listener
{
    public static Main plugin;
    public static Player[] RPlace;
    public Move(Main ins)
    {
        Move.plugin = ins;
    }

    @EventHandler
    public static void onMove(PlayerMoveEvent event)
    {
        Player p = event.getPlayer();
        //p.sendMessage(Prefix + "§bYour place is: " + evalPlace(p));
        p.setMetadata("playerScore", new FixedMetadataValue(plugin, evalPlace(p)));
        
        //if(p.getVehicle() != null)
        //{
        if(p.getVehicle() instanceof Horse)
        {
            updateRespawn(p);
        }
//}
        
                List<Entity> nearby = p.getNearbyEntities(0.25,0.25,0.5);
                for(Entity cry : nearby)
                {
                    if(cry instanceof EnderCrystal && p.getGameMode() != GameMode.CREATIVE)
                    {
                    //p.sendMessage("Debug: Crystal nearby.");
                    
                        if (!p.getMetadata("pCooldown").get(0).asBoolean()) 
                        {
                            //p.sendMessage("Debug: Not cooling down.");

                            giveReward(p, cry, cry.getLocation().getBlockX(), cry.getLocation().getBlockY(), cry.getLocation().getBlockZ());
                            p.setMetadata("pCooldown", new FixedMetadataValue(plugin, true));
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
    
    public static ArrayList<Integer> playerScores = new ArrayList<>(); // You can add all the ints in the world to this arraylist.

    
    public static void updateRespawn(Player p)
    {
        Entity e = p.getVehicle();
        Block block = e.getLocation().getBlock().getRelative(BlockFace.DOWN);

        Location loc1 = e.getLocation().add(1,-1,1);
        Location loc2 = e.getLocation().add(-1,-1,1);
        Location loc3 = e.getLocation().add(1,-1,-1);
        Location loc4 = e.getLocation().add(-1,-1,-1);
        Location loc5 = e.getLocation().add(0,-1,-1);
        Location loc6 = e.getLocation().add(1,-1,0);
        Location loc7 = e.getLocation().add(-1,-1,0);
        Location loc8 = e.getLocation().add(0,-1,1);
        
        Location tloc1 = e.getLocation().add(2,-1,2);
        Location tloc2 = e.getLocation().add(-2,-1,2);
        Location tloc3 = e.getLocation().add(2,-1,-2);
        Location tloc4 = e.getLocation().add(-2,-1,-2);
        Location tloc5 = e.getLocation().add(0,-1,-2);
        Location tloc6 = e.getLocation().add(2,-1,0);
        Location tloc7 = e.getLocation().add(-2,-2,0);
        Location tloc8 = e.getLocation().add(0,-1,2);

        Location eloc1 = e.getLocation().add(3,-1,3);
        Location eloc2 = e.getLocation().add(-3,-1,3);
        Location eloc3 = e.getLocation().add(3,-1,-3);
        Location eloc4 = e.getLocation().add(-3,-1,-3);
        Location eloc5 = e.getLocation().add(3,-1,-3);
        Location eloc6 = e.getLocation().add(3,-1,0);
        Location eloc7 = e.getLocation().add(-3,-1,0);
        Location eloc8 = e.getLocation().add(0,-1,3);
        if (!block.isLiquid() && block.getType() != Material.AIR && e.getLocation().subtract(0,1,0).getBlock().getType() != Material.AIR && loc1.getBlock().getType() != Material.AIR && loc2.getBlock().getType() != Material.AIR && loc3.getBlock().getType() != Material.AIR  && loc4.getBlock().getType() != Material.AIR && loc5.getBlock().getType() != Material.AIR && loc6.getBlock().getType() != Material.AIR && loc7.getBlock().getType() != Material.AIR && loc8.getBlock().getType() != Material.AIR)
        {
            if(tloc1.getBlock().getType() != Material.AIR && tloc2.getBlock().getType() != Material.AIR && tloc3.getBlock().getType() != Material.AIR  && tloc4.getBlock().getType() != Material.AIR && tloc5.getBlock().getType() != Material.AIR && tloc6.getBlock().getType() != Material.AIR && tloc7.getBlock().getType() != Material.AIR && tloc8.getBlock().getType() != Material.AIR)
            {
            if(eloc1.getBlock().getType() != Material.AIR && eloc2.getBlock().getType() != Material.AIR && eloc3.getBlock().getType() != Material.AIR  && eloc4.getBlock().getType() != Material.AIR && eloc5.getBlock().getType() != Material.AIR && eloc6.getBlock().getType() != Material.AIR && eloc7.getBlock().getType() != Material.AIR && eloc8.getBlock().getType() != Material.AIR)
            {
            if(tloc1.getBlock().getType() != Material.PISTON_MOVING_PIECE && tloc2.getBlock().getType() != Material.PISTON_MOVING_PIECE && tloc3.getBlock().getType() != Material.PISTON_MOVING_PIECE && tloc4.getBlock().getType() != Material.PISTON_MOVING_PIECE && tloc5.getBlock().getType() != Material.PISTON_MOVING_PIECE && tloc6.getBlock().getType() != Material.PISTON_MOVING_PIECE && tloc7.getBlock().getType() != Material.PISTON_MOVING_PIECE && tloc8.getBlock().getType() != Material.PISTON_MOVING_PIECE)
            {
            if(eloc1.getBlock().getType() != Material.PISTON_MOVING_PIECE && eloc2.getBlock().getType() != Material.PISTON_MOVING_PIECE && eloc3.getBlock().getType() != Material.PISTON_MOVING_PIECE  && eloc4.getBlock().getType() != Material.PISTON_MOVING_PIECE && eloc5.getBlock().getType() != Material.PISTON_MOVING_PIECE && eloc6.getBlock().getType() != Material.PISTON_MOVING_PIECE && eloc7.getBlock().getType() != Material.PISTON_MOVING_PIECE && eloc8.getBlock().getType() != Material.PISTON_MOVING_PIECE)
            {
                    //p.sendMessage("§6Stage 1: No air");
            
                    //p.sendMessage("§aStage 3: No lava, saving pos...");
                    p.setMetadata("lastLocX", new FixedMetadataValue(plugin, p.getLocation().getX()));
                    p.setMetadata("lastLocY", new FixedMetadataValue(plugin, p.getLocation().getY()));
                    p.setMetadata("lastLocZ", new FixedMetadataValue(plugin, p.getLocation().getZ()));
                    p.setMetadata("lastLocPitch", new FixedMetadataValue(plugin, p.getLocation().getPitch()));
                    p.setMetadata("lastLocYaw", new FixedMetadataValue(plugin, p.getLocation().getYaw()));
            }
            }
            }
        }
        else
        {
            //p.sendMessage("§6STAGE 1 §4ERROR!");    
        }
        
        if(e.getLocation().subtract(0,1,0).getBlock().getType() == Material.LAVA || e.getLocation().subtract(0,1,0).getBlock().getType() == Material.STATIONARY_LAVA && p.getGameMode() != GameMode.CREATIVE)
        {
            p.setHealth(0.0);
        }
        
        Material mat1 = p.getVehicle().getLocation().add(2,0,0).getBlock().getRelative(BlockFace.EAST).getType();
        Material mat2 = p.getVehicle().getLocation().add(-2,0,0).getBlock().getRelative(BlockFace.WEST).getType();
        Material mat3 = p.getVehicle().getLocation().add(0, 0, 2).getBlock().getRelative(BlockFace.SOUTH).getType();
        Material mat4 = p.getVehicle().getLocation().add(0, 0, -2).getBlock().getRelative(BlockFace.NORTH).getType();
        
        if(mat1 == Material.PISTON_MOVING_PIECE && mat2 == Material.PISTON_MOVING_PIECE && mat3 == Material.PISTON_MOVING_PIECE && mat4 == Material.PISTON_MOVING_PIECE && p.getLocation().getYaw() < 90 && p.getLocation().getYaw() > -90)
        {
            p.sendMessage("§aDebug: Near raceline.");
        }
        else if(mat1 == Material.PISTON_MOVING_PIECE && mat2 == Material.PISTON_MOVING_PIECE && mat3 == Material.PISTON_MOVING_PIECE && mat4 == Material.PISTON_MOVING_PIECE)
        {
            p.setHealth(0.0);
        }
    }
}
   
    
    public static void plCooldown(final Player p)
    {
        new BukkitRunnable() 
        {

              @Override
             public void run() 
            {
              //Start game method
                p.setMetadata("pCooldown", new FixedMetadataValue(plugin, false));            
                cancel(); //Cancels the timer
            }

        }.runTaskTimer(plugin, 100L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);
        
    }
    public static int evalPlace(Player p)
    {
        Location playerLoc = p.getLocation();
        int pX = playerLoc.getBlockX();
        int pY = playerLoc.getBlockY();
        int pZ = playerLoc.getBlockZ();

                    
        for (int x = -7; x <= 7; x ++)
        {
            for (int y = -7; y <= 7; y ++)
            {
                for (int z = -7; z <= 7; z ++)
                {
                Block b = p.getWorld().getBlockAt((int)pX+x, (int)pY+y, (int)pZ+z);
                    if(b.getType() == Material.SIGN || b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)
                    {
                        BlockState state = b.getState();
                        Sign sign = (Sign)state;
                        String signline1 = sign.getLine(0);
                        String signline2 = sign.getLine(1); 
                        String replace = signline2.replace("", "");
                    //p.sendMessage("Debug: Was a sign. " + signline1 + " and " + signline2);
                        
                        
                            
                            
                        if("#".equals(signline1) && isInteger(replace)) 
                        {
                            //p.sendMessage("Debug: Line 2 is int.");
                            int markers = plugin.getConfig().getInt("Path.Editor.Markers");
                            int pllap = p.getMetadata("playerLap").get(0).asInt();
                            
                            int racenum = 0;
                            
                            try
                            {
                            racenum = Integer.parseInt(replace);        
                            }
                            catch (NumberFormatException a)
                            {
                                plugin.getServer().broadcastMessage("§4Couldn't parse §a" + sign.getLine(1) + "§4!");
                                return 0;
                            }
                            
                            if(pllap > 1)
                            {
                            //p.sendMessage("Debug: Lap was bigger than 1.");
                            return racenum + (pllap * markers);    
                            }
                            else
                            {
                            //p.sendMessage("Debug: Lap was smaller than 1.");
                            return racenum;                                
                            }
                        }
                        else
                        {
                            if(!"#".equals(signline1))
                            {
                            //p.sendMessage("Debug: Line 1 was not a #.");
                            }
                            else if(isInteger(signline2))
                            {
                            //p.sendMessage("Debug: Line 2 was not an int.");                                
                            }
                            return 0;
                        }
                    }
                }
            }
        }
        return 0;
    }
    
public static boolean isInteger(String s) {
    try { 
        Integer.parseInt(s); 
    } catch(NumberFormatException e) { 
        //plugin.getServer().broadcastMessage("§6" + s + " §awas not an int.");
        return false; 
    }
    // only got here if we didn't return false
    return true;
}

}
