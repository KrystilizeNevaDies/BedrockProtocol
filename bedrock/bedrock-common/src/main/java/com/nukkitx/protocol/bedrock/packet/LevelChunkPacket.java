package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

interface LevelChunkPacket extends BedrockPacket {
    private int chunkX;
    private int chunkZ;
    private int subChunksLength;
    private boolean cachingEnabled;
    /**
     * @since v471
     */
    private boolean requestSubChunks;
    /**
     * @since v485
     */
    private int subChunkLimit;
    private final LongList blobIds = new LongArrayList();
    private byte[] data;


    public class FullChunkDataReader_v291 implements BedrockPacketReader<LevelChunkPacket> {
        public static final FullChunkDataReader_v291 INSTANCE = new FullChunkDataReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LevelChunkPacket packet) {
            VarInts.writeInt(buffer, packet.getChunkX());
            VarInts.writeInt(buffer, packet.getChunkZ());
            helper.writeByteArray(buffer, packet.getData());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LevelChunkPacket packet) {
            packet.setChunkX(VarInts.readInt(buffer));
            packet.setChunkZ(VarInts.readInt(buffer));
            packet.setData(helper.readByteArray(buffer));
        }
    }

    public class LevelChunkReader_v361 implements BedrockPacketReader<LevelChunkPacket> {
        public static final LevelChunkReader_v361 INSTANCE = new LevelChunkReader_v361();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LevelChunkPacket packet) {
            VarInts.writeInt(buffer, packet.getChunkX());
            VarInts.writeInt(buffer, packet.getChunkZ());
            VarInts.writeUnsignedInt(buffer, packet.getSubChunksLength());
            buffer.writeBoolean(packet.isCachingEnabled());
            if (packet.isCachingEnabled()) {
                LongList blobIds = packet.getBlobIds();
                VarInts.writeUnsignedInt(buffer, blobIds.size());

                for (long blobId : blobIds) {
                    buffer.writeLongLE(blobId);
                }
            }

            helper.writeByteArray(buffer, packet.getData());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LevelChunkPacket packet) {
            packet.setChunkX(VarInts.readInt(buffer));
            packet.setChunkZ(VarInts.readInt(buffer));
            packet.setSubChunksLength(VarInts.readUnsignedInt(buffer));
            packet.setCachingEnabled(buffer.readBoolean());

            if (packet.isCachingEnabled()) {
                LongList blobIds = packet.getBlobIds();
                int length = VarInts.readUnsignedInt(buffer);

                for (int i = 0; i < length; i++) {
                    blobIds.add(buffer.readLongLE());
                }
            }
            packet.setData(helper.readByteArray(buffer));
        }
    }

    public class LevelChunkReader_v486 extends LevelChunkReader_v361 {
        public static final LevelChunkReader_v486 INSTANCE = new LevelChunkReader_v486();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LevelChunkPacket packet) {
            VarInts.writeInt(buffer, packet.getChunkX());
            VarInts.writeInt(buffer, packet.getChunkZ());

            if (!packet.isRequestSubChunks()) {
                VarInts.writeUnsignedInt(buffer, packet.getSubChunksLength());
            } else if (packet.getSubChunkLimit() < 0) {
                VarInts.writeUnsignedInt(buffer, -1);
            } else {
                VarInts.writeUnsignedInt(buffer, -2);
                buffer.writeShortLE(packet.getSubChunkLimit());
            }

            buffer.writeBoolean(packet.isCachingEnabled());
            if (packet.isCachingEnabled()) {
                LongList blobIds = packet.getBlobIds();
                VarInts.writeUnsignedInt(buffer, blobIds.size());

                for (long blobId : blobIds) {
                    buffer.writeLongLE(blobId);
                }
            }

            helper.writeByteArray(buffer, packet.getData());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LevelChunkPacket packet) {
            packet.setChunkX(VarInts.readInt(buffer));
            packet.setChunkZ(VarInts.readInt(buffer));

            int subChunksCount = VarInts.readUnsignedInt(buffer);
            if (subChunksCount >= 0) {
                packet.setSubChunksLength(subChunksCount);
            } else {
                packet.setRequestSubChunks(true);
                if (subChunksCount == -1) {
                    packet.setSubChunkLimit(subChunksCount);
                } else if (subChunksCount == -2) {
                    packet.setSubChunkLimit(buffer.readUnsignedShortLE());
                }
            }

            packet.setCachingEnabled(buffer.readBoolean());

            if (packet.isCachingEnabled()) {
                LongList blobIds = packet.getBlobIds();
                int length = VarInts.readUnsignedInt(buffer);

                for (int i = 0; i < length; i++) {
                    blobIds.add(buffer.readLongLE());
                }
            }
            packet.setData(helper.readByteArray(buffer));
        }
    }


}
