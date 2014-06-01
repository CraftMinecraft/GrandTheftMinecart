/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ToastHelmi.GrandTheftMinecart.Police.Listener;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 *
 * @author Robin
 */
public class CrackshotListener implements Listener {
    @EventHandler
    public void hurtEntityCrackshot(WeaponDamageEntityEvent ev) {
        Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent(
                ev.getPlayer(), ev.getVictim(), 
                EntityDamageEvent.DamageCause.PROJECTILE, ev.getDamage()));
    }
}
