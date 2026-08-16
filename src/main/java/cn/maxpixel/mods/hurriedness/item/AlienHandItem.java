package cn.maxpixel.mods.hurriedness.item;

import cn.maxpixel.mods.hurriedness.hvalue.HurriednessValueHelper;
import cn.maxpixel.mods.hurriedness.registry.ItemRegistry;
import cn.maxpixel.mods.hurriedness.util.HurriednessUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jspecify.annotations.NonNull;

public class AlienHandItem extends Item {
    public AlienHandItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockPos relative = pos.relative(context.getClickedFace());
        var chunk = level.getChunk(pos);
        ItemStack boneMealStack = new ItemStack(Items.BONE_MEAL, 1);
        if (BoneMealItem.applyBonemeal(boneMealStack, level, pos, context.getPlayer())) {
            if (!level.isClientSide()) {
                boneMealStack.causeUseVibration(context.getPlayer(), GameEvent.ITEM_INTERACT_FINISH);
                level.levelEvent(1505, pos, 15);
                HurriednessValueHelper.increase((ServerLevel) level, chunk, pos);
                return InteractionResult.SUCCESS_SERVER;
            } else return InteractionResult.PASS;
        } else {
            BlockState clickedState = level.getBlockState(pos);
            boolean solidBlockFace = clickedState.isFaceSturdy(level, pos, context.getClickedFace());
            if (solidBlockFace && BoneMealItem.growWaterPlant(boneMealStack, level, relative, context.getClickedFace())) {
                if (!level.isClientSide()) {
                    boneMealStack.causeUseVibration(context.getPlayer(), GameEvent.ITEM_INTERACT_FINISH);
                    level.levelEvent(1505, relative, 15);
                    HurriednessValueHelper.increase((ServerLevel) level, chunk, pos);
                }
                return InteractionResult.SUCCESS;
            } else {
                if (clickedState.isRandomlyTicking()) {
                    if (level instanceof ServerLevel serverLevel) {
                        clickedState.randomTick(serverLevel, pos, level.getRandom());
                        HurriednessValueHelper.increase(serverLevel, chunk, pos);
                    }
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.PASS;
            }
        }
    }

    @Override
    public @NonNull InteractionResult interactLivingEntity(@NonNull ItemStack itemStack, @NonNull Player source, @NonNull LivingEntity target, @NonNull InteractionHand type) {
        if (target instanceof Player p) {
            if (p instanceof ServerPlayer sp) {
                p.addEffect(new MobEffectInstance(MobEffects.SPEED, 100, 1, false, false, false), source);
                p.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 100, 1, false, false, false), source);
                HurriednessUtil.sendMessage1(sp, source);
                HurriednessValueHelper.increase(sp.level(), sp);
            }
            return InteractionResult.SUCCESS;
        } else if (target instanceof Chicken c) {
            if (target.level() instanceof ServerLevel level) {
                c.spawnAtLocation(level, ItemRegistry.HURRIED_EGG);
                HurriednessValueHelper.increase(level, target);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, @NonNull Player player, @NonNull InteractionHand hand) {
        if (player.isShiftKeyDown()) {
            var playerPos = player.blockPosition();
            for (BlockPos pos : BlockPos.betweenClosed(playerPos.offset(-5, -2, -5), playerPos.offset(5, 2, 5))) {
                var chunk = level.getChunk(pos);
                ItemStack boneMealStack = new ItemStack(Items.BONE_MEAL, 1);
                if (BoneMealItem.applyBonemeal(boneMealStack, level, pos, player)) {
                    if (!level.isClientSide()) {
                        boneMealStack.causeUseVibration(player, GameEvent.ITEM_INTERACT_FINISH);
                        level.levelEvent(1505, pos, 15);
                        HurriednessValueHelper.increase((ServerLevel) level, chunk, pos);
                    }
                } else {
                    BlockState clickedState = level.getBlockState(pos);
                    boolean success = false;
                    for (var direction : Direction.values()) {
                        boolean solidBlockFace = clickedState.isFaceSturdy(level, pos, direction);
                        BlockPos relative = pos.relative(direction);
                        if (solidBlockFace && BoneMealItem.growWaterPlant(boneMealStack, level, relative, direction)) {
                            if (!level.isClientSide()) {
                                boneMealStack.causeUseVibration(player, GameEvent.ITEM_INTERACT_FINISH);
                                level.levelEvent(1505, relative, 15);
                                HurriednessValueHelper.increase((ServerLevel) level, chunk, pos);
                            }
                            success = true;
                            break;
                        }
                    }
                    if (!success && clickedState.isRandomlyTicking() && level instanceof ServerLevel serverLevel) {
                        clickedState.randomTick(serverLevel, pos, level.getRandom());
                        HurriednessValueHelper.increase(serverLevel, chunk, pos);
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        if (entity instanceof ServerPlayer p) {
            p.addEffect(new MobEffectInstance(MobEffects.SPEED, 100, 1, false, false, false), p);
            HurriednessUtil.sendMessage1(p);
            HurriednessValueHelper.increase(p.level(), p);
        }
        entity.stopUsingItem();
        return itemStack;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity user) {
        return 10;
    }

    @Override
    public @NonNull ItemUseAnimation getUseAnimation(@NonNull ItemStack itemStack) {
        return ItemUseAnimation.BLOCK;
    }
}