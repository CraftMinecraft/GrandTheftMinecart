package me.ToastHelmi.GrandTheftMinecart.Util.Updater;

import java.io.File;

import me.ToastHelmi.GrandTheftMinecart.GrandTheftMinecart;
import me.ToastHelmi.GrandTheftMinecart.Util.Updater.Updater.UpdateResult;
import me.ToastHelmi.GrandTheftMinecart.Util.Updater.Updater.UpdateType;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateListener implements Listener{
	
	Updater u;
	boolean ignoreUpdate = false;
	
	public UpdateListener(File f){
		u = new Updater(GrandTheftMinecart.getInstance(), 63438, f, UpdateType.NO_DOWNLOAD, false);
	}
	
	public void toggleInfo(){
		ignoreUpdate = !ignoreUpdate;
	}
	public boolean isInfoEnabeld(){
		return !ignoreUpdate;
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		if(e.getPlayer().hasPermission("gtm.UpdateInfo") && 
		   u.getResult() == UpdateResult.UPDATE_AVAILABLE &&
		   !ignoreUpdate){
			e.getPlayer().sendMessage(ChatColor.GOLD +""+ ChatColor.BOLD + "A new Version of Grand Theft Minecart is available!");
			e.getPlayer().sendMessage("You can download it here: " + u.getLatestFileLink());
		}
	}
}
