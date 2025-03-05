package net.phazoganon.froghopperboots.item;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.phazoganon.froghopperboots.util.ModEquipmentAssets;
import net.phazoganon.froghopperboots.util.ModTags;

import java.util.EnumMap;

public class ModArmorMaterials {
    public static final ArmorMaterial FROGHOPPER = new ArmorMaterial(
            -1,
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 0);
                map.put(ArmorType.LEGGINGS, 0);
                map.put(ArmorType.CHESTPLATE, 0);
                map.put(ArmorType.HELMET, 0);
            }),
            1,
            SoundEvents.ARMOR_EQUIP_GENERIC,
            0,
            0,
            ModTags.Items.REPAIRS_FROGHOPPER_ARMOR,
            ModEquipmentAssets.FROGHOPPER
    );
}