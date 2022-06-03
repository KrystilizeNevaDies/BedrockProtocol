package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface ContainerOpenPacket extends BedrockPacket {
    private byte id;
    private ContainerType valueType;
    private Vector3i blockPosition;
    private long uniqueEntityId = -1;


    public class ContainerOpenReader_v291 implements BedrockPacketReader<ContainerOpenPacket> {
        public static final ContainerOpenReader_v291 INSTANCE = new ContainerOpenReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ContainerOpenPacket packet) {
            buffer.writeByte(packet.getId());
            buffer.writeByte(packet.getType().getId());
            helper.writeBlockPosition(buffer, packet.getBlockPosition());
            VarInts.writeLong(buffer, packet.getUniqueEntityId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ContainerOpenPacket packet) {
            packet.setId(buffer.readByte());
            packet.setType(ContainerType.from(buffer.readByte()));
            packet.setBlockPosition(helper.readBlockPosition(buffer));
            packet.setUniqueEntityId(VarInts.readLong(buffer));
        }
    }

}
