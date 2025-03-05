package net.phazoganon.froghopperboots.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.phazoganon.froghopperboots.FrogHopperBoots;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, FrogHopperBoots.MODID);
    }
    @Override
    protected void addTags(HolderLookup.Provider provider) {

    }
}