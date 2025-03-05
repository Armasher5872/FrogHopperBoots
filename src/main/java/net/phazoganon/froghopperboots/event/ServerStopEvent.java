package net.phazoganon.froghopperboots.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.phazoganon.froghopperboots.FrogHopperBoots;
import net.phazoganon.froghopperboots.util.ModUtils;

@EventBusSubscriber(modid = FrogHopperBoots.MODID)
public class ServerStopEvent {
    @SubscribeEvent
    private static void serverStop(ServerStoppingEvent serverStoppingEvent) {
        ModUtils.ENTITY_MOD_MOMENTUM_HANDLER_IDENTITY_HASH_MAP.clear();
    }
}