package net.phazoganon.froghopperboots.event;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.phazoganon.froghopperboots.FrogHopperBoots;
import net.phazoganon.froghopperboots.item.ModItems;

@EventBusSubscriber(modid = FrogHopperBoots.MODID)
public class OnFallEvent {
    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player player) {
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.FROG_HOPPER_BOOTS)) {
                if (!player.isSuppressingBounce()) {
                    event.setDamageMultiplier(0.0F);
                    player.level().playSound(player, new BlockPos(player.getOnPos().getX(), player.getOnPos().getY(), player.getOnPos().getZ()), SoundEvents.SLIME_BLOCK_BREAK, SoundSource.PLAYERS, 1, 1);
                }
                else {
                    event.setDamageMultiplier(0.2F);
                }
            }
        }
    }
}
