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

interface MapCreateLockedCopyPacket extends BedrockPacket {
    long originalMapId;
    long newMapId;


    record v354 implements MapCreateLockedCopyPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MapCreateLockedCopyPacket packet) {
            VarInts.writeLong(buffer, packet.getOriginalMapId());
            VarInts.writeLong(buffer, packet.getNewMapId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MapCreateLockedCopyPacket packet) {
            packet.setOriginalMapId(VarInts.readLong(buffer));
            packet.setNewMapId(VarInts.readLong(buffer));
        }
    }

}
