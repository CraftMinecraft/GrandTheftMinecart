package me.ToastHelmi.GrandTheftMinecart.Police.Listener;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import me.ToastHelmi.GrandTheftMinecart.Police.CrimeManager;
import me.ToastHelmi.GrandTheftMinecart.StaticValues.StaticMetaDataValue;
import me.ToastHelmi.GrandTheftMinecart.Util.MetaDataManager;
import org.bukkit.craftbukkit.v1_7_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class CrimeListener implements Listener {

    public CrimeManager m;

    public CrimeListener() {
        m = CrimeManager.getInstance();
    }

    public static void hurtEntity(Entity edamager, Entity victim, double damage) {
        if (!(edamager instanceof Player)) {
            return;
        }
        Player damager = (Player)edamager;
        if (victim instanceof Player && damager != victim) {
            final Player player = (Player) victim;

            int level;
            try {
                level = MetaDataManager.getPlayerMetaData(Integer.class, damager, StaticMetaDataValue.WANTEDLEVEL);
            } catch (Exception ex) {
                level = 0;
            }
            if (level <= 0) {
                CrimeManager.getInstance().setWantedLevel(damager, 1);
            } else if (level == 1 && ((CraftPlayer) player).getHealth() <= damage) {
                CrimeManager.getInstance().setWantedLevel(damager, 2);
                CrimeManager.getInstance().incrementInfringement(damager);
            }
        } else if (victim instanceof Zombie
                || victim instanceof Skeleton) {

            CrimeManager.getInstance().setWantedLevel(damager, 3);
            CrimeManager.getInstance().incrementInfringement(damager);
        }
    }

    @EventHandler
    public void hurtEntity(EntityDamageByEntityEvent e) {
        hurtEntity(e.getDamager(), e.getEntity(), e.getDamage());
    }

    @EventHandler
    public void onHorseTheft(VehicleEnterEvent e) {
        if (e.getVehicle() instanceof Horse && e.getEntered() instanceof Player) {
            final Horse h = (Horse) e.getVehicle();
            if (h.getOwner() != null && h.getOwner() != e.getEntered()) {
                if (m.getWantedLevel((Player) e.getEntered()) < 2) {
                    m.setWantedLevel((Player) e.getEntered(), 2);
                    m.incrementInfringement((Player) e.getEntered());
                }
            }
        }
    }
}
