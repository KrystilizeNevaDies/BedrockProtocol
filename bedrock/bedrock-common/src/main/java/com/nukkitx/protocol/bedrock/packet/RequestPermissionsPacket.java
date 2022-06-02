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

interface RequestPermissionsPacket extends BedrockPacket {
    private long uniqueEntityId;
    private int permissions;
    private int customPermissions;


    @Overrid

    public class RequestPermissionsReaderBeta implements BedrockPacketReader<RequestPermissionsPacket> {
        public static final RequestPermissionsReaderBeta INSTANCE = new RequestPermissionsReaderBeta();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RequestPermissionsPacket packet) {
            buffer.writeLongLE(packet.getUniqueEntityId());
            VarInts.writeInt(buffer, packet.getPermissions());
            buffer.writeShortLE(packet.getCustomPermissions());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RequestPermissionsPacket packet) {
            packet.setUniqueEntityId(buffer.readLongLE());
            packet.setPermissions(VarInts.readInt(buffer));
            packet.setCustomPermissions(buffer.readUnsignedShortLE());
        }
    }

}
