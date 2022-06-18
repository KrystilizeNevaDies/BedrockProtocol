package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface ScriptCustomEventPacket extends BedrockPacket {
    String eventName;
    String data;


    record v291 implements ScriptCustomEventPacket {


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

