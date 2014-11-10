package tk.ColonelHedgehog.Dash.API.Powerup;

import org.bukkit.inventory.ItemStack;
import tk.ColonelHedgehog.Dash.API.Entity.Racer;

/**
 * Created by ColonelHedgehog on 11/9/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public interface Powerup
{
    public int getItemAmountReduction();

    public ItemStack getItem();

    public void doOnRightClick(Racer racer);

    public void doOnLeftClick(Racer racer);

    public void doOnDrop(Racer racer);

    public String getMessage();
}
