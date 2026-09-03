package cn.maxpixel.mods.hurriedness.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;

public record RestrictedInComponent(BlockPos from, BlockPos to) {
    public static final Codec<RestrictedInComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BlockPos.CODEC.fieldOf("from").forGetter(RestrictedInComponent::from),
                    BlockPos.CODEC.fieldOf("to").forGetter(RestrictedInComponent::to)
            ).apply(instance, RestrictedInComponent::new)
    );

    public static final StreamCodec<ByteBuf, RestrictedInComponent> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, RestrictedInComponent::from,
            BlockPos.STREAM_CODEC, RestrictedInComponent::to,
            RestrictedInComponent::new
    );
}