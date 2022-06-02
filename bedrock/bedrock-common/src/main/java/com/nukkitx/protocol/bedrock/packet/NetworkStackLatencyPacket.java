package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface NetworkStackLatencyPacket extends BedrockPacket {
    private long timestamp;
    private boolean fromServer;


    public class NetworkStackLatencyReader_v291 implements BedrockPacketReader<NetworkStackLatencyPacket> {
        public static final NetworkStackLatencyReader_v291 INSTANCE = new NetworkStackLatencyReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkStackLatencyPacket packet) {
            buffer.writeLongLE(packet.getTimestamp());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkStackLatencyPacket packet) {
            packet.setTimestamp(buffer.readLongLE());
        }
    }

    public class NetworkStackLatencyReader_v332 implements BedrockPacketReader<NetworkStackLatencyPacket> {
        public static final NetworkStackLatencyReader_v332 INSTANCE = new NetworkStackLatencyReader_v332();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkStackLatencyPacket packet) {
            buffer.writeLongLE(packet.getTimestamp());
            buffer.writeBoolean(packet.isFromServer());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkStackLatencyPacket packet) {
            packet.setTimestamp(buffer.readLongLE());
            packet.setFromServer(buffer.readBoolean());
        }
    }


}
