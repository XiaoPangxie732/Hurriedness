package cn.maxpixel.mods.hurriedness.block.entity;

import cn.maxpixel.mods.hurriedness.block.HurriedAnchorBlock;
import cn.maxpixel.mods.hurriedness.registry.BlockEntityRegistry;
import cn.maxpixel.mods.hurriedness.registry.ItemRegistry;
import cn.maxpixel.mods.hurriedness.util.HurriednessUtil;
import cn.maxpixel.mods.hurriedness.util.I18nKey;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class HurriedAnchorBlockEntity extends BaseContainerBlockEntity {
    public static final String DEFAULT_NAME_KEY = I18nKey.container(HurriedAnchorBlock.ID);
    private static final Component DEFAULT_NAME = Component.translatable(DEFAULT_NAME_KEY);
    private NonNullList<ItemStack> items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
    private int cooldownTime = -1;

    public HurriedAnchorBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(BlockEntityRegistry.HURRIED_ANCHOR.get(), worldPosition, blockState);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, items);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, items);
    }

    @Override
    protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return ChestMenu.threeRows(containerId, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    @Override
    public boolean canOpen(Player player) {
        return true;
    }

    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack itemStack) {
        return itemStack.is(ItemRegistry.HURRIED_EGG);
    }

    public static void serverTick(Level l, BlockPos pos, BlockState state, HurriedAnchorBlockEntity be) {
        var level = (ServerLevel) l;
        if (!state.getValue(HurriedAnchorBlock.ENABLED)) return;
        if (be.cooldownTime-- > 0) return;
        be.cooldownTime = 20;
        ItemStack hurriedEggStack = null;
        for (var is : be.getItems()) {
            if (!is.isEmpty()) {
                hurriedEggStack = is;
                break;
            }
        }
        if (hurriedEggStack == null) return;
        if (HurriednessUtil.rangeHurryBlocks(pos, level, null, true)) {
            hurriedEggStack.shrink(1);
            be.setChanged();
        }
    }
}
