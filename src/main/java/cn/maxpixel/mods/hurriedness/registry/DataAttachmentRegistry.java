package cn.maxpixel.mods.hurriedness.registry;

import cn.maxpixel.mods.hurriedness.HurriednessMod;
import cn.maxpixel.mods.hurriedness.hvalue.EntityHurriednessValueSyncHandler;
import cn.maxpixel.mods.hurriedness.hvalue.HurriednessValue;
import cn.maxpixel.mods.hurriedness.hvalue.HurriednessValueSyncHandler;
import cn.maxpixel.mods.hurriedness.hvalue.TickCandidate;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class DataAttachmentRegistry {
    static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, HurriednessMod.MODID);

    public static final Supplier<AttachmentType<HurriednessValue>> HURRIEDNESS_VALUE = ATTACHMENT_TYPES.register(
            "hurriedness_value", () -> AttachmentType.builder(HurriednessValue::of)
                    .sync(new HurriednessValueSyncHandler())
                    .build()
    );

    public static final Supplier<AttachmentType<Byte>> ENTITY_HURRIEDNESS_VALUE = ATTACHMENT_TYPES.register(
            "entity_hurriedness_value", () -> AttachmentType.builder(() -> (byte) 0)
                    .sync(new EntityHurriednessValueSyncHandler())
                    .build()
    );

    public static final Supplier<AttachmentType<TickCandidate>> TICK_CANDIDATE = ATTACHMENT_TYPES.register(
            "tick_candidate", () -> AttachmentType.builder(TickCandidate::new).build());
}