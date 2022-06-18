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
    int id;
    /**
     * @since v503
     */
    int dimension;


    record v440 implements RemoveVolumeEntityPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RemoveVolumeEntityPacket packet) {
            VarInts.writeUnsignedInt(buffer, packet.getId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RemoveVolumeEntityPacket packet) {
            packet.setId(VarInts.readUnsignedInt(buffer));
        }
    }

    record v503 extends RemoveVolumeEntityReader_v440 {


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
