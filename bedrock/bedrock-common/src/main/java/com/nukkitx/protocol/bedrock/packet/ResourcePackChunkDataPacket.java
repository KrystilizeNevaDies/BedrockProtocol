package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

public interface ResourcePackChunkDataPacket extends BedrockPacket {
    UUID packId;
    String packVersion;
    int chunkIndex;
    long progress;
    byte[] data;


    record v291 implements ResourcePackChunkDataPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackChunkDataPacket packet) {
            String packInfo = packet.getPackId().toString() + (packet.getPackVersion() == null ? "" : '_' + packet.getPackVersion());
            helper.writeString(buffer, packInfo);
            buffer.writeIntLE(packet.getChunkIndex());
            buffer.writeLongLE(packet.getProgress());
            byte[] data = packet.getData();
            buffer.writeIntLE(data.length);
            buffer.writeBytes(data);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackChunkDataPacket packet) {
            String[] packInfo = helper.readString(buffer).split("_");
            packet.setPackId(UUID.fromString(packInfo[0]));
            if (packInfo.length > 1) {
                packet.setPackVersion(packInfo[1]);
            }
            packet.setChunkIndex(buffer.readIntLE());
            packet.setProgress(buffer.readLongLE());
            byte[] data = new byte[buffer.readIntLE()];
            buffer.readBytes(data);
            packet.setData(data);
        }
    }

    record v388 implements ResourcePackChunkDataPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackChunkDataPacket packet) {
            String packInfo = packet.getPackId().toString() + (packet.getPackVersion() == null ? "" : '_' + packet.getPackVersion());
            helper.writeString(buffer, packInfo);
            buffer.writeIntLE(packet.getChunkIndex());
            buffer.writeLongLE(packet.getProgress());
            helper.writeByteArray(buffer, packet.getData());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackChunkDataPacket packet) {
            String[] packInfo = helper.readString(buffer).split("_");
            packet.setPackId(UUID.fromString(packInfo[0]));
            if (packInfo.length > 1) {
                packet.setPackVersion(packInfo[1]);
            }
            packet.setChunkIndex(buffer.readIntLE());
            packet.setProgress(buffer.readLongLE());
            packet.setData(helper.readByteArray(buffer));
        }
    }


}
