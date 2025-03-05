package net.phazoganon.froghopperboots.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.phazoganon.froghopperboots.FrogHopperBoots;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FrogHopperBoots.MODID);
    public static final DeferredItem<ArmorItem> FROG_HOPPER_BOOTS = ITEMS.register("frog_hopper_boots", () -> new ArmorItem(ModArmorMaterials.FROGHOPPER, ArmorType.BOOTS, (new Item.Properties()).setId(prefix("frog_hopper_boots")).durability(ArmorType.BOOTS.getDurability(-1))));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
    private static ResourceKey<Item> prefix(String path) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(FrogHopperBoots.MODID, path));
    }
}
