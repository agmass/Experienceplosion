package org.agmas.experienceplosion.items;

import eu.pb4.polymer.core.api.item.PolymerItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.agmas.experienceplosion.ExperienceplosionBlocks;
import org.jetbrains.annotations.Nullable;

public class CapturedExperienceItem extends Item implements PolymerItem {
    public CapturedExperienceItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        double d = (double) EntityType.EXPERIENCE_ORB.getHeight() / 2.0;
        double e = user.getX() + 0.5 + MathHelper.nextDouble(world.random, -0.25, 0.25);
        double f = user.getY() + 0.5 + MathHelper.nextDouble(world.random, -0.25, 0.25) - d;
        double g = user.getZ() + 0.5 + MathHelper.nextDouble(world.random, -0.25, 0.25);

        var xp = new ExperienceOrbEntity(world,e,f,g,1);
        xp.setPos(user.getX(),user.getEyeY(), user.getZ());
        xp.setVelocity(user.getRotationVector().multiply(2.35));

        world.spawnEntity(xp);
        user.getStackInHand(hand).decrementUnlessCreative(1, user);
        return super.use(world, user, hand);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return Items.GREEN_DYE.asItem();
    }
}
