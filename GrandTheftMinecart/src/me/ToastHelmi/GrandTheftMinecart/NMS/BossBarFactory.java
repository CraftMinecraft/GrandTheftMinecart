package me.ToastHelmi.GrandTheftMinecart.NMS;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.server.v1_7_R3.EntityEnderDragon;
import net.minecraft.server.v1_7_R3.PacketPlayOutEntityDestroy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BossBarFactory {

	/*
	 * Class made by BigTeddy98.
	 * 
	 * BossBarFactory is a class to set a boss bar with your custom text to a
	 * player
	 * 
	 * 1. No warranty is given or implied. 2. All damage is your own
	 * responsibility. 3. If you want to use this in your plugins, a credit
	 * would we appreciated.
	 */

	// everything needed for our reflection
	private static Constructor<?> packetPlayOutSpawnEntityLiving;
	private static Constructor<?> entityEnderdragon;
	private static Method setLocation;

	private static Method getWorldHandle;
	private static Method getPlayerHandle;
	private static Field playerConnection;
	private static Method sendPacket;

	private static Method getDatawatcher;
	private static Method a;
	private static Field d;

	static {
		try {
			// all needed fields, methods and constructors
			packetPlayOutSpawnEntityLiving = getMCClass("PacketPlayOutSpawnEntityLiving").getConstructor(getMCClass("EntityLiving"));
			entityEnderdragon = getMCClass("EntityEnderDragon").getConstructor(getMCClass("World"));
			setLocation = getMCClass("EntityEnderDragon").getMethod("setLocation", double.class, double.class, double.class,float.class, float.class);

			
			
			getWorldHandle = getCraftClass("CraftWorld").getMethod("getHandle");
			getPlayerHandle = getCraftClass("entity.CraftPlayer").getMethod("getHandle");
			playerConnection = getMCClass("EntityPlayer").getDeclaredField("playerConnection");
			sendPacket = getMCClass("PlayerConnection").getMethod("sendPacket",getMCClass("Packet"));

			getDatawatcher = getMCClass("EntityEnderDragon").getMethod("getDataWatcher");
			a = getMCClass("DataWatcher").getMethod("a", int.class,Object.class);
			d = getMCClass("DataWatcher").getDeclaredField("d");
			d.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Plugin plugin;
	private Map<UUID, Object> playerDragons = new HashMap<UUID, Object>();
	private Map<UUID, String> playerText = new HashMap<UUID, String>();

	public BossBarFactory(final Plugin plugin) {
		this.plugin = plugin;

		new BukkitRunnable() {

			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					if(playerText.containsKey(p.getUniqueId()))
						setBossBar(p, playerText.get(p.getUniqueId()));
				}
			}
		}.runTaskTimer(this.plugin, 100, 100);
	}

	public Object getEnderDragon(Player p) throws IllegalAccessException,IllegalArgumentException, InvocationTargetException,InstantiationException {
		if (this.playerDragons.containsKey(p.getUniqueId())) {
			return this.playerDragons.get(p.getUniqueId());
		} else {
			// convert CraftWorld to WorldServer
			Object nms_world = getWorldHandle.invoke(p.getWorld());
			// create new EnderDragon instance
			this.playerDragons.put(p.getUniqueId(),entityEnderdragon.newInstance(nms_world));
			return this.getEnderDragon(p);
		}
	}
	
	public void removeBossBar(Player p){
		
		//TODO packet construktor per reflection -> wartungsärmer
		if(!playerText.containsKey(p.getUniqueId()))
			return;
		
		playerText.remove(p.getUniqueId());
		try {
			Object nms_dragon = playerDragons.remove(p.getUniqueId());
			Object nms_packet = new PacketPlayOutEntityDestroy(((EntityEnderDragon)nms_dragon).getId());
			Object nms_player = getPlayerHandle.invoke(p);
			// get PlayerConnection
			Object nms_connection = playerConnection.get(nms_player);
			// send the packet
			sendPacket.invoke(nms_connection, nms_packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void setBossBar(Player p, String text) {
		playerText.put(p.getUniqueId(), text);
		try {
			// get player's EnderDragon instance
			Object nms_dragon = getEnderDragon(p);
			// set correct location
			setLocation.invoke(nms_dragon, p.getLocation().getX(), -200, p.getLocation().getZ(), 0F, 0F);
			// modify the DataWatcher
			changeWatcher(nms_dragon, text);
			// create new packet instance
			Object nms_packet = packetPlayOutSpawnEntityLiving.newInstance(nms_dragon);
			// convert CraftPlayer to Entityplayer
			Object nms_player = getPlayerHandle.invoke(p);
			// get PlayerConnection
			Object nms_connection = playerConnection.get(nms_player);
			// send the packet
			sendPacket.invoke(nms_connection, nms_packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// modify the datawatcher to change the text
	private static void changeWatcher(Object nms_entity, String text)throws IllegalAccessException, IllegalArgumentException,InvocationTargetException {
		Object nms_watcher = getDatawatcher.invoke(nms_entity);
		Map<?, ?> map = (Map<?, ?>) d.get(nms_watcher);
		map.remove(10);
		a.invoke(nms_watcher, 10, text);
	}

	// easy way to get NMS classes
	private static Class<?> getMCClass(String name)throws ClassNotFoundException {
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3]+ ".";
		String className = "net.minecraft.server." + version + name;
		return Class.forName(className);
	}

	// easy way to get CraftBukkit classes
	private static Class<?> getCraftClass(String name)throws ClassNotFoundException {
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3]+ ".";
		String className = "org.bukkit.craftbukkit." + version + name;
		return Class.forName(className);
	}
}