package me.ToastHelmi.GrandTheftMinecart.Police.Listener;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import me.ToastHelmi.GrandTheftMinecart.Police.CrimeManager;
import me.ToastHelmi.GrandTheftMinecart.StaticValues.StaticMetaDataValue;
import me.ToastHelmi.GrandTheftMinecart.Util.MetaDataManager;

import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
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

public class CrimeListener implements Listener{

	public CrimeManager m;
	
	public CrimeListener(){
		m = CrimeManager.getInstance();
        }
	
	@EventHandler
	public void hurtEntity(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player ){
			
			final Player damager = (Player) e.getDamager();
			
			if(e.getEntity() instanceof Player && e.getDamager() != e.getEntity()){
				final Player player = ((Player)e.getEntity());
			
				int level;
				try{
					level = MetaDataManager.getPlayerMetaData(Integer.class, damager, StaticMetaDataValue.WANTEDLEVEL);
				}catch(Exception ex){
					level = 0;
				}
				if(level <= 0){
					m.setWantedLevel(damager, 1);
				}
				else if (level == 1 && ((CraftPlayer)player).getHealth() <= e.getDamage()){
					m.setWantedLevel(damager, 2);
					m.incrementInfringement(damager);
				}
			}
			else if (e.getEntity() instanceof Zombie ||
					 e.getEntity() instanceof Skeleton){
				
				m.setWantedLevel(damager, 3);
				m.incrementInfringement(damager);
			}
			
			
		}
	}
	
	@EventHandler
	public void onHorseTheft(VehicleEnterEvent e){
		if(e.getVehicle() instanceof Horse && e.getEntered() instanceof Player){
			final Horse h = (Horse) e.getVehicle();
			if(h.getOwner() != null && h.getOwner() != e.getEntered()){
				if(m.getWantedLevel((Player) e.getEntered()) < 2){
					m.setWantedLevel((Player)e.getEntered(), 2);
					m.incrementInfringement((Player) e.getEntered());
				}
			}
		}
	}
	

}
