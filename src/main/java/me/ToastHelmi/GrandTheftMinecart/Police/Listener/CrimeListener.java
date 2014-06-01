package me.ToastHelmi.GrandTheftMinecart.Police.Listener;

import com.sun.scenario.Settings;
import me.ToastHelmi.GrandTheftMinecart.NMS.Police.PoliceOfficer;
import me.ToastHelmi.GrandTheftMinecart.NMS.Police.Sniper;
import me.ToastHelmi.GrandTheftMinecart.NMS.Police.Swat;
import me.ToastHelmi.GrandTheftMinecart.Police.CrimeManager;
import me.ToastHelmi.GrandTheftMinecart.StaticValues.SettingPath;
import me.ToastHelmi.GrandTheftMinecart.StaticValues.StaticMetaDataValue;
import me.ToastHelmi.GrandTheftMinecart.Util.MetaDataManager;
import org.bukkit.craftbukkit.v1_7_R2.entity.CraftEntity;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class CrimeListener implements Listener {

    public CrimeManager m;

    public CrimeListener() {
        m = CrimeManager.getInstance();
    }

    public static void hurtEntity(Entity edamager, Damageable victim, double damage) {
        if (!(edamager instanceof Player)) {
            return;
        }
        Player damager = (Player) edamager;
        net.minecraft.server.v1_7_R2.Entity handle = ((CraftEntity) victim).getHandle();
        int level;
        try {
            level = MetaDataManager.getPlayerMetaData(Integer.class, damager, StaticMetaDataValue.WANTEDLEVEL);
        } catch (Exception ex) {
            level = 0;
        }

        if (victim instanceof Player && damager != victim) {
            final Player player = (Player) victim;

            if (player.getHealth() <= damage) {
                level = Math.max(level, Settings.getInt(SettingPath.CONDITION_KILL_PLAYER, 2));
                CrimeManager.getInstance().setWantedLevel(damager, level);
                CrimeManager.getInstance().incrementInfringement(damager);
            } else {
                level = Math.max(level, Settings.getInt(SettingPath.CONDITION_ATTACK_PLAYER, 1));
                CrimeManager.getInstance().setWantedLevel(damager, level);
            }
        } else if (handle instanceof PoliceOfficer
                || handle instanceof Swat
                || handle instanceof Sniper) {

            if (((Damageable) victim).getHealth() <= damage) {
                level = Math.max(level, Settings.getInt(SettingPath.CONDITION_KILL_POLICE, 4));
                CrimeManager.getInstance().setWantedLevel(damager, level);
                CrimeManager.getInstance().incrementInfringement(damager);
            } else {
                level = Math.max(level, Settings.getInt(SettingPath.CONDITION_ATTACK_POLICE, 3));
                CrimeManager.getInstance().setWantedLevel(damager, level);
            }
        } else {
            if (((Damageable) victim).getHealth() <= damage) {
                level = Math.max(level, Settings.getInt(SettingPath.CONDITION_KILL_MOB, 0));
                CrimeManager.getInstance().setWantedLevel(damager, level);
                CrimeManager.getInstance().incrementInfringement(damager);
            } else {
                level = Math.max(level, Settings.getInt(SettingPath.CONDITION_ATTACK_MOB, 0));
                CrimeManager.getInstance().setWantedLevel(damager, level);
            }
        }
    }

    @EventHandler
    public void hurtEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Damageable) {
            hurtEntity(e.getDamager(), (Damageable)e.getEntity(), e.getDamage());
        }
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
