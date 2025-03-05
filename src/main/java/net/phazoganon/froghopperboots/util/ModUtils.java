package net.phazoganon.froghopperboots.util;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import javax.annotation.Nullable;
import java.util.IdentityHashMap;

public class ModUtils {
    private ModUtils() {}
    private static final IdentityHashMap<Entity, ModMomentumHandler> ENTITY_MOD_MOMENTUM_HANDLER_IDENTITY_HASH_MAP = new IdentityHashMap<>();
    public static void init() {
        NeoForge.EVENT_BUS.addListener(ModUtils::onTick);
        NeoForge.EVENT_BUS.addListener(ModUtils::serverStop);
    }
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
    private static void onTick(EntityTickEvent event) {
        Entity entity = event.getEntity();
        LivingEntity livingEntity = (LivingEntity) entity;
        ModMomentumHandler momentumHandler = ENTITY_MOD_MOMENTUM_HANDLER_IDENTITY_HASH_MAP.get(livingEntity);
        if (momentumHandler != null) {
            if (!livingEntity.isSpectator() || !livingEntity.isFallFlying()) {
                if (livingEntity.tickCount == momentumHandler.bounceTick) {
                    if (momentumHandler.bounce != null) {
                        livingEntity.setDeltaMovement(momentumHandler.bounce);
                        momentumHandler.bounce = null;
                    }
                    momentumHandler.bounceTick = 0;
                }
                boolean isAir = !livingEntity.onGround() || !livingEntity.isInWater() || !livingEntity.onClimbable();
                if (isAir && momentumHandler.lastMagSq > 0) {
                    Vec3 vec3 = livingEntity.getDeltaMovement();
                    double momentumSquared = Math.pow(vec3.x, 2)+Math.pow(vec3.z, 2);
                    if (momentumSquared == 0) {
                        if (momentumHandler.stopMagTick == 0) {
                            momentumHandler.stopMagTick = livingEntity.tickCount+5;
                        }
                        else if (livingEntity.tickCount > momentumHandler.stopMagTick) {
                            momentumHandler.lastMagSq = 0;
                        }
                    }
                    else if (momentumSquared < momentumHandler.lastMagSq) {
                        momentumHandler.stopMagTick = 0;
                        double momentumChange = Math.sqrt(momentumHandler.lastMagSq/momentumSquared)*0.975D;
                        if (momentumChange > 1) {
                            livingEntity.setDeltaMovement(vec3.x*momentumChange, vec3.y, vec3.z*momentumChange);
                            livingEntity.hasImpulse = true;
                            momentumHandler.lastMagSq = momentumHandler.lastMagSq*Math.pow(0.975, 2);
                            double adjustedAngle = Mth.atan2(vec3.z, vec3.x);
                            if (Math.abs(adjustedAngle-momentumHandler.lastAngle) > 1) {
                                livingEntity.playSound(SoundEvents.SLIME_BLOCK_BREAK, 1F, 1F);
                            }
                            momentumHandler.lastAngle = adjustedAngle;
                        }
                        else {
                            momentumHandler.lastMagSq = momentumSquared;
                            momentumHandler.lastAngle = Mth.atan2(vec3.z, vec3.x);
                        }
                    }
                }
                if (momentumHandler.wasInAir && !isAir) {
                    if (momentumHandler.endHandler == 0) {
                        momentumHandler.endHandler = livingEntity.tickCount+5;
                    }
                    else if (livingEntity.tickCount > momentumHandler.endHandler) {
                        ENTITY_MOD_MOMENTUM_HANDLER_IDENTITY_HASH_MAP.remove(entity);
                    }
                }
                else {
                    momentumHandler.endHandler = 0;
                    momentumHandler.wasInAir = true;
                }
            }
            else {
                ENTITY_MOD_MOMENTUM_HANDLER_IDENTITY_HASH_MAP.remove(entity);
            }
        }
    }
    private static void serverStop(ServerStoppingEvent serverStoppingEvent) {
        ENTITY_MOD_MOMENTUM_HANDLER_IDENTITY_HASH_MAP.clear();
    }
    private static class ModMomentumHandler {
        @Nullable private Vec3 bounce;
        private int bounceTick;
        private int stopMagTick;
        private double lastMagSq;
        private boolean wasInAir = false;
        private int endHandler = 0;
        private double lastAngle;
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