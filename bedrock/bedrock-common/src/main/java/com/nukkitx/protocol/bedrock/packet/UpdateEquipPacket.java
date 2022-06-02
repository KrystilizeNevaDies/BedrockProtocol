package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface UpdateEquipPacket extends BedrockPacket {
    private short windowId;
    private short windowType;
    private int size; // Couldn't find anything on this one. Looks like it isn't used?
    private long uniqueEntityId;
    private NbtMap tag;


    public class UpdateEquipReader_v291 implements BedrockPacketReader<UpdateEquipPacket> {
        public static final UpdateEquipReader_v291 INSTANCE = new UpdateEquipReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateEquipPacket packet) {
            buffer.writeByte(packet.getWindowId());
            buffer.writeByte(packet.getWindowType());
            VarInts.writeInt(buffer, packet.getSize());
            VarInts.writeLong(buffer, packet.getUniqueEntityId());
            helper.writeTag(buffer, packet.getTag());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateEquipPacket packet) {
            packet.setWindowId(buffer.readUnsignedByte());
            packet.setWindowType(buffer.readUnsignedByte());
            packet.setSize(VarInts.readInt(buffer));
            packet.setUniqueEntityId(VarInts.readLong(buffer));
            packet.setTag(helper.readTag(buffer));
        }
    }

}
