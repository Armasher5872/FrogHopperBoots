package net.phazoganon.froghopperboots.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.phazoganon.froghopperboots.FrogHopperBoots;

public class ModEquipmentAssets {
    public static ResourceKey<EquipmentAsset> FROGHOPPER = createId("froghopper");
    private static ResourceKey<EquipmentAsset> createId(final String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(FrogHopperBoots.MODID, name));
    }
}