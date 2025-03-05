package net.phazoganon.froghopperboots;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.phazoganon.froghopperboots.item.ModItems;
import net.phazoganon.froghopperboots.util.ModUtils;

@Mod(FrogHopperBoots.MODID)
public class FrogHopperBoots {
    public static final String MODID = "froghopperboots";
    public FrogHopperBoots(IEventBus modEventBus) {
        modEventBus.addListener(this::addCreative);
        ModItems.register(modEventBus);
        ModUtils.init();
    }
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> getTabKey = event.getTabKey();
        if (getTabKey == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.FROG_HOPPER_BOOTS);
        }
    }
}