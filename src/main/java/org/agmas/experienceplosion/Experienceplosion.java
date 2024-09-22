package org.agmas.experienceplosion;

import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import eu.pb4.polymer.core.api.other.SimplePolymerPotion;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Experienceplosion implements ModInitializer {

    public static String MOD_ID = "experienceplosion";

    @Override
    public void onInitialize() {
        ExperienceplosionBlocks.init();
        ExperienceplosionItems.initialize();
        ExperienceplosionEffects.init();
        PolymerResourcePackUtils.addModAssets(MOD_ID);
    }
}
