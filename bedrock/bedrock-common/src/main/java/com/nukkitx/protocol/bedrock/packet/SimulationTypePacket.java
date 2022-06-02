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

    private SimulationType valueType;


    @Overrid

    public class SimulationTypeReader_v448 implements BedrockPacketReader<SimulationTypePacket> {
        public static final SimulationTypeReader_v448 INSTANCE = new SimulationTypeReader_v448();

        private static final SimulationType[] VALUES = SimulationType.values();

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
