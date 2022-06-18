package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.SimpleEventType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface SimpleEventPacket extends BedrockPacket {
    SimpleEventType event;


    record v291 implements SimpleEventPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SimpleEventPacket packet) {
            buffer.writeShortLE(packet.getEvent().ordinal());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SimpleEventPacket packet) {
            packet.setEvent(SimpleEventType.values()[buffer.readUnsignedShortLE()]);
        }
    }

}
