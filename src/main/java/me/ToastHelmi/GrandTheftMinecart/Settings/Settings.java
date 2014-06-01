package me.ToastHelmi.GrandTheftMinecart.Settings;

import java.util.HashMap;

import me.ToastHelmi.GrandTheftMinecart.GrandTheftMinecart;
import me.ToastHelmi.GrandTheftMinecart.NMS.Police.PoliceType;
import me.ToastHelmi.GrandTheftMinecart.StaticValues.SettingPath;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class Settings {
		
	private static ItemStack[] OffiserEquip = new ItemStack[5];
	private static ItemStack[] SniperEquip = new ItemStack[4];
	private static ItemStack[] SwatEquip = new ItemStack[5];
	private static HashMap<Integer, HashMap<PoliceType, Integer>> policeSpawn = new HashMap<Integer, HashMap<PoliceType, Integer>>();
	
        private static boolean MobNameVisible = true;
	private static boolean Info = true;
	private static FileConfiguration c; 
	public static void loadConfig(){
		GrandTheftMinecart.getInstance().saveDefaultConfig();
		 c = GrandTheftMinecart.getInstance().getConfig();
		
		Info = c.getBoolean(SettingPath.UPDATER_ENABELD, true);
		MobNameVisible = c.getBoolean(SettingPath.MOB_NAME_VISIBLE);
                
		OffiserEquip[0] = getItemStack(SettingPath.OFFICER_HELMET);
		OffiserEquip[1] = getItemStack(SettingPath.OFFICER_CHESTPLATE);
		OffiserEquip[2] = getItemStack(SettingPath.OFFICER_LEGGINGS);;
		OffiserEquip[3] = getItemStack(SettingPath.OFFICER_BOOTS);
		OffiserEquip[4] = getItemStack(SettingPath.OFFICER_WEAPON);

		SniperEquip[0] = getItemStack(SettingPath.SNIPER_HELMET);
		SniperEquip[1] = getItemStack(SettingPath.SNIPER_CHESTPLATE);
		SniperEquip[2] = getItemStack(SettingPath.SNIPER_LEGGINGS);;
		SniperEquip[3] = getItemStack(SettingPath.SNIPER_BOOTS);
		
		SwatEquip[0] = getItemStack(SettingPath.SWAT_HELMET);
		SwatEquip[1] = getItemStack(SettingPath.SWAT_CHESTPLATE);
		SwatEquip[2] = getItemStack(SettingPath.SWAT_LEGGINGS);;
		SwatEquip[3] = getItemStack(SettingPath.SWAT_BOOTS);
		SwatEquip[4] = getItemStack(SettingPath.SWAT_WEAPON);
		
		String path = "WantedLevel.Spawn.", p2;
		int i = 1;
		for(String s : new String[]{"OneStar","OneStar","ThreeStarts","FourStars","FiveStars"}){
			p2 = path + s+".";
			HashMap<PoliceType, Integer> level = new HashMap<PoliceType, Integer>();
			level.put(PoliceType.POLICEOFFICER, c.getInt(p2+"Officers"));
			level.put(PoliceType.SNIPER, c.getInt(p2+"Snipers"));
			level.put(PoliceType.SWAT, c.getInt(p2+"Swats"));
			policeSpawn.put(i, level);
			i++;
		}
		
		
	}
	public static  HashMap<PoliceType, Integer> getPoliceSpawn(int level){
		return policeSpawn.get(level);
	}
	public static ItemStack[] getOfficerEquip(){
		return OffiserEquip;
	}
	public static ItemStack[] getSniperEquip(){
		return SniperEquip;
	}
	public static ItemStack[] getSwatEquip(){
		return SwatEquip;
	}
	public static boolean isUpdateInfoEnabeld(){
		return Info;
	}
        public static boolean isMobNameVisible(){
                return MobNameVisible;
        }
	private static ItemStack getItemStack(String path){
		try{
			return new ItemStack(Material.getMaterial(c.getString(path)));
		}
		catch(Exception e){
			return null;
		}
	}
}
