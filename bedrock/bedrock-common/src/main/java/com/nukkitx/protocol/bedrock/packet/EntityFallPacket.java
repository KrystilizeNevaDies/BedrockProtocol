package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface EntityFallPacket extends BedrockPacket {
    private long runtimeEntityId;
    private float fallDistance;
    private boolean inVoid;


    public class EntityFallReader_v291 implements BedrockPacketReader<EntityFallPacket> {
        public static final EntityFallReader_v291 INSTANCE = new EntityFallReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EntityFallPacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            buffer.writeFloatLE(packet.getFallDistance());
            buffer.writeBoolean(packet.isInVoid());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EntityFallPacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setFallDistance(buffer.readFloatLE());
            packet.setInVoid(buffer.readBoolean());
        }
    }
}
