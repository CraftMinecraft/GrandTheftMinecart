package me.ToastHelmi.GrandTheftMinecart.NMS.PathFinderGoals;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_7_R3.DistanceComparator;
import net.minecraft.server.v1_7_R3.EntityCreature;
import net.minecraft.server.v1_7_R3.EntityLiving;
import net.minecraft.server.v1_7_R3.IEntitySelector;
import net.minecraft.server.v1_7_R3.PathfinderGoalTarget;

public class PathfinderGoalNearestAttackableGangster extends PathfinderGoalTarget  {

	private final Class a;
    private final int b;
    private final DistanceComparator e;
    private final IEntitySelector f;
    private EntityLiving g;

    public PathfinderGoalNearestAttackableGangster(EntityCreature entitycreature, Class oclass, int i, boolean flag, int minLevel) {
        this(entitycreature, oclass, i, flag, false, minLevel);
    }

    public PathfinderGoalNearestAttackableGangster(EntityCreature entitycreature, Class oclass, int i, boolean flag, boolean flag1, int minLevel) {
        this(entitycreature, oclass, i, flag, flag1, (IEntitySelector) null, minLevel);
    }

    public PathfinderGoalNearestAttackableGangster(EntityCreature entitycreature, Class oclass, int i, boolean flag, boolean flag1, IEntitySelector ientityselector, int minLevel) {
        super(entitycreature,flag, flag1);
        this.a = oclass;
        this.b = i;
        this.e = new DistanceComparator(entitycreature);
        this.a(1);
        this.f = new EntitySelectorNearestAttackableGangster(this, ientityselector,minLevel);
    }

    public boolean a() {
        if (this.b > 0 && this.c.aH().nextInt(this.b) != 0) {
            return false;
        } else {
            double d0 = this.f();
            List list = this.c.world.a(this.a, this.c.boundingBox.grow(d0, 4.0D, d0), this.f);

            Collections.sort(list, this.e);
            if (list.isEmpty()) {
                return false;
            } else {
                this.g = (EntityLiving) list.get(0);
                return true;
            }
        }
    }

    public void c() {
        this.c.setGoalTarget(this.g);
        super.c();
    }
    public boolean a(EntityLiving e, boolean flag){
    	return super.a(e, flag);
    }
}