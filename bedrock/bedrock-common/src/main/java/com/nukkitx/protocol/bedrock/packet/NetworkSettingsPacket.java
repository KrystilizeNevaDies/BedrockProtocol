package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface NetworkSettingsPacket extends BedrockPacket {
    /**
     * The smallest amount of bytes that should be compressed by the client. 0-65535
     */
    int compressionThreshold;


    record v388 implements NetworkSettingsPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkSettingsPacket packet) {
            buffer.writeShortLE(packet.getCompressionThreshold());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkSettingsPacket packet) {
            packet.setCompressionThreshold(buffer.readUnsignedShortLE());
        }
    }

}
