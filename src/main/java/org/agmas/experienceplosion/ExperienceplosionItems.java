package org.agmas.experienceplosion;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.agmas.experienceplosion.items.CapturedExperienceItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExperienceplosionItems {
    public static final Item CAPTURED_EXPERIENCE = register(
            new CapturedExperienceItem(new Item.Settings()),
            "captured_experience"
    );

    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = Identifier.of("experienceplosion", id);

        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);


        // Return the registered item!
        return registeredItem;
    }
    public static void initialize() {

    }
}

