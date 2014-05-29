package me.ToastHelmi.GrandTheftMinecart.NMS.Police;

import java.lang.reflect.Field;

import me.ToastHelmi.GrandTheftMinecart.NMS.PathFinderGoals.PathfinderGoalNearestAttackableGangster;
import me.ToastHelmi.GrandTheftMinecart.Settings.Settings;
import net.minecraft.server.v1_7_R3.EntityHuman;
import net.minecraft.server.v1_7_R3.EntityPigZombie;
import net.minecraft.server.v1_7_R3.GenericAttributes;
import net.minecraft.server.v1_7_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_7_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_7_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_7_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_7_R3.PathfinderGoalMoveIndoors;
import net.minecraft.server.v1_7_R3.PathfinderGoalOpenDoor;
import net.minecraft.server.v1_7_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_7_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_7_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_7_R3.World;

import org.bukkit.craftbukkit.v1_7_R3.util.UnsafeList;
import org.bukkit.entity.PigZombie;
import org.bukkit.inventory.EntityEquipment;

public class Swat extends EntityPigZombie{

	public static org.bukkit.inventory.ItemStack[]  equip = Settings.getSwatEquip();
	
	public Swat(World world){
		super(world);
		try {
			Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
			bField.setAccessible(true);
			Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
			cField.setAccessible(true);
			bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
			cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
			
			this.setCustomName("SWAT");
			
		} catch (Exception exc) {
			exc.printStackTrace();
			// This means that the name of one of the fields changed names or
			// declaration and will have to be re-examined.
		}
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(4, new PathfinderGoalRandomLookaround(this));
		this.goalSelector.a(3, new PathfinderGoalMoveIndoors(this));
		this.goalSelector.a(5,new PathfinderGoalOpenDoor(this, true));
		this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this,EntityHuman.class, 8.0F));
		this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
		
		this.goalSelector.a(7, new PathfinderGoalMeleeAttack(this,EntityHuman.class, 1.0D, false)); 
		this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableGangster(this, EntityHuman.class, 0, true,4));
		
		EntityEquipment e = ((PigZombie)this.getBukkitEntity()).getEquipment();
		e.setHelmet(equip[0]);
		e.setChestplate(equip[1]);
		e.setLeggings(equip[2]);
		e.setBoots(equip[3]);
		e.setItemInHand(equip[4]);
		
	}
		

	@Override
	protected void aC() {
		super.aC();
		this.getAttributeInstance(GenericAttributes.e).setValue(6.0D);
		this.getAttributeInstance(GenericAttributes.a).setValue(50.0D);
	}
}
