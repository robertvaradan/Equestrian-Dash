package com.colonelhedgehog.equestriandash.api.powerup.common;

import com.colonelhedgehog.equestriandash.api.entity.Racer;
import com.colonelhedgehog.equestriandash.api.powerup.Powerup;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Item;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ColonelHedgehog on 10/16/15.
 */
public class JumpPowerup implements Powerup
{
    @Override
    public ItemStack getItem()
    {
        FileConfiguration config = EquestrianDash.plugin.getConfig();

        ItemStack icon = new ItemStack(Material.getMaterial(config.getString("Powerups.Jump.Material")), config.getInt("Powerups.Jump.Amount"));
        ItemMeta iconMeta = icon.getItemMeta(); // Getting its meta.
        iconMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', EquestrianDash.plugin.getConfig().getString("Powerups.Jump.Title"))); // Setting its display name to a predefined string in the config.

        return icon;
    }

    private String getMessage()
    {
        return EquestrianDash.Prefix + "§aYou used a " + this.getItem().getItemMeta().getDisplayName() + "§a!";
    }

    @Override
    public void doOnRightClick(Racer racer, Action action)
    {
        racer.getPlayer().sendMessage(getMessage());
        FileConfiguration config = EquestrianDash.plugin.getConfig();
        // This will be performed in the event that you right-click with the item.
        double x = config.getDouble("Powerups.Jump.Velocity.MultiplyX");
        double y = config.getDouble("Powerups.Jump.Velocity.MultiplyY");
        double z = config.getDouble("Powerups.Jump.Velocity.MultiplyZ");
        double plus_y = config.getDouble("Powerups.Jump.Velocity.AddY");
        
        Horse h = racer.getHorse();
        h.setVelocity(h.getLocation().getDirection().multiply(new Vector(x, y, z)).add(new Vector(0, plus_y, 0)));
        racer.getPlayer().setVelocity(racer.getPlayer().getLocation().getDirection().multiply(new Vector(2, 1, 2)).add(new Vector(0, 1.5, 0)));
        racer.getPlayer().playSound(racer.getPlayer().getLocation(), Sound.BAT_HURT, 7, 1);

        ItemStack stack = racer.getPlayer().getItemInHand();
        ItemStack thro = stack.clone();
        thro.setAmount(1);

        if(stack.getAmount() == 1)
        {
            racer.getPlayer().getInventory().clear();
            return;
        }

        stack.setAmount(stack.getAmount() - 1);

        racer.getPlayer().getInventory().setItem(0, stack);
    }

    // For reference's sake:

    @Override
    public void doOnLeftClick(Racer racer, Action action)
    {
        // This will be performed in the event that you left-click with the item.
    }

    @Override
    public void doOnDrop(Racer racer, Item dropped)
    {
        // This will be performed in the event that you drop the item.
    }

    @Override
    public void doOnRightClickRacer(Racer racer, Racer clicked)
    {
        // This will be performed in the event that you right-click someone with the item.
    }

    @Override
    public void doOnLeftClickRacer(Racer racer, Racer clicked)
    {
        // This will be performed in the event that you left-click someone with the item.
    }

    @Override
    public void doOnPickup(Racer racer, Racer dropper, Item item)
    {
        // This will be performed in the event that you pick up the item.
    }

    // For not reference's sake:

    /*@Override
    public String getMessage()
    {
        // This is executed when a message needs to be sent to the racer.
        return EquestrianDash.Prefix + "§aYou used a §bLaunch Powerup§a!"; // Consistancy is cool, yo.
    }*/

    @Override
    public double getChance(int rank)
    {
        return (rank / EquestrianDash.plugin.getConfig().getDouble("Powerups.Jump.Chance")); // Chance that when we hit an item-box, this will be an option.
    }

    @Override
    public List<ActionType> cancelledEvents()
    {
        List<ActionType> actions = new ArrayList<>();
        actions.add(ActionType.ALL);
        // ALL types of actions will be cancelled.
        return actions;
    }
}
