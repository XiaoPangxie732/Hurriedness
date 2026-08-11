package cn.maxpixel.mods.hurriedness.hvalue;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.jspecify.annotations.Nullable;

public class EntityHurriednessValueSyncHandler implements AttachmentSyncHandler<Byte> {
    @Override
    public void write(RegistryFriendlyByteBuf buf, Byte attachment, boolean initialSync) {
        buf.writeByte(attachment);
    }

    @Override
    public @Nullable Byte read(IAttachmentHolder holder, RegistryFriendlyByteBuf buf, @Nullable Byte previousValue) {
        return buf.readByte();
    }
}
