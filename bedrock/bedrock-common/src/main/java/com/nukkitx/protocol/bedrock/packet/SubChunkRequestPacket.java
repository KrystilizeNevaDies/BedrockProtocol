package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

interface SubChunkRequestPacket extends BedrockPacket {
    int dimension;
    Vector3i subChunkPosition;
    /**
     * @since v485
     */
    List<Vector3i> positionOffsets = new ObjectArrayList<>();


    record v471 implements SubChunkRequestPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkRequestPacket packet) {
            VarInts.writeInt(buffer, packet.getDimension());
            helper.writeVector3i(buffer, packet.getSubChunkPosition());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkRequestPacket packet) {
            packet.setDimension(VarInts.readInt(buffer));
            packet.setSubChunkPosition(helper.readVector3i(buffer));
        }
    }

    record v486 extends SubChunkRequestReader_v471 {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkRequestPacket packet) {
            VarInts.writeInt(buffer, packet.getDimension());
            helper.writeVector3i(buffer, packet.getSubChunkPosition());

            buffer.writeIntLE(packet.getPositionOffsets().size());
            packet.getPositionOffsets().forEach(position -> this.writeSubChunkOffset(buffer, position));
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkRequestPacket packet) {
            packet.setDimension(VarInts.readInt(buffer));
            packet.setSubChunkPosition(helper.readVector3i(buffer));

            int requestCount = buffer.readIntLE(); // Unsigned but realistically, we're not going to read that many.
            for (int i = 0; i < requestCount; i++) {
                packet.getPositionOffsets().add(this.readSubChunkOffset(buffer));
            }
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
