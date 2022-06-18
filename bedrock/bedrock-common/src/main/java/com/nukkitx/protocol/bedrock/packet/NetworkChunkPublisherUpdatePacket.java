package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface NetworkChunkPublisherUpdatePacket extends BedrockPacket {
    Vector3i position;
    int radius;


    record v313 implements NetworkChunkPublisherUpdatePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkChunkPublisherUpdatePacket packet) {
            helper.writeVector3i(buffer, packet.getPosition());
            VarInts.writeUnsignedInt(buffer, packet.getRadius());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkChunkPublisherUpdatePacket packet) {
            packet.setPosition(helper.readVector3i(buffer));
            packet.setRadius(VarInts.readUnsignedInt(buffer));
        }
    }

}
