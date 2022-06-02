package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.ResourcePackType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

interface ResourcePackDataInfoPacket extends BedrockPacket {
    private UUID packId;
    private String packVersion;
    private long maxChunkSize;
    private long chunkCount;
    private long compressedPackSize;
    private byte[] hash;
    private boolean premium;
    private ResourcePackType valueType;


    public class ResourcePackDataInfoReader_v291 implements BedrockPacketReader<ResourcePackDataInfoPacket> {
        public static final ResourcePackDataInfoReader_v291 INSTANCE = new ResourcePackDataInfoReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackDataInfoPacket packet) {
            String packInfo = packet.getPackId().toString() + (packet.getPackVersion() == null ? "" : '_' + packet.getPackVersion());
            helper.writeString(buffer, packInfo);
            buffer.writeIntLE((int) packet.getMaxChunkSize());
            buffer.writeIntLE((int) packet.getChunkCount());
            buffer.writeLongLE(packet.getCompressedPackSize());
            byte[] hash = packet.getHash();
            VarInts.writeUnsignedInt(buffer, hash.length);
            buffer.writeBytes(hash);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackDataInfoPacket packet) {
            String[] packInfo = helper.readString(buffer).split("_");
            packet.setPackId(UUID.fromString(packInfo[0]));
            if (packInfo.length > 1) {
                packet.setPackVersion(packInfo[1]);
            }
            packet.setMaxChunkSize(buffer.readIntLE());
            packet.setChunkCount(buffer.readIntLE());
            packet.setCompressedPackSize(buffer.readLongLE());
            byte[] hash = new byte[VarInts.readUnsignedInt(buffer)];
            buffer.readBytes(hash);
            packet.setHash(hash);
        }
    }

    public class ResourcePackDataInfoReader_v361 implements BedrockPacketReader<ResourcePackDataInfoPacket> {
        public static final ResourcePackDataInfoReader_v361 INSTANCE = new ResourcePackDataInfoReader_v361();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackDataInfoPacket packet) {
            String packInfo = packet.getPackId().toString() + (packet.getPackVersion() == null ? "" : '_' + packet.getPackVersion());
            helper.writeString(buffer, packInfo);
            buffer.writeIntLE((int) packet.getMaxChunkSize());
            buffer.writeIntLE((int) packet.getChunkCount());
            buffer.writeLongLE(packet.getCompressedPackSize());
            byte[] hash = packet.getHash();
            VarInts.writeUnsignedInt(buffer, hash.length);
            buffer.writeBytes(hash);
            buffer.writeBoolean(packet.isPremium());
            buffer.writeByte(helper.getResourcePackTypeId(packet.getType()));
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackDataInfoPacket packet) {
            String[] packInfo = helper.readString(buffer).split("_");
            packet.setPackId(UUID.fromString(packInfo[0]));
            if (packInfo.length > 1) {
                packet.setPackVersion(packInfo[1]);
            }
            packet.setMaxChunkSize(buffer.readUnsignedIntLE());
            packet.setChunkCount(buffer.readUnsignedIntLE());
            packet.setCompressedPackSize(buffer.readLongLE());
            byte[] hash = new byte[VarInts.readUnsignedInt(buffer)];
            buffer.readBytes(hash);
            packet.setHash(hash);
            packet.setPremium(buffer.readBoolean());
            packet.setType(helper.getResourcePackType(buffer.readUnsignedByte()));
        }
    }

}
