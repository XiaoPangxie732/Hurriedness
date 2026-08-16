package cn.maxpixel.mods.hurriedness.util;

import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.NonNull;

public class HurriednessUtil {
    public static void sendMessage1(@NonNull ServerPlayer target) {
        sendMessage1(target, target);
    }

    public static void sendMessage1(@NonNull ServerPlayer target, @NonNull Entity source) {
        target.sendChatMessage(OutgoingChatMessage.create(PlayerChatMessage.system("你已急哭")), false,
                ChatType.bind(ChatType.MSG_COMMAND_OUTGOING, source).withTargetName(target.getDisplayName()));
    }
}