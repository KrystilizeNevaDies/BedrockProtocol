package com.nukkitx.protocol.bedrock.compat.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;
import io.netty.buffer.ByteBuf;

public class PlayStatusReaderCompat implements BedrockPacketReader<PlayStatusPacket> {
    public static final PlayStatusReaderCompat INSTANCE = new PlayStatusReaderCompat();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayStatusPacket packet) {
        buffer.writeInt(packet.getStatus().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayStatusPacket packet) {
        packet.setStatus(PlayStatusPacket.Status.values()[buffer.readInt()]);
    }
}

