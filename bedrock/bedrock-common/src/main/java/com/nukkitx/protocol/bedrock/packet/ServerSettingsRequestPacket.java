package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface ServerSettingsRequestPacket extends BedrockPacket {


    public class ServerSettingsRequestReader_v291 implements BedrockPacketReader<ServerSettingsRequestPacket> {
        public static final ServerSettingsRequestReader_v291 INSTANCE = new ServerSettingsRequestReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ServerSettingsRequestPacket packet) {
            // No payload
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ServerSettingsRequestPacket packet) {
            // No payload
        }
    }

}
