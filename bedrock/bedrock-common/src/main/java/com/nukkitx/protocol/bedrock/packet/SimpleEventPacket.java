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

interface SimpleEventPacket extends BedrockPacket {
    private SimpleEventType event;


    public class SimpleEventReader_v291 implements BedrockPacketReader<SimpleEventPacket> {
        public static final SimpleEventReader_v291 INSTANCE = new SimpleEventReader_v291();


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
