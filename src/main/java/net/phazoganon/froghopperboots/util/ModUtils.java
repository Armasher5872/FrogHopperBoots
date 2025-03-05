package net.phazoganon.froghopperboots.util;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

import javax.annotation.Nullable;
import java.util.IdentityHashMap;

public class ModUtils {
    private ModUtils() {}
    public static final IdentityHashMap<Entity, ModMomentumHandler> ENTITY_MOD_MOMENTUM_HANDLER_IDENTITY_HASH_MAP = new IdentityHashMap<>();
    public static void addEntityHandler(LivingEntity entity) {
        addEntityHandler(entity, null);
    }
    public static void addEntityHandler(LivingEntity entity, @Nullable Vec3 bounce) {
        if (!(entity instanceof FakePlayer)) {
            ModMomentumHandler momentumHandler = ENTITY_MOD_MOMENTUM_HANDLER_IDENTITY_HASH_MAP.get(entity);
            if (momentumHandler == null) {
                ENTITY_MOD_MOMENTUM_HANDLER_IDENTITY_HASH_MAP.put(entity, new ModMomentumHandler(entity, bounce));
            }
            else if (bounce != null) {
                momentumHandler.bounce = bounce;
                momentumHandler.bounceTick = entity.tickCount+1;
                Vec3 vec3 = entity.getDeltaMovement();
                momentumHandler.lastMagSq = Math.pow(vec3.x, 2)+Math.pow(vec3.z, 2);
                momentumHandler.lastAngle = Mth.atan2(vec3.z, vec3.x);
            }
        }
    }
    public static class ModMomentumHandler {
        @Nullable public Vec3 bounce;
        public int bounceTick;
        public int stopMagTick;
        public double lastMagSq;
        public boolean wasInAir = false;
        public int endHandler = 0;
        public double lastAngle;
        public ModMomentumHandler(LivingEntity entity, @Nullable Vec3 bounce) {
            this.bounce = bounce;
            if (bounce != null) {
                this.bounceTick = entity.tickCount + 1;
            }
            else {
                this.bounceTick = 0;
            }
            Vec3 motion = entity.getDeltaMovement();
            this.lastMagSq = Math.pow(motion.x, 2)+Math.pow(motion.z, 2);
        }
    }
}