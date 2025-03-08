package net.phazoganon.froghopperboots.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.phazoganon.froghopperboots.FrogHopperBoots;
import net.phazoganon.froghopperboots.item.ModItems;

@EventBusSubscriber(modid = FrogHopperBoots.MODID)
public class OnFallEvent {
    private static int boundCount;
    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.fallDistance > 1) {
            if (livingEntity.getItemBySlot(EquipmentSlot.FEET).is(ModItems.FROG_HOPPER_BOOTS)) {
                if (!livingEntity.isSuppressingBounce()) {
                    event.setDamageMultiplier(0.0F);
                    Vec3 vec3 = livingEntity.getDeltaMovement();
                    if (livingEntity instanceof ServerPlayer) {
                        double gravity = livingEntity.getAttributeValue(Attributes.GRAVITY);
                        double time = Math.sqrt(livingEntity.fallDistance/gravity);
                        double velocity = gravity*time;
                        livingEntity.setDeltaMovement(vec3.x, velocity, vec3.z);
                        livingEntity.hurtMarked = true;
                        if (boundCount <= 0) {
                            boundCount++;
                        }
                        if (boundCount > 0) {
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 25, 126, true, true));
                        }
                    }
                    else {
                        float factor = livingEntity.fallDistance < 2 ? -0.7f : -0.9f;
                        livingEntity.setDeltaMovement(vec3.x/0.975f, vec3.y*factor, vec3.z/0.975f);
                    }
                    event.setDistance(0.0F);
                    if (!livingEntity.level().isClientSide) {
                        livingEntity.hasImpulse = true;
                        event.setCanceled(true);
                        livingEntity.setOnGround(false);
                    }
                    livingEntity.playSound(SoundEvents.SLIME_BLOCK_BREAK, 1f, 1f);
                }
                else {
                    event.setDamageMultiplier(0.2F);
                }
            }
        }
        else {
            boundCount = 0;
        }
    }
}
