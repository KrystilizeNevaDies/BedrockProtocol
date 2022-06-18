package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

public interface ResourcePackChunkRequestPacket extends BedrockPacket {
    UUID packId;
    String packVersion;
    int chunkIndex;


    record v291 implements ResourcePackChunkRequestPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackChunkRequestPacket packet) {
            String packInfo = packet.getPackId().toString() + (packet.getPackVersion() == null ? "" : '_' + packet.getPackVersion());
            helper.writeString(buffer, packInfo);
            buffer.writeIntLE(packet.getChunkIndex());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackChunkRequestPacket packet) {
            String[] packInfo = helper.readString(buffer).split("_");
            packet.setPackId(UUID.fromString(packInfo[0]));
            if (packInfo.length > 1) {
                packet.setPackVersion(packInfo[1]);
            }
            packet.setChunkIndex(buffer.readIntLE());
        }
    }

}
