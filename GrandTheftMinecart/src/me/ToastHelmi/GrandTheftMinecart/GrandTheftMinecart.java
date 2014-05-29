package me.ToastHelmi.GrandTheftMinecart;

import java.util.HashMap;
import java.util.UUID;

import me.ToastHelmi.GrandTheftMinecart.Cmds.CommandManager;
import me.ToastHelmi.GrandTheftMinecart.Cmds.commands.DisableUpdateInfoCommand;
import me.ToastHelmi.GrandTheftMinecart.NMS.BossBarFactory;
import me.ToastHelmi.GrandTheftMinecart.NMS.CustomEntityType;
import me.ToastHelmi.GrandTheftMinecart.Police.CrimeManager;
import me.ToastHelmi.GrandTheftMinecart.Settings.Settings;
import me.ToastHelmi.GrandTheftMinecart.Util.Metrics;
import me.ToastHelmi.GrandTheftMinecart.Util.Updater.UpdateListener;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.java.JavaPlugin;

public class GrandTheftMinecart extends JavaPlugin{
	
	private static GrandTheftMinecart t;
	private BossBarFactory f;
	private HashMap<String, UUID> uuids = new HashMap<String,UUID>();
	
	public GrandTheftMinecart(){
		t = this;
	}
	
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		super.onEnable();
		
		Settings.loadConfig();
		
		
		CustomEntityType.registerEntities();
		CustomEntityType.registerListener();
		f = new BossBarFactory(this);
		
		
		CommandManager cm = new CommandManager();
		getCommand("GTM").setExecutor(cm);
		if(Settings.isUpdateInfoEnabeld()){
			UpdateListener s = new UpdateListener(getFile());
			cm.registrerCommand("toggleUpdateInfo", new DisableUpdateInfoCommand(s));
			this.getServer().getPluginManager().registerEvents(s, this);
		}
		
		//normaler listener
		CrimeManager.getInstance().registerListener(getServer().getPluginManager());
		/*
		if(getServer().getPluginManager().getPlugin("CrackShot") != null){
			System.out.println("CrackShot is there");
			//register crackshot stuff
		}
		else{
			//normal lsitener
		}*/
	
		Metrics.initMetrics();
			
	
	}
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
		despawnCustomEntitys();
		CustomEntityType.unregisterEntities();
		
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,String label, String[] args) {
		
		return true;
	}
	
	private void despawnCustomEntitys(){
		for(World w : getServer().getWorlds())
			for(Entity e : w.getEntities())
				if(e instanceof Zombie || e instanceof Skeleton || e instanceof PigZombie)
					e.remove();
	}
	public BossBarFactory getBarFactory(){
		return f;
	}
	
	public static GrandTheftMinecart getInstance(){
		if(t == null)
			t = new GrandTheftMinecart();
		return t;
	}
	public UUID getUUIDFromName(String name){
		return uuids.get(name);
	}
	public void addUUID(String name, UUID uuid){
		uuids.put(name, uuid);
	}
}
