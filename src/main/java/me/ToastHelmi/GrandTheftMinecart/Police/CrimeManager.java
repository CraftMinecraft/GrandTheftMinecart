package me.ToastHelmi.GrandTheftMinecart.Police;

import java.util.HashMap;
import java.util.UUID;

import me.ToastHelmi.GrandTheftMinecart.GrandTheftMinecart;
import me.ToastHelmi.GrandTheftMinecart.Police.Listener.CrackshotListener;
import me.ToastHelmi.GrandTheftMinecart.Police.Listener.CrimeListener;
import me.ToastHelmi.GrandTheftMinecart.Police.Listener.PlayerLifeListener;
import me.ToastHelmi.GrandTheftMinecart.StaticValues.SettingPath;
import me.ToastHelmi.GrandTheftMinecart.StaticValues.StaticMetaDataValue;
import me.ToastHelmi.GrandTheftMinecart.Util.MetaDataManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.PluginManager;

public class CrimeManager {

	private static CrimeManager cm;
	private GrandTheftMinecart plugin;
	private static HashMap<UUID, Cooldown> cooldowns = new HashMap<UUID,Cooldown>();
	private final Integer[] cooldownlenth;
	
	
	private CrimeManager(){
		cm = this;
		plugin = GrandTheftMinecart.getInstance();
		FileConfiguration c = plugin.getConfig();
		cooldownlenth = new Integer[]{	c.getInt(SettingPath.TIME_ONE_STAR),
										c.getInt(SettingPath.TIME_TWO_STAR),
										c.getInt(SettingPath.TIME_THREE_STAR),
										c.getInt(SettingPath.TIME_FOUR_STAR),
										c.getInt(SettingPath.TIME_FIVE_STAR)};
		
	Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(GrandTheftMinecart.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				for(Cooldown c: cooldowns.values()){
					if(c.getSecondsLeft() == 0){
						setWantedLevel(c.getPlayer(), 0);
					}
					else{
						c.decrement();
						for(Entity e : c.getPlayer().getNearbyEntities(20, 20, 20)){
							if((e instanceof Zombie || e instanceof Skeleton) 
							   && c.getPlayer().hasLineOfSight(e)){
								c.resetCooldown();
								break;
							}
							
						}
					}
				}
				
			}
		}, 0L,20L );
	}
	
	public static CrimeManager getInstance(){
		if(cm == null)
			cm = new CrimeManager();
		return cm;
	}
	
	public double getAverageWantetLevel(){
		int playercount =0;
		int total = 0;
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			total+= getWantedLevel(p);
			playercount++;
		}
		for(OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()){
			total+= getWantedLevel(p.getPlayer());
			playercount++;
		}
		
		if(playercount == 0){
			return 0;
		}
		return total/playercount;
	}
		
	public void setWantedLevel(Player p, int level){
		if(level <0)
			level = 0;
		if(level > 5)
			level = 5;
		int check = checkRepeatedInfringement(p);
		if (check != 0)
			level = check;
		if(level == 0){
			plugin.getBarFactory().removeBossBar(p);
			if(cooldowns.containsKey(p.getUniqueId())){
				cooldowns.remove(p.getUniqueId());
			MetaDataManager.setPlayerMetaData(p, StaticMetaDataValue.GESETZESVERSTOS, 0);
		}
		}else{
			if(!cooldowns.containsKey(p.getUniqueId()) || level != getWantedLevel(p)){
				SpawnPolice.spawnPolice(p, level);
			}
				cooldowns.put(p.getUniqueId(), new Cooldown(p, getCooldownlengthForLevel(level)));
			String s = ChatColor.GOLD+"";
			for (int i = 0; i < level; i++) {
				s+= "✪ ";
			}
			plugin.getBarFactory().setBossBar(p, s);
		}
		MetaDataManager.setPlayerMetaData(p, StaticMetaDataValue.WANTEDLEVEL, level);
		
	}
	
	public int getWantedLevel(Player p){
		int i = 0;
		try{
			i = MetaDataManager.getPlayerMetaData(Integer.class, p, StaticMetaDataValue.WANTEDLEVEL);
			return i;
		}
		catch(Exception e){
			return 0;
		}
	}
	
	public void resetPlayer(Player p){
		if(p == null){
			System.err.print("Player is null");
			return;
		}
		cooldowns.remove(p.getUniqueId());
		MetaDataManager.setPlayerMetaData(p, StaticMetaDataValue.GESETZESVERSTOS, 0);
		setWantedLevel(p, 0);
	}
	
	public int checkRepeatedInfringement(Player p){
		int amount;
		try{
			amount = MetaDataManager.getPlayerMetaData(Integer.class, p,StaticMetaDataValue.GESETZESVERSTOS);
		}catch(Exception ex){
			amount = 0;
		}
		if(amount >20 && amount <50)
			return 4;
		else if (amount >=50)
			return 5;
		else return 0;
	}
	
	
	public void incrementInfringement(Player p){
		int amount;
		try{
			amount = MetaDataManager.getPlayerMetaData(Integer.class, p,StaticMetaDataValue.GESETZESVERSTOS);
		}catch(Exception ex){
			amount = 0;
		}
		MetaDataManager.setPlayerMetaData(p, StaticMetaDataValue.GESETZESVERSTOS, amount+1);
	}
	
	public void registerListener(PluginManager pm){
		pm.registerEvents(new PlayerLifeListener(), plugin);
		pm.registerEvents(new CrimeListener(), plugin);
                if (pm.getPlugin("CrackShot") != null) {
                    pm.registerEvents(new CrackshotListener(), plugin);
                }
	}
	public boolean isPlayerCriminal(Player p){
		return cooldowns.containsKey(p.getUniqueId());
	}
	
	public void resetPlayerCooldown(Player p ){
		if(cooldowns.containsKey(p.getUniqueId())){
			cooldowns.get(p.getUniqueId()).resetCooldown();
		}
	}
	private int getCooldownlengthForLevel(int level){
		return cooldownlenth[level-1];
	}
	private class Cooldown{
		
		private final long lengthInSeconds;
		private  long secondsLeft;
		private final Player p;
		
		public Cooldown(Player p, long lengthInSeconds){
			this.p = p;
			this.lengthInSeconds = lengthInSeconds-1;
			secondsLeft = lengthInSeconds;
		}
		
		public void  decrement() {
			secondsLeft--;
		}
		public long getSecondsLeft() {
			return secondsLeft;
		}
		public void resetCooldown(){
			secondsLeft = lengthInSeconds;
		}
		public Player getPlayer() {
			return p;
		}
	}

}
