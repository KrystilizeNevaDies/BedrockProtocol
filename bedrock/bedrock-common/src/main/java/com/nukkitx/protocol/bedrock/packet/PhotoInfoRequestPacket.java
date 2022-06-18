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

interface PhotoInfoRequestPacket extends BedrockPacket {
    long photoId;


    record v471 implements PhotoInfoRequestPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PhotoInfoRequestPacket packet) {
            VarInts.writeLong(buffer, packet.getPhotoId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PhotoInfoRequestPacket packet) {
            packet.setPhotoId(VarInts.readLong(buffer));
        }
    }

}
