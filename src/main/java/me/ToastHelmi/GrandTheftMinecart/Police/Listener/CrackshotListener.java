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
import org.bukkit.craftbukkit.v1_7_R2.entity.CraftPlayer;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CrackshotListener implements Listener {

    public CrimeManager m;

    public CrackshotListener() {
        m = CrimeManager.getInstance();
    }

    @EventHandler
    public void hurtEntity(WeaponDamageEntityEvent e) {
        if (e.getVictim() instanceof Damageable) {
            CrimeListener.hurtEntity(e.getPlayer(), (Damageable)e.getVictim(), e.getDamage());
        }
    }
}
