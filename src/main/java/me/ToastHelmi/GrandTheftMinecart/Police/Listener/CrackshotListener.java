/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ToastHelmi.GrandTheftMinecart.Police.Listener;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import me.ToastHelmi.GrandTheftMinecart.Police.CrimeManager;
import me.ToastHelmi.GrandTheftMinecart.StaticValues.StaticMetaDataValue;
import me.ToastHelmi.GrandTheftMinecart.Util.MetaDataManager;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 *
 * @author Robin
 */
public class CrackshotListener implements Listener {

    public CrimeManager m;

    public CrackshotListener() {
        m = CrimeManager.getInstance();
    }

    @EventHandler
    public void hurtEntity(WeaponDamageEntityEvent e) {
        if (e.getDamager() instanceof Player) {

            final Player damager = (Player) e.getPlayer();

            if (e.getVictim() instanceof Player && damager != e.getVictim()) {
                final Player player = ((Player) e.getVictim());

                int level;
                try {
                    level = MetaDataManager.getPlayerMetaData(Integer.class, damager, StaticMetaDataValue.WANTEDLEVEL);
                } catch (Exception ex) {
                    level = 0;
                }
                if (level <= 0) {
                    m.setWantedLevel(damager, 1);
                } else if (level == 1 && ((CraftPlayer) player).getHealth() <= e.getDamage()) {
                    m.setWantedLevel(damager, 2);
                    m.incrementInfringement(damager);
                }
            } else if (e.getVictim() instanceof Zombie
                    || e.getVictim() instanceof Skeleton) {

                m.setWantedLevel(damager, 3);
                m.incrementInfringement(damager);
            }

        }
    }
}
