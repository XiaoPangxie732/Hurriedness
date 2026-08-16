package cn.maxpixel.mods.hurriedness.util;

import cn.maxpixel.mods.hurriedness.hvalue.HurriednessValue;
import cn.maxpixel.mods.hurriedness.hvalue.HurriednessValueHelper;
import cn.maxpixel.mods.hurriedness.registry.DataAttachmentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class HurriednessUtil {
    public static void sendMessage1(@NonNull ServerPlayer target) {
        sendMessage1(target, target);
    }

    public static void sendMessage1(@NonNull ServerPlayer target, @NonNull Entity source) {
        target.sendChatMessage(OutgoingChatMessage.create(PlayerChatMessage.system("你已急哭")), false,
                ChatType.bind(ChatType.MSG_COMMAND_OUTGOING, source).withTargetName(target.getDisplayName()));
    }

    public static boolean rangeHurryBlocks(BlockPos blockPos, Level level, @Nullable Entity player) {
        return rangeHurryBlocks(blockPos, level, player, false);
    }

    public static boolean rangeHurryBlocks(BlockPos blockPos, Level level, @Nullable Entity user, boolean smart) {// Returns true if any succeeds
        boolean ret = false;
        for (BlockPos pos : BlockPos.betweenClosed(blockPos.offset(-5, -2, -5), blockPos.offset(5, 2, 5))) {
            ret |= hurryBlock(pos, level, user, null, smart);
        }
        return ret;
    }

    public static boolean hurryBlock(UseOnContext context) {
        return hurryBlock(context.getClickedPos(), context.getLevel(), context.getPlayer(), context.getClickedFace(), false);
    }

    public static boolean hurryBlock(BlockPos pos, Level l, @Nullable Entity user, @Nullable Direction dir, boolean smart) {// Returns true if succeeds
        var chunk = l.getChunk(pos);
        ItemStack boneMealStack = new ItemStack(Items.BONE_MEAL, 1);
        if (smart && chunk.hasData(DataAttachmentRegistry.HURRIEDNESS_VALUE)) {
            var hv = chunk.getData(DataAttachmentRegistry.HURRIEDNESS_VALUE);
            int value = HurriednessValueHelper.getValue(hv, chunk, pos);
            if (value > HurriednessValue.MAX_VALUE - 15) return false;
        }
        if (BoneMealItem.applyBonemeal(boneMealStack, l, pos, user instanceof Player p ? p : null)) {
            if (l instanceof ServerLevel level) {
                if (user != null) boneMealStack.causeUseVibration(user, GameEvent.ITEM_INTERACT_FINISH);
                level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 15);
                HurriednessValueHelper.increase(level, chunk, pos);
            }
            return true;
        }
        BlockState clickedState = l.getBlockState(pos);
        if (dir == null) {
            for (var direction : Direction.values()) {
                if (tryDirectional(pos, l, user, direction, clickedState, boneMealStack, chunk)) return true;
            }
        } else if (tryDirectional(pos, l, user, dir, clickedState, boneMealStack, chunk)) return true;
        if (clickedState.isRandomlyTicking()) {
            if (l instanceof ServerLevel level) {
                clickedState.randomTick(level, pos, level.getRandom());
                HurriednessValueHelper.increase(level, chunk, pos);
            }
            return true;
        }
        // TODO: BE TICK(is that possible now?)
        return false;
    }

    private static boolean tryDirectional(BlockPos pos, Level l, @Nullable Entity user, Direction direction,
                                          BlockState clickedState, ItemStack boneMealStack, ChunkAccess chunk) {
        boolean solidBlockFace = clickedState.isFaceSturdy(l, pos, direction);
        BlockPos relative = pos.relative(direction);
        if (solidBlockFace && BoneMealItem.growWaterPlant(boneMealStack, l, relative, direction)) {
            if (l instanceof ServerLevel level) {
                if (user != null) boneMealStack.causeUseVibration(user, GameEvent.ITEM_INTERACT_FINISH);
                level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, relative, 15);
                HurriednessValueHelper.increase(level, chunk, pos);
            }
            return true;
        }
        return false;
    }
}