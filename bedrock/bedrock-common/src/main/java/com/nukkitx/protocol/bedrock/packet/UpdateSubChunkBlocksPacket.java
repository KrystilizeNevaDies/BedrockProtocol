package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.BlockChangeEntry;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

interface UpdateSubChunkBlocksPacket extends BedrockPacket {

    int chunkX;
    int chunkY;
    int chunkZ;

    final List<BlockChangeEntry> standardBlocks = new ObjectArrayList<>();
    final List<BlockChangeEntry> extraBlocks = new ObjectArrayList<>();


    record v465 implements UpdateSubChunkBlocksPacket {


        static final BlockChangeEntry.MessageType[] VALUES = BlockChangeEntry.MessageType.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateSubChunkBlocksPacket packet) {
            VarInts.writeInt(buffer, packet.getChunkX());
            VarInts.writeUnsignedInt(buffer, packet.getChunkY());
            VarInts.writeInt(buffer, packet.getChunkZ());
            helper.writeArray(buffer, packet.getStandardBlocks(), this::writeBlockChangeEntry);
            helper.writeArray(buffer, packet.getExtraBlocks(), this::writeBlockChangeEntry);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateSubChunkBlocksPacket packet) {
            packet.setChunkX(VarInts.readInt(buffer));
            packet.setChunkY(VarInts.readUnsignedInt(buffer));
            packet.setChunkZ(VarInts.readInt(buffer));
            helper.readArray(buffer, packet.getStandardBlocks(), this::readBlockChangeEntry);
            helper.readArray(buffer, packet.getExtraBlocks(), this::readBlockChangeEntry);
        }

        protected void writeBlockChangeEntry(ByteBuf buffer, BedrockPacketHelper helper, BlockChangeEntry entry) {
            helper.writeBlockPosition(buffer, entry.getPosition());
            VarInts.writeUnsignedInt(buffer, entry.getRuntimeId());
            VarInts.writeUnsignedInt(buffer, entry.getUpdateFlags());
            VarInts.writeUnsignedLong(buffer, entry.getMessageEntityId());
            VarInts.writeUnsignedInt(buffer, entry.getMessageType().ordinal());
        }

        protected BlockChangeEntry readBlockChangeEntry(ByteBuf buffer, BedrockPacketHelper helper) {
            return new BlockChangeEntry(
                    helper.readBlockPosition(buffer),
                    VarInts.readUnsignedInt(buffer),
                    VarInts.readUnsignedInt(buffer),
                    VarInts.readUnsignedLong(buffer),
                    VALUES[VarInts.readUnsignedInt(buffer)]
            );
        }
    }


}
