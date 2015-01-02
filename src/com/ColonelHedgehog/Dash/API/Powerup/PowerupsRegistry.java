package com.ColonelHedgehog.Dash.API.Powerup;

import com.ColonelHedgehog.Dash.API.Lang.PowerupAlreadyRegisteredException;
import com.ColonelHedgehog.Dash.Core.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ColonelHedgehog on 11/9/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class PowerupsRegistry
{
    private List<Powerup> powerups;
    public PowerupsRegistry()
    {
        this.powerups = new ArrayList<>();
    }
    public List<Powerup> getPowerups()
    {
        return powerups;
    }
    public void registerPowerup(Powerup powerup)
    {
        if(powerups.contains(powerup))
        {
            String name = (powerup.getItem().hasItemMeta() && powerup.getItem().getItemMeta().hasDisplayName() ? powerup.getItem().getItemMeta().getDisplayName() : "Unnamed");
            try
            {
                throw new PowerupAlreadyRegisteredException("Powerup \"" + name + "\" has already been registed. Please check code for duplicate register statements.");
            }
            catch (PowerupAlreadyRegisteredException e)
            {
                Main.plugin.getLogger().severe(e.getMessage());
                e.printStackTrace();
            }
        }
        powerups.add(powerup);
    }
}
