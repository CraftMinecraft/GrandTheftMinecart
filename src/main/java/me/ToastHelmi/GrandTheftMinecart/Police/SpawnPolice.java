package me.ToastHelmi.GrandTheftMinecart.Police;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.ToastHelmi.GrandTheftMinecart.NMS.Police.PoliceType;
import me.ToastHelmi.GrandTheftMinecart.Settings.Settings;
import me.ToastHelmi.GrandTheftMinecart.Util.Locations.LocationUtils;
import net.minecraft.server.v1_7_R2.Entity;
import net.minecraft.server.v1_7_R2.EntityMonster;
import net.minecraft.server.v1_7_R2.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class SpawnPolice {

    private static Entity spawnPolice(PoliceType typ, Location loc) {
        try {
            Entity e = typ.getTypeClass().getConstructor(World.class).newInstance(((World) ((CraftWorld) loc.getWorld()).getHandle()));
            e.setLocation(loc.getX(), loc.getY(), loc.getBlockZ(), loc.getPitch(), loc.getYaw());
            ((CraftWorld) loc.getWorld()).getHandle().addEntity(e, SpawnReason.NATURAL);
            return e;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            System.out.println("Unable to spawn " + typ.getTypeClass().getSimpleName() + " because");
            e.printStackTrace();
        }
        return null;

    }

    public static void spawnPolice(Player p, int level) {
        Random r = new Random(p.getPlayerTime());
        List<Location> locs = LocationUtils.getCircle(p.getLocation(), 15);
        HashMap<PoliceType, Integer> spawns = Settings.getPoliceSpawn(level);

        for (PoliceType t : spawns.keySet()) {
            for (int i = 0; i < spawns.get(t); i++) {
                Entity e = spawnPolice(t, LocationUtils.getTopLocation(locs.get(r.nextInt(locs.size() - 1))));
                ((EntityMonster) e).setTarget(((CraftPlayer) p).getHandle());
            }
        }
    }

    public static void spawnPolice(Player p, Location loc, int level) {
        HashMap<PoliceType, Integer> spawns = Settings.getPoliceSpawn(level);

        for (PoliceType t : spawns.keySet()) {
            for (int i = 0; i < spawns.get(t); i++) {
                Entity e = spawnPolice(t, loc);
                ((EntityMonster) e).setTarget(((CraftPlayer) p).getHandle());
            }
        }
    }
}
