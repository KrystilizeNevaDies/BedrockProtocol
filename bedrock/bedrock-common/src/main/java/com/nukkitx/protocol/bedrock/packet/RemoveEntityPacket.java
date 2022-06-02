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

interface RemoveEntityPacket extends BedrockPacket {
    private long uniqueEntityId;


    public class RemoveEntityReader_v291 implements BedrockPacketReader<RemoveEntityPacket> {
        public static final RemoveEntityReader_v291 INSTANCE = new RemoveEntityReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RemoveEntityPacket packet) {
            VarInts.writeLong(buffer, packet.getUniqueEntityId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RemoveEntityPacket packet) {
            packet.setUniqueEntityId(VarInts.readLong(buffer));
        }
    }

}
