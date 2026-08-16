package cn.maxpixel.mods.hurriedness.registry;

import cn.maxpixel.mods.hurriedness.HurriednessMod;
import cn.maxpixel.mods.hurriedness.block.HurriedAnchorBlock;
import cn.maxpixel.mods.hurriedness.block.entity.HurriedAnchorBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntityRegistry {
    static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, HurriednessMod.MODID);

    public static final Supplier<BlockEntityType<HurriedAnchorBlockEntity>> HURRIED_ANCHOR = BLOCK_ENTITY_TYPES.register(
            HurriedAnchorBlock.ID, () -> new BlockEntityType<>(HurriedAnchorBlockEntity::new, BlockRegistry.HURRIED_ANCHOR.get()));
}