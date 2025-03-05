package net.phazoganon.froghopperboots.mixin.blocks;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.phazoganon.froghopperboots.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Inject(method = "updateEntityMovementAfterFallOn", at = @At(value = "HEAD"), cancellable = true)
    public void updateEntityMovementAfterFallOn(BlockGetter level, Entity entity, CallbackInfo ci) {
        ci.cancel();
        if (entity instanceof Player player) {
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.FROG_HOPPER_BOOTS)) {
                if (player.isSuppressingBounce()) {
                    entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0, 0.0, 1.0));
                }
                else {
                    Vec3 vec3 = entity.getDeltaMovement();
                    if (vec3.y < 0.0D) {
                        entity.setDeltaMovement(vec3.x, -vec3.y, vec3.y);
                    }
                }
            }
            else {
                entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0, 0.0, 1.0));
            }
        }
        else {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0, 0.0, 1.0));
        }
    }
}