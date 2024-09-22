package org.agmas.experienceplosion.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.world.World;
import org.agmas.experienceplosion.ExperienceplosionBlocks;
import org.agmas.experienceplosion.ExperienceplosionEffects;
import org.agmas.experienceplosion.blocks.ExperienceChargedTNTBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(method = "addExperience", at = @At("HEAD"))
    public void addexperience(int experience, CallbackInfo ci) {
        if (me().hasStatusEffect(Registries.STATUS_EFFECT.getEntry(ExperienceplosionEffects.SCOLIONOPHOBIA)))
            me().getWorld().createExplosion(null, me().getX(), me().getY(), me().getZ(), 0.1f, World.ExplosionSourceType.TNT);
    }
    @Unique
    public LivingEntity me() {
        return (LivingEntity) (Object) this;
    }
}
