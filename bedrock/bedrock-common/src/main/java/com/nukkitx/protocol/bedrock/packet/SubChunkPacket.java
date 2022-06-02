package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.data.HeightMapDataType;
import com.nukkitx.protocol.bedrock.data.SubChunkRequestResult;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.SubChunkData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

interface SubChunkPacket extends BedrockPacket {
    private int dimension;
    private boolean cacheEnabled;
    /**
     * @since v485
     */
    private Vector3i centerPosition;
    private List<SubChunkData> subChunks = new ObjectArrayList<>();


    @Overrid

    public class SubChunkReader_v471 implements BedrockPacketReader<SubChunkPacket> {

        public static final SubChunkReader_v471 INSTANCE = new SubChunkReader_v471();

        protected static final int HEIGHT_MAP_LENGTH = 256;

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
            VarInts.writeInt(buffer, packet.getDimension());
            SubChunkData subChunk = packet.getSubChunks().get(0);
            this.serializeSubChunk(buffer, helper, packet, subChunk);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
            packet.setDimension(VarInts.readInt(buffer));
            SubChunkData subChunk = this.deserializeSubChunk(buffer, helper, packet);
            packet.getSubChunks().add(subChunk);
        }

        protected void serializeSubChunk(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet, SubChunkData subChunk) {
            helper.writeVector3i(buffer, subChunk.getPosition());
            helper.writeByteArray(buffer, subChunk.getData());
            VarInts.writeInt(buffer, subChunk.getResult().ordinal());
            buffer.writeByte(subChunk.getHeightMapType().ordinal());
            byte[] heightMapBuf = subChunk.getHeightMapData();
            buffer.writeBytes(heightMapBuf, 0, HEIGHT_MAP_LENGTH);
        }

        protected SubChunkData deserializeSubChunk(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
            SubChunkData subChunk = new SubChunkData();
            subChunk.setPosition(helper.readVector3i(buffer));
            subChunk.setData(helper.readByteArray(buffer));
            subChunk.setResult(SubChunkRequestResult.values()[VarInts.readInt(buffer)]);
            subChunk.setHeightMapType(HeightMapDataType.values()[buffer.readByte()]);

            byte[] heightMap = new byte[HEIGHT_MAP_LENGTH];
            buffer.readBytes(heightMap);
            subChunk.setHeightMapData(heightMap);
            return subChunk;
        }
    }

    public class SubChunkReader_v475 extends SubChunkReader_v471 {
        public static final SubChunkReader_v475 INSTANCE = new SubChunkReader_v475();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
            super.serialize(buffer, helper, packet);
            if (packet.isCacheEnabled()) {
                buffer.writeLongLE(packet.getSubChunks().get(0).getBlobId());
            }
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
            super.deserialize(buffer, helper, packet);
            if (packet.isCacheEnabled()) {
                packet.getSubChunks().get(0).setBlobId(buffer.readLongLE());
            }
        }

        @Override
        protected void serializeSubChunk(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet, SubChunkData subChunk) {
            helper.writeVector3i(buffer, subChunk.getPosition());
            helper.writeByteArray(buffer, subChunk.getData());
            VarInts.writeInt(buffer, subChunk.getResult().ordinal());
            buffer.writeByte(subChunk.getHeightMapType().ordinal());
            if (subChunk.getHeightMapType() == HeightMapDataType.HAS_DATA) {
                byte[] heightMapBuf = subChunk.getHeightMapData();
                buffer.writeBytes(heightMapBuf, 0, HEIGHT_MAP_LENGTH);
            }
        }

        @Override
        protected SubChunkData deserializeSubChunk(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
            SubChunkData subChunk = new SubChunkData();
            subChunk.setPosition(helper.readVector3i(buffer));
            subChunk.setData(helper.readByteArray(buffer));
            subChunk.setResult(SubChunkRequestResult.values()[VarInts.readInt(buffer)]);
            subChunk.setHeightMapType(HeightMapDataType.values()[buffer.readByte()]);

            if (subChunk.getHeightMapType() == HeightMapDataType.HAS_DATA) {
                byte[] heightMap = new byte[HEIGHT_MAP_LENGTH];
                buffer.readBytes(heightMap);
                subChunk.setHeightMapData(heightMap);
            }
            return subChunk;
        }
    }

    public class SubChunkReader_v486 extends SubChunkReader_v475 {
        public static final SubChunkReader_v486 INSTANCE = new SubChunkReader_v486();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
            buffer.writeBoolean(packet.isCacheEnabled());
            VarInts.writeInt(buffer, packet.getDimension());
            helper.writeVector3i(buffer, packet.getCenterPosition());

            buffer.writeIntLE(packet.getSubChunks().size());
            packet.getSubChunks().forEach(subChunk -> this.serializeSubChunk(buffer, helper, packet, subChunk));
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
            packet.setCacheEnabled(buffer.readBoolean());
            packet.setDimension(VarInts.readInt(buffer));
            packet.setCenterPosition(helper.readVector3i(buffer));

            int size = buffer.readIntLE(); // Unsigned but realistically, we're not going to read that many.
            for (int i = 0; i < size; i++) {
                packet.getSubChunks().add(this.deserializeSubChunk(buffer, helper, packet));
            }
        }

        @Override
        protected void serializeSubChunk(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet, SubChunkData subChunk) {
            this.writeSubChunkOffset(buffer, subChunk.getPosition());
            buffer.writeByte(subChunk.getResult().ordinal());
            if (subChunk.getResult() != SubChunkRequestResult.SUCCESS_ALL_AIR || !packet.isCacheEnabled()) {
                helper.writeByteArray(buffer, subChunk.getData());
            }
            buffer.writeByte(subChunk.getHeightMapType().ordinal());
            if (subChunk.getHeightMapType() == HeightMapDataType.HAS_DATA) {
                byte[] heightMapBuf = subChunk.getHeightMapData();
                buffer.writeBytes(heightMapBuf, 0, HEIGHT_MAP_LENGTH);
            }
            if (packet.isCacheEnabled()) {
                buffer.writeLongLE(subChunk.getBlobId());
            }
        }

        @Override
        protected SubChunkData deserializeSubChunk(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
            SubChunkData subChunk = new SubChunkData();
            subChunk.setPosition(this.readSubChunkOffset(buffer));
            subChunk.setResult(SubChunkRequestResult.values()[buffer.readByte()]);
            if (subChunk.getResult() != SubChunkRequestResult.SUCCESS_ALL_AIR || !packet.isCacheEnabled()) {
                subChunk.setData(helper.readByteArray(buffer));
            }
            subChunk.setHeightMapType(HeightMapDataType.values()[buffer.readByte()]);
            if (subChunk.getHeightMapType() == HeightMapDataType.HAS_DATA) {
                byte[] heightMap = new byte[HEIGHT_MAP_LENGTH];
                buffer.readBytes(heightMap);
                subChunk.setHeightMapData(heightMap);
            }
            if (packet.isCacheEnabled()) {
                subChunk.setBlobId(buffer.readLongLE());
            }
            return subChunk;
        }

        protected void writeSubChunkOffset(ByteBuf buffer, Vector3i offsetPosition) {
            buffer.writeByte(offsetPosition.getX());
            buffer.writeByte(offsetPosition.getY());
            buffer.writeByte(offsetPosition.getZ());
        }

        protected Vector3i readSubChunkOffset(ByteBuf buffer) {
            return Vector3i.from(buffer.readByte(), buffer.readByte(), buffer.readByte());
        }
    }


}
