package net.phazoganon.froghopperboots.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.phazoganon.froghopperboots.FrogHopperBoots;
import net.phazoganon.froghopperboots.item.ModItems;

public class ModItemModelProvider extends ModelProvider {
    public ModItemModelProvider(PackOutput output) {
        super(output, FrogHopperBoots.MODID);
    }
    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.FROG_HOPPER_BOOTS.value(), ModelTemplates.FLAT_HANDHELD_ITEM);
    }
}