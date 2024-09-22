package org.agmas.experienceplosion.blocks;

import com.mojang.serialization.MapCodec;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.agmas.experienceplosion.ExperienceplosionItems;
import org.jetbrains.annotations.Nullable;

public class ExperienceChargedTNTBlock extends Block implements PolymerTexturedBlock {
    public static final BooleanProperty UNSTABLE;
    public static final IntProperty CHARGE;
    public static final IntProperty SEMICHARGE;

    BlockState modelState = PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(Identifier.of("experienceplosion", "block/tnt")));


    static {
        UNSTABLE = Properties.UNSTABLE;
        CHARGE = IntProperty.of("charge", 0, 12);
        SEMICHARGE = IntProperty.of("semicharge", 0, 5);
    }

    public ExperienceChargedTNTBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)this.getDefaultState().with(UNSTABLE, false).with(CHARGE, 0).with(SEMICHARGE, 0));
    }


    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock())) {
            if (world.isReceivingRedstonePower(pos)) {
                primeTnt(world, pos, state);
                world.removeBlock(pos, false);
            }

        }
    }

    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (world.isReceivingRedstonePower(pos)) {
            primeTnt(world, pos, state);
            world.removeBlock(pos, false);
        }

    }

    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient() && !player.isCreative() && (Boolean)state.get(UNSTABLE)) {
            primeTnt(world, pos, state);
        }

        if (!world.isClient()) {
            for (int i = 0; i < (state.get(CHARGE)*6)+state.get(SEMICHARGE); i++) {

                double d = (double) EntityType.EXPERIENCE_ORB.getHeight() / 2.0;
                double e = pos.getX() + 0.5 + MathHelper.nextDouble(world.random, -0.25, 0.25);
                double f = pos.getY() + 0.5 + MathHelper.nextDouble(world.random, -0.25, 0.25) - d;
                double g = pos.getZ() + 0.5 + MathHelper.nextDouble(world.random, -0.25, 0.25);

                var xp = new ExperienceOrbEntity(world,e,f,g,1);
                xp.setPos(pos.getX(),pos.getY(), pos.getZ());
                world.spawnEntity(xp);
            }
        }

        return super.onBreak(world, pos, state, player);
    }

    public static void primeTnt(World world, BlockPos pos, BlockState state) {
        primeTnt(world, pos, (LivingEntity)null, state);
    }

    private static void primeTnt(World world, BlockPos pos, @Nullable LivingEntity igniter, BlockState state) {
        if (!world.isClient) {
            TntEntity tntEntity = new TntEntity(world, (double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, igniter);
            world.spawnEntity(tntEntity);
            tntEntity.setBlockState(state);
            world.playSound((PlayerEntity)null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(igniter, GameEvent.PRIME_FUSE, pos);
        }
    }



    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
       return ActionResult.CONSUME;
    }

    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!stack.isOf(Items.FLINT_AND_STEEL) && !stack.isOf(Items.FIRE_CHARGE)) {
            if (state.get(CHARGE) == 12) {
                player.sendMessage(Text.of("Experience Charge: Fully Charged"), true);
            } else {
                player.sendMessage(Text.of("Experience Charge: " + state.get(CHARGE) + " (" + state.get(SEMICHARGE) + "/6)"), true);
            }
            if (stack.isOf(ExperienceplosionItems.CAPTURED_EXPERIENCE)) {
                if (state.get(CHARGE)+1 > 12) {
                    return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
                }
                stack.decrementUnlessCreative(1, player);
                if (state.get(SEMICHARGE)+1 > 5) {
                    world.setBlockState(pos, state.with(CHARGE, state.get(CHARGE) + 1).with(SEMICHARGE, 0));
                    world.playSound(null, pos, SoundEvents.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.MASTER, 1, 1);
                } else {
                    world.setBlockState(pos, state.with(SEMICHARGE, state.get(SEMICHARGE) + 1));
                    world.playSound(null, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 1, 1);
                }
                return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

            }
            return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
        } else {
            primeTnt(world, pos, player, state);
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
            Item item = stack.getItem();
            if (stack.isOf(Items.FLINT_AND_STEEL)) {
                stack.damage(1, player, LivingEntity.getSlotForHand(hand));
            } else {
                stack.decrementUnlessCreative(1, player);
            }

            player.incrementStat(Stats.USED.getOrCreateStat(item));
            return ItemActionResult.success(world.isClient);
        }
    }

    protected void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient) {
            BlockPos blockPos = hit.getBlockPos();
            Entity entity = projectile.getOwner();
            if (projectile.isOnFire() && projectile.canModifyAt(world, blockPos)) {
                primeTnt(world, blockPos, entity instanceof LivingEntity ? (LivingEntity)entity : null, state);
                world.removeBlock(blockPos, false);
            }
        }

    }

    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return false;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{UNSTABLE, CHARGE, SEMICHARGE});
    }


    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return modelState;
    }
}
