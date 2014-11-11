package tk.ColonelHedgehog.Dash.API.Powerup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ColonelHedgehog on 11/9/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class PowerupsRegistery
{
    private List<Powerup> powerups;
    public PowerupsRegistery()
    {
        this.powerups = new ArrayList<>();
    }
    public List<Powerup> getPowerups()
    {
        return powerups;
    }
    public void registerPowerup(Powerup powerup)
    {
        powerups.add(powerup);
    }
}
