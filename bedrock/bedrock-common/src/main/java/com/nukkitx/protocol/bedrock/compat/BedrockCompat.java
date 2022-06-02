package com.nukkitx.protocol.bedrock.compat;

import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.compat.serializer.DisconnectReaderCompat;
import com.nukkitx.protocol.bedrock.compat.serializer.LoginReaderCompat;
import com.nukkitx.protocol.bedrock.compat.serializer.PlayStatusReaderCompat;
import com.nukkitx.protocol.bedrock.packet.DisconnectPacket;
import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;

public class BedrockCompat {
    /**
     * This is for servers when figuring out the protocol of a client joining.
     */
    public static BedrockPacketCodec COMPAT_CODEC = BedrockPacketCodec.builder()
            .helper(NoopBedrockPacketHelper.INSTANCE)
            .registerPacket(LoginPacket.class, LoginReaderCompat.INSTANCE, 1)
            .registerPacket(PlayStatusPacket.class, PlayStatusReaderCompat.INSTANCE, 2)
            .registerPacket(DisconnectPacket.class, DisconnectReaderCompat.INSTANCE, 5)
            .protocolVersion(0)
            .minecraftVersion("0.0.0")
            .build();
}
