package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.SimulationType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface SimulationTypePacket extends BedrockPacket {

    SimulationType valueType;


    record v448 implements SimulationTypePacket {


        static final SimulationType[] VALUES = SimulationType.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SimulationTypePacket packet) {
            buffer.writeByte(packet.getType().ordinal());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SimulationTypePacket packet) {
            packet.setType(VALUES[buffer.readUnsignedByte()]);
        }
    }

}
