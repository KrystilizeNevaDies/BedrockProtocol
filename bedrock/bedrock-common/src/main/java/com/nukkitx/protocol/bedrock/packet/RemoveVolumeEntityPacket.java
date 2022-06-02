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

interface RemoveVolumeEntityPacket extends BedrockPacket {
    private int id;
    /**
     * @since v503
     */
    private int dimension;


    @Overrid

    public class RemoveVolumeEntityReader_v440 implements BedrockPacketReader<RemoveVolumeEntityPacket> {

        public static final RemoveVolumeEntityReader_v440 INSTANCE = new RemoveVolumeEntityReader_v440();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RemoveVolumeEntityPacket packet) {
            VarInts.writeUnsignedInt(buffer, packet.getId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RemoveVolumeEntityPacket packet) {
            packet.setId(VarInts.readUnsignedInt(buffer));
        }
    }

    public class RemoveVolumeEntityReader_v503 extends RemoveVolumeEntityReader_v440 {
        public static final RemoveVolumeEntityReader_v503 INSTANCE = new RemoveVolumeEntityReader_v503();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RemoveVolumeEntityPacket packet) {
            super.serialize(buffer, helper, packet);
            VarInts.writeInt(buffer, packet.getDimension());
        }

        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RemoveVolumeEntityPacket packet) {
            super.deserialize(buffer, helper, packet);
            packet.setDimension(VarInts.readInt(buffer));
        }
    }


}
