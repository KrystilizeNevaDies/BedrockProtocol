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


    record v291 implements ServerSettingsRequestPacket {


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
