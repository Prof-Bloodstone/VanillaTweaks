package me.machinemaker.vanillatweaks.largerphantoms;

import me.machinemaker.vanillatweaks.BaseModule;
import me.machinemaker.vanillatweaks.VanillaTweaks;
import org.apache.commons.lang3.Range;
import org.bukkit.Statistic;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class LargerPhantoms extends BaseModule implements Listener {

    public LargerPhantoms(VanillaTweaks plugin) {
        super(plugin, config -> config.largerPhantoms);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntityType() != EntityType.PHANTOM) return;
        Phantom phantom = (Phantom) event.getEntity();
        double closest = Double.MAX_VALUE;
        Player player = null;
        for (Player tempPlayer : event.getEntity().getWorld().getPlayers()) {
            double tempDistance = event.getLocation().distanceSquared(tempPlayer.getLocation());
            if (tempDistance < closest) {
                closest = tempDistance;
                player = tempPlayer;
            }
        }
        if (player == null) return;
        int ticksSinceSleep = player.getStatistic(Statistic.TIME_SINCE_REST);
        int size;
        double maxHealth;
        double movementSpeed;
        double followRange;
        double attackDamage;
        if (ticksSinceSleep < 140000) return;
        else if (Range.between(144000, 216000).contains(ticksSinceSleep)) {
            size = 3;
            maxHealth = 25;
            movementSpeed = 1;
            followRange = 20;
            attackDamage = 15;
        }
        else if (Range.between(216000, 288000).contains(ticksSinceSleep)) {
            size = 5;
            maxHealth = 30;
            movementSpeed = 1.3;
            followRange = 24;
            attackDamage = 17;
        }
        else if (Range.between(288000, 2400000).contains(ticksSinceSleep)) {
            size = 7;
            maxHealth = 35;
            movementSpeed = 1.6;
            followRange = 28;
            attackDamage = 20;
        }
        else {
            size = 20;
            maxHealth = 100;
            movementSpeed = 2;
            followRange = 50;
            attackDamage = 30;
        }
        phantom.setSize(size);
        phantom.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        phantom.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(movementSpeed);
        phantom.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(followRange);
        phantom.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(attackDamage);
    }

    @Override
    public void register() {
        this.registerEvents(this);
    }

    @Override
    public void unregister() {
        this.unregisterEvents(this);
    }
}
