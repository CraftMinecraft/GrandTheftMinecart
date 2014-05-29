package me.ToastHelmi.GrandTheftMinecart.NMS.PathFinderGoals;

import me.ToastHelmi.GrandTheftMinecart.Police.CrimeManager;
import net.minecraft.server.v1_7_R3.Entity;
import net.minecraft.server.v1_7_R3.EntityLiving;
import net.minecraft.server.v1_7_R3.EntityPlayer;
import net.minecraft.server.v1_7_R3.IEntitySelector;

import org.bukkit.entity.Player;

public class EntitySelectorNearestAttackableGangster implements IEntitySelector{
	final IEntitySelector c;
	final PathfinderGoalNearestAttackableGangster d;
	final int minLevel;

	EntitySelectorNearestAttackableGangster(PathfinderGoalNearestAttackableGangster pathfindergoalnearestattackablegengster,IEntitySelector ientityselector, int minLevel) {
		this.d = pathfindergoalnearestattackablegengster;
		this.c = ientityselector;
		this.minLevel = minLevel;
	}

	public boolean a(Entity entity) {
		return !(entity instanceof EntityPlayer) ? false : (this.c != null && !this.c.a(entity) ? false : (!checkWantedlevel((EntityPlayer)entity) ? false : this.d.a((EntityLiving) entity,false)));
	}
	public boolean checkWantedlevel(EntityPlayer e){
		 return CrimeManager.getInstance().getWantedLevel((Player)e.getBukkitEntity())>= minLevel;
			
	}
}
