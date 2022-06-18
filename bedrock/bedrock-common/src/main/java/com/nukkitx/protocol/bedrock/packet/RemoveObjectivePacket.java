package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface RemoveObjectivePacket extends BedrockPacket {
    String objectiveId;


    record v291 implements RemoveObjectivePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RemoveObjectivePacket packet) {
            helper.writeString(buffer, packet.getObjectiveId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RemoveObjectivePacket packet) {
            packet.setObjectiveId(helper.readString(buffer));
        }
    }

}
