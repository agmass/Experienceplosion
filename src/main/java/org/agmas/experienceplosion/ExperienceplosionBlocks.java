package org.agmas.experienceplosion;

import eu.pb4.polymer.core.api.item.PolymerBlockItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.agmas.experienceplosion.blocks.ExperienceChargedTNTBlock;
import org.agmas.experienceplosion.blocks.ExperienceChargedTNTItem;
import org.agmas.experienceplosion.blocks.ExperienceConverterBlock;

public class ExperienceplosionBlocks {

    public static final Block EXPERIENCE_TNT = register(
            new ExperienceChargedTNTBlock(AbstractBlock.Settings.create().mapColor(MapColor.BRIGHT_RED).breakInstantly().sounds(BlockSoundGroup.GRASS).burnable().solidBlock(Blocks::never)),
            "experience_charged_tnt",
            false
    );
    public static final Block EXPERIENCE_CONVERTER = register(
            new ExperienceConverterBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.LODESTONE)),
            "experience_converter",
            true
    );
    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        // Register the block and its item.
        Identifier id = Identifier.of(Experienceplosion.MOD_ID, name);

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            BlockItem blockItem = new PolymerBlockItem(block, new Item.Settings(), Items.GREEN_STAINED_GLASS.asItem());
            Registry.register(Registries.ITEM, id, blockItem);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void init() {

        BlockItem blockItem = new ExperienceChargedTNTItem(EXPERIENCE_TNT, new Item.Settings());
        Registry.register(Registries.ITEM, Identifier.of(Experienceplosion.MOD_ID, "experience_charged_tnt"), blockItem);
    }

}
