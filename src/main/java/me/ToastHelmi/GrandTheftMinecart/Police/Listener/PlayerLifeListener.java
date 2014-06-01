package me.ToastHelmi.GrandTheftMinecart.Police.Listener;

import me.ToastHelmi.GrandTheftMinecart.GrandTheftMinecart;
import me.ToastHelmi.GrandTheftMinecart.Police.CrimeManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerLifeListener implements Listener {
	
	private final GrandTheftMinecart plugin;
	private final CrimeManager cm;
	public PlayerLifeListener(){
		plugin = GrandTheftMinecart.getInstance();
		cm = CrimeManager.getInstance();
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		cm.resetPlayer(e.getEntity());
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		plugin.addUUID(e.getPlayer().getPlayerListName(),e.getPlayer().getUniqueId());
	}

}
