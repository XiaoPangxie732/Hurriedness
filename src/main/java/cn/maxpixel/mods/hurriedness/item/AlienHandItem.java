package cn.maxpixel.mods.hurriedness.item;

import cn.maxpixel.mods.hurriedness.hvalue.HurriednessValueHelper;
import cn.maxpixel.mods.hurriedness.registry.DataAttachmentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
    public InteractionResult useOn(UseOnContext context) {
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
                if (!level.isClientSide() && clickedState.isRandomlyTicking()) {
                    clickedState.randomTick((ServerLevel) level, pos, level.getRandom());
                    HurriednessValueHelper.increase((ServerLevel) level, chunk, pos);
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.PASS;
            }
        }
    }
}