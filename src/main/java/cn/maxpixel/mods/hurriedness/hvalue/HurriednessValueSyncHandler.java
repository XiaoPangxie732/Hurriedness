package cn.maxpixel.mods.hurriedness.hvalue;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class HurriednessValueSyncHandler implements AttachmentSyncHandler<HurriednessValue> {
    @Override
    public void write(@NonNull RegistryFriendlyByteBuf buf, @NonNull HurriednessValue attachment, boolean initialSync) {
        buf.writeShort(attachment.getSectionCount());
        short toUpdate = 0;
        for (int i = 0; i < attachment.getSectionCount(); ++i) {
            if (attachment.needSync(i)) ++toUpdate;
        }
        buf.writeShort(toUpdate);
        for (int i = 0; i < attachment.getSectionCount(); ++i) {
            if (attachment.needSync(i)) {
                buf.writeShort(i);
                buf.writeByteArray(attachment.getSection(i));
            }
        }
    }

    @Override
    public @Nullable HurriednessValue read(@NonNull IAttachmentHolder holder, @NonNull RegistryFriendlyByteBuf buf, @Nullable HurriednessValue previousValue) {
        short sectionCount = buf.readShort();
        if (previousValue == null || sectionCount != previousValue.getSectionCount()) previousValue = new HurriednessValue(sectionCount);
        int cnt = buf.readShort();
        for (int i = 0; i < cnt; ++i) {
            int j = buf.readShort();
            previousValue.getSections()[j] = buf.readByteArray();
        }
        return previousValue;
    }
}
