package cn.maxpixel.mods.hurriedness.item;

import cn.maxpixel.mods.hurriedness.hvalue.HurriednessValueHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class InvertedAlienHandItem extends Item {
    public InvertedAlienHandItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (level instanceof ServerLevel serverLevel) {
            var chunk = serverLevel.getChunk(pos);
            if (serverLevel.getRandom().nextInt(10) != 9) {
                HurriednessValueHelper.decrease(serverLevel, chunk, pos);
            } else HurriednessValueHelper.timesTwo(serverLevel, chunk, pos);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NonNull InteractionResult interactLivingEntity(@NonNull ItemStack itemStack, @NonNull Player source, @NonNull LivingEntity target, @NonNull InteractionHand type) {
        if (target instanceof ServerPlayer sp) {
            sp.sendChatMessage(OutgoingChatMessage.create(PlayerChatMessage.system("你没急吧")), false,
                    ChatType.bind(ChatType.MSG_COMMAND_OUTGOING, source).withTargetName(sp.getDisplayName()));
        }
        if (target.level() instanceof ServerLevel level) {
            if (level.getRandom().nextInt(10) != 9) {
                HurriednessValueHelper.decrease(level, target);
            } else HurriednessValueHelper.timesTwo(level, target);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, @NonNull Player player, @NonNull InteractionHand hand) {
        if (player.isShiftKeyDown()) {
            if (level instanceof ServerLevel serverLevel) {
                var playerPos = player.blockPosition();
                for (BlockPos pos : BlockPos.betweenClosed(playerPos.offset(-5, -2, -5), playerPos.offset(5, 2, 5))) {
                    var chunk = serverLevel.getChunk(pos);
                    if (serverLevel.getRandom().nextInt(10) != 9) {
                        HurriednessValueHelper.decrease(serverLevel, chunk, pos);
                    } else HurriednessValueHelper.timesTwo(serverLevel, chunk, pos);
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
            p.sendChatMessage(OutgoingChatMessage.create(PlayerChatMessage.system("你没急吧")), false,
                    ChatType.bind(ChatType.MSG_COMMAND_OUTGOING, p).withTargetName(p.getDisplayName()));
        }
        entity.stopUsingItem();
        if (level instanceof ServerLevel serverLevel) {
            if (serverLevel.getRandom().nextInt(10) != 9) {
                HurriednessValueHelper.decrease(serverLevel, entity);
            } else HurriednessValueHelper.timesTwo(serverLevel, entity);
        }
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
