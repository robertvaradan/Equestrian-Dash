package com.colonelhedgehog.equestriandash.api.powerup.common;

import com.colonelhedgehog.equestriandash.api.entity.Racer;
import com.colonelhedgehog.equestriandash.api.powerup.Powerup;
import com.colonelhedgehog.equestriandash.assets.handlers.RacerHandler;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ColonelHedgehog on 12/28/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class TrollkinPowerup implements Powerup
{

    private EquestrianDash plugin = EquestrianDash.getInstance();

    @Override
    public ItemStack getItem()
    {
        ItemStack icon = new ItemStack(Material.getMaterial(EquestrianDash.plugin.getConfig().getString("Powerups.Trollkin.Material")));
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', EquestrianDash.plugin.getConfig().getString("Powerups.Trollkin.Title")));
        icon.setItemMeta(meta);

        return icon;
    }

    private String getMessage()
    {
        return EquestrianDash.Prefix + "§aYou used a " + this.getItem().getItemMeta().getDisplayName() + "§a!";
    }

    @Override
    public void doOnRightClick(Racer racer, Action action)
    {
        racer.getPlayer().getInventory().clear();
        int dur = EquestrianDash.plugin.getConfig().getInt("Powerups.Trollkin.Effects.Length");
        int mag = EquestrianDash.plugin.getConfig().getInt("Powerups.Trollkin.Effects.Power");
        boolean ph = EquestrianDash.plugin.getConfig().getBoolean("Powerups.Trollkin.Effects.PumpkinHelmet");

        racer.getPlayer().sendMessage(getMessage());

        RacerHandler racerHandler = plugin.getRacerHandler();

        racerHandler.getPlayers().stream().filter(p -> !p.equals(racer.getPlayer())).forEach(p -> {
            p.playSound(p.getLocation(), Sound.GHAST_MOAN, 3, 2);
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, dur, mag));

            if (ph)
            {
                p.getInventory().setHelmet(new ItemStack(Material.PUMPKIN));
            }
        });

        if(ph)
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    for(Player p : racerHandler.getPlayers())
                    {
                        p.getInventory().setHelmet(null);
                    }
                }
            }.runTaskLater(EquestrianDash.plugin, dur);
        }
    }

    @Override
    public void doOnLeftClick(Racer racer, Action action)
    {

    }

    @Override
    public void doOnDrop(Racer racer, Item dropped)
    {

    }

    @Override
    public void doOnRightClickRacer(Racer racer, Racer clicked)
    {

    }

    @Override
    public void doOnLeftClickRacer(Racer racer, Racer clicked)
    {

    }

    @Override
    public void doOnPickup(Racer racer, Racer dropper, Item item)
    {

    }

    @Override
    public double getChance(int rank)
    {
        return rank / EquestrianDash.plugin.getConfig().getInt("Powerups.Trollkin.Chance");
    }

    @Override
    public List<ActionType> cancelledEvents()
    {
        List<ActionType> actions = new ArrayList<>();
        actions.add(ActionType.RIGHT_CLICK);
        return actions;
    }
}
