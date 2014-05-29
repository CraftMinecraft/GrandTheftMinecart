package me.ToastHelmi.GrandTheftMinecart.Util;

import me.ToastHelmi.GrandTheftMinecart.GrandTheftMinecart;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class MetaDataManager {
	public static void setPlayerMetaData(Player p , String key, Object value){
		p.setMetadata(key, new FixedMetadataValue(GrandTheftMinecart.getInstance(), value));
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T getPlayerMetaData(Class T,Player p, String key){
		
		for(MetadataValue v : p.getMetadata(key)){
			if(v.getOwningPlugin() == GrandTheftMinecart.getInstance()){
				return (T)v.value();
			}
		}
		return null;
	}
	
}
