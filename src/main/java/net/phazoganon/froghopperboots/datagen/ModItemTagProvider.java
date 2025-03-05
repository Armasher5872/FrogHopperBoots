package net.phazoganon.froghopperboots.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.phazoganon.froghopperboots.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.FOOT_ARMOR).add(ModItems.FROG_HOPPER_BOOTS.get());
    }
}
