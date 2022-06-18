package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface ShowProfilePacket extends BedrockPacket {
    String xuid;


    record v291 implements ShowProfilePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ShowProfilePacket packet) {
            helper.writeString(buffer, packet.getXuid());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ShowProfilePacket packet) {
            packet.setXuid(helper.readString(buffer));
        }
    }

}
