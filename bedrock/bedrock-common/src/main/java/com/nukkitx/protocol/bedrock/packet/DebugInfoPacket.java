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

interface DebugInfoPacket extends BedrockPacket {
    private long uniqueEntityId;
    private String data;


    @Overrid

    public class DebugInfoReader_v407 implements BedrockPacketReader<DebugInfoPacket> {
        public static final DebugInfoReader_v407 INSTANCE = new DebugInfoReader_v407();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, DebugInfoPacket packet) {
            VarInts.writeLong(buffer, packet.getUniqueEntityId());
            helper.writeString(buffer, packet.getData());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, DebugInfoPacket packet) {
            packet.setUniqueEntityId(VarInts.readLong(buffer));
            packet.setData(helper.readString(buffer));
        }
    }

}
