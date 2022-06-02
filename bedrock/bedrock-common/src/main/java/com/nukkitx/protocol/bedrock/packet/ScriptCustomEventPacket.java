package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface ScriptCustomEventPacket extends BedrockPacket {
    private String eventName;
    private String data;


    public class ScriptCustomEventReader_v291 implements BedrockPacketReader<ScriptCustomEventPacket> {
        public static final ScriptCustomEventReader_v291 INSTANCE = new ScriptCustomEventReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ScriptCustomEventPacket packet) {
            helper.writeString(buffer, packet.getEventName());
            helper.writeString(buffer, packet.getData());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ScriptCustomEventPacket packet) {
            packet.setEventName(helper.readString(buffer));
            packet.setData(helper.readString(buffer));
        }
    }


}

