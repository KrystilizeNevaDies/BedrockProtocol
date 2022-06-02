package com.nukkitx.protocol.bedrock.compat.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.packet.DisconnectPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DisconnectReaderCompat implements BedrockPacketReader<DisconnectPacket> {
    public static final DisconnectReaderCompat INSTANCE = new DisconnectReaderCompat();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, DisconnectPacket packet) {
        buffer.writeBoolean(packet.isMessageSkipped());
        if (!packet.isMessageSkipped()) {
            helper.writeString(buffer, packet.getKickMessage());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, DisconnectPacket packet) {
        packet.setMessageSkipped(buffer.readBoolean());
        if (!packet.isMessageSkipped()) {
            packet.setKickMessage(helper.readString(buffer));
        }
    }
}
