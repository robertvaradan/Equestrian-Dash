package com.ColonelHedgehog.Dash.API.Powerup;

import org.bukkit.entity.Item;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import com.ColonelHedgehog.Dash.API.Entity.Racer;

import java.util.List;

/**
 * Created by ColonelHedgehog on 11/9/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public interface Powerup
{
    public enum ActionType
    {
        RIGHT_CLICK, LEFT_CLICK, RIGHT_CLICK_ENTITY, LEFT_CLICK_ENTITY, DROP, PICKUP, ALL
    }
    // The itemstack used.
    public ItemStack getItem();

    // Right-click action.
    public void doOnRightClick(Racer racer, Action action);

    // Left-click action.
    public void doOnLeftClick(Racer racer, Action action);

    // Item dropped (default drop button is Q) action.
    public void doOnDrop(Racer racer, Item dropped);

    // Right-click racer action.
    public void doOnRightClickRacer(Racer racer, Racer clicked);

    // Left-click (attack) racer action.
    public void doOnLeftClickRacer(Racer racer, Racer clicked);

    // Pickup item (after it is dropped) action.
    public void doOnPickup(Racer racer, Racer dropper, Item item);

    // The message sent when you get the item.
    // Removed: public String getMessage();

    // The chance (1/#) that the item will appear in the hotbar while it is being "spun."
    public double getChance(int rank);

    // Disabled -> // public double decreasePerRank(); // Depending on the rank , how much should the chance go down each time?

    // All events to ignore (cancel) when you perform a "doOn" action with the item.
    public List<ActionType> cancelledEvents();
}
