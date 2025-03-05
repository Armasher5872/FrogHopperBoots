package net.phazoganon.froghopperboots.event;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.phazoganon.froghopperboots.FrogHopperBoots;
import net.phazoganon.froghopperboots.util.ModUtils;

@EventBusSubscriber(modid = FrogHopperBoots.MODID)
public class OnTickEvent {
    @SubscribeEvent
    private static void onTick(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();
        ModUtils.ModMomentumHandler momentumHandler = ModUtils.ENTITY_MOD_MOMENTUM_HANDLER_IDENTITY_HASH_MAP.get(entity);
        if (momentumHandler != null) {
            if (entity.tickCount == momentumHandler.bounceTick) {
                if (momentumHandler.bounce != null) {
                    entity.setDeltaMovement(momentumHandler.bounce);
                    momentumHandler.bounce = null;
                }
                momentumHandler.bounceTick = 0;
            }
            boolean isAir = !entity.onGround() || !entity.isInWater();
            if (isAir && momentumHandler.lastMagSq > 0) {
                Vec3 vec3 = entity.getDeltaMovement();
                double momentumSquared = Math.pow(vec3.x, 2)+Math.pow(vec3.z, 2);
                if (momentumSquared == 0) {
                    if (momentumHandler.stopMagTick == 0) {
                        momentumHandler.stopMagTick = entity.tickCount+5;
                    }
                    else if (entity.tickCount > momentumHandler.stopMagTick) {
                        momentumHandler.lastMagSq = 0;
                    }
                }
                else if (momentumSquared < momentumHandler.lastMagSq) {
                    momentumHandler.stopMagTick = 0;
                    double momentumChange = Math.sqrt(momentumHandler.lastMagSq/momentumSquared)*0.975D;
                    if (momentumChange > 1) {
                        entity.setDeltaMovement(vec3.x*momentumChange, vec3.y, vec3.z*momentumChange);
                        entity.hasImpulse = true;
                        momentumHandler.lastMagSq = momentumHandler.lastMagSq*Math.pow(0.975, 2);
                        double adjustedAngle = Mth.atan2(vec3.z, vec3.x);
                        if (Math.abs(adjustedAngle-momentumHandler.lastAngle) > 1) {
                            entity.playSound(SoundEvents.SLIME_BLOCK_BREAK, 1F, 1F);
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
                    momentumHandler.endHandler = entity.tickCount+5;
                }
                else if (entity.tickCount > momentumHandler.endHandler) {
                    ModUtils.ENTITY_MOD_MOMENTUM_HANDLER_IDENTITY_HASH_MAP.remove(entity);
                }
            }
            else {
                momentumHandler.endHandler = 0;
                momentumHandler.wasInAir = true;
            }
        }
        else {
            ModUtils.ENTITY_MOD_MOMENTUM_HANDLER_IDENTITY_HASH_MAP.remove(entity);
        }
    }
}