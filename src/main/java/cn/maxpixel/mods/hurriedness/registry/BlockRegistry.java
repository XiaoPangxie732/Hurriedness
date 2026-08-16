package cn.maxpixel.mods.hurriedness.registry;

import cn.maxpixel.mods.hurriedness.HurriednessMod;
import cn.maxpixel.mods.hurriedness.block.HurriedAnchorBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockRegistry {
    static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(HurriednessMod.MODID);

    public static final DeferredBlock<HurriedAnchorBlock> HURRIED_ANCHOR = BLOCKS.registerBlock(HurriedAnchorBlock.ID,
            HurriedAnchorBlock::new, props -> props.mapColor(MapColor.COLOR_RED)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(2.5F));
}
