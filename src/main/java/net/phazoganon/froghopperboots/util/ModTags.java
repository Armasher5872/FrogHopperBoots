package net.phazoganon.froghopperboots.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.phazoganon.froghopperboots.FrogHopperBoots;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> REPAIRS_FROGHOPPER_ARMOR = tag("repairs_froghopper_armor");
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(FrogHopperBoots.MODID, name));
        }
    }
}
