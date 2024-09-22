package org.agmas.experienceplosion;

import eu.pb4.polymer.core.api.other.SimplePolymerPotion;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import org.agmas.experienceplosion.effects.ScolionophobiaEffect;

public class ExperienceplosionEffects
{

    public static final StatusEffect SCOLIONOPHOBIA;

    static {
        SCOLIONOPHOBIA = Registry.register(Registries.STATUS_EFFECT, Identifier.of(Experienceplosion.MOD_ID, "scolionophobia"), new ScolionophobiaEffect());
    }

    public static void init() {

        Potion scolionophobia_potion = Registry.register(
                Registries.POTION,
                Identifier.of(Experienceplosion.MOD_ID, "scolionophobia"),
                new SimplePolymerPotion(
                        new StatusEffectInstance(
                                Registries.STATUS_EFFECT.getEntry(ExperienceplosionEffects.SCOLIONOPHOBIA),
                                20*60,
                                0)));

        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(
                    // Input potion.
                    Potions.WATER,
                    // Ingredient
                    ExperienceplosionItems.CAPTURED_EXPERIENCE,
                    // Output potion.
                    Registries.POTION.getEntry(scolionophobia_potion)
            );
        });
    }
}
