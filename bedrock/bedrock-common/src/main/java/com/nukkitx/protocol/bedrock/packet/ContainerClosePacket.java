package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface ContainerClosePacket extends BedrockPacket {
    private byte id;
    private boolean unknownBool0;


    public class ContainerCloseReader_v291 implements BedrockPacketReader<ContainerClosePacket> {
        public static final ContainerCloseReader_v291 INSTANCE = new ContainerCloseReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ContainerClosePacket packet) {
            buffer.writeByte(packet.getId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ContainerClosePacket packet) {
            packet.setId(buffer.readByte());
        }
    }

    public class ContainerCloseReader_v419 implements BedrockPacketReader<ContainerClosePacket> {

        public static final ContainerCloseReader_v419 INSTANCE = new ContainerCloseReader_v419();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ContainerClosePacket packet) {
            buffer.writeByte(packet.getId());
            buffer.writeBoolean(packet.isUnknownBool0());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ContainerClosePacket packet) {
            packet.setId(buffer.readByte());
            packet.setUnknownBool0(buffer.readBoolean());
        }
    }


}
