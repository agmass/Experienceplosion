package org.agmas.experienceplosion.blocks;

import eu.pb4.polymer.core.api.block.PolymerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ExperienceBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.agmas.experienceplosion.ExperienceplosionItems;

public class ExperienceConverterBlock extends Block implements PolymerBlock {
    public ExperienceConverterBlock(Settings settings) {
        super(settings);
    }


    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player.totalExperience > 0) {
            player.addExperience(-1);
            player.giveItemStack(ExperienceplosionItems.CAPTURED_EXPERIENCE.getDefaultStack());
        }
        return super.onUse(state, world, pos, player, hit);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return Blocks.GREEN_SHULKER_BOX.getDefaultState();
    }
}
