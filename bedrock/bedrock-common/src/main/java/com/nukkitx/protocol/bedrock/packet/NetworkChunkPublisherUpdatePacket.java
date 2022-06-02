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
    private Vector3i position;
    private int radius;


    public class NetworkChunkPublisherUpdateReader_v313 implements BedrockPacketReader<NetworkChunkPublisherUpdatePacket> {
        public static final NetworkChunkPublisherUpdateReader_v313 INSTANCE = new NetworkChunkPublisherUpdateReader_v313();

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
