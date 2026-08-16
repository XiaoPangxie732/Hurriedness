package cn.maxpixel.mods.hurriedness.item;

import cn.maxpixel.mods.hurriedness.hvalue.HurriednessValueHelper;
import cn.maxpixel.mods.hurriedness.registry.ItemRegistry;
import cn.maxpixel.mods.hurriedness.util.HurriednessUtil;
import net.minecraft.core.BlockPos;
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
        return HurriednessUtil.hurryBlock(context) ? InteractionResult.SUCCESS : InteractionResult.PASS;
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
            HurriednessUtil.rangeHurryBlocks(player.blockPosition(), level, player);
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