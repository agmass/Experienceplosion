package org.agmas.experienceplosion.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.TntEntity;
import org.agmas.experienceplosion.ExperienceplosionBlocks;
import org.agmas.experienceplosion.blocks.ExperienceChargedTNTBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(TntEntity.class)
public abstract class TntEntityMixin {
    @Shadow public abstract BlockState getBlockState();

    @ModifyConstant(method = "explode", constant = @Constant(floatValue = 4.0f))
    public float explode(float constant) {
        if (getBlockState().getBlock().equals(ExperienceplosionBlocks.EXPERIENCE_TNT)) {
            return getBlockState().get(ExperienceChargedTNTBlock.CHARGE)+4;
        }
        return constant;
    }
}
