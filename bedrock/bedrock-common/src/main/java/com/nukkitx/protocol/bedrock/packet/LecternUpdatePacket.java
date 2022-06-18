package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface LecternUpdatePacket extends BedrockPacket {
    int page;
    int totalPages;
    Vector3i blockPosition;
    boolean droppingBook;


    record v340 implements LecternUpdatePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LecternUpdatePacket packet) {
            buffer.writeByte(packet.getPage());
            helper.writeBlockPosition(buffer, packet.getBlockPosition());
            buffer.writeBoolean(packet.isDroppingBook());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LecternUpdatePacket packet) {
            packet.setPage(buffer.readUnsignedByte());
            packet.setBlockPosition(helper.readBlockPosition(buffer));
            packet.setDroppingBook(buffer.readBoolean());
        }
    }

    record v354 implements LecternUpdatePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LecternUpdatePacket packet) {
            buffer.writeByte(packet.getPage());
            buffer.writeByte(packet.getTotalPages());
            helper.writeBlockPosition(buffer, packet.getBlockPosition());
            buffer.writeBoolean(packet.isDroppingBook());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LecternUpdatePacket packet) {
            packet.setPage(buffer.readUnsignedByte());
            packet.setTotalPages(buffer.readUnsignedByte());
            packet.setBlockPosition(helper.readBlockPosition(buffer));
            packet.setDroppingBook(buffer.readBoolean());
        }
    }

}
