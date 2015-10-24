/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.colonelhedgehog.equestriandash.events;

import com.colonelhedgehog.equestriandash.api.powerup.Powerup;
import com.colonelhedgehog.equestriandash.api.powerup.PowerupsRegistry;
import com.colonelhedgehog.equestriandash.assets.handlers.RacerHandler;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * @author Robert
 */
public class PlayerInteractEntityListener implements Listener
{
    public static EquestrianDash plugin = EquestrianDash.plugin;

    @EventHandler
    public void onBox(final PlayerInteractEntityEvent event)
    {
        /*if (event.getRightClicked() instanceof EnderCrystal && !EquestrianDash.getCooldownHandler().isCooling(event.getPlayer()) && !racerHandler.getRacer(event.getPlayer()).inventoryIsSpinning())
        {
            event.getPlayer().getInventory().clear();
            giveReward(event.getPlayer(), event.getRightClicked(), event.getRightClicked().getLocation().getBlockX(), event.getRightClicked().getLocation().getBlockY(), event.getRightClicked().getLocation().getBlockZ());
            event.getRightClicked().remove();
            event.getRightClicked().getWorld().playEffect(event.getRightClicked().getLocation(), Effect.STEP_SOUND, 20);


        }
        else */if (event.getRightClicked() instanceof Player)
        {
            Player victim = (Player) event.getRightClicked();
            Player hurter = event.getPlayer(); // not really lol
            PowerupsRegistry powerupsRegistry = plugin.getPowerupsRegistry();
            RacerHandler racerHandler = plugin.getRacerHandler();

            if (hurter.getItemInHand() != null)
            {
                for (Powerup pow : powerupsRegistry.getPowerups())
                {
                    if (pow.getItem().getType() == hurter.getItemInHand().getType() && pow.getItem().getDurability() == hurter.getItemInHand().getDurability())
                    {
                        pow.doOnRightClickRacer(racerHandler.getRacer(hurter), racerHandler.getRacer(victim));
                        if (pow.cancelledEvents().contains(Powerup.ActionType.ALL) || pow.cancelledEvents().contains(Powerup.ActionType.RIGHT_CLICK_ENTITY))
                        {
                            event.setCancelled(true);
                        }
                        return;
                    }
                }
            }
        }
    }

}
