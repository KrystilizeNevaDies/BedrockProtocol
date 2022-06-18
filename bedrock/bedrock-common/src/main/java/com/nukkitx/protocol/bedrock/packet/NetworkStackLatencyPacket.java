package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface NetworkStackLatencyPacket extends BedrockPacket {
    long timestamp;
    boolean fromServer;


    record v291 implements NetworkStackLatencyPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkStackLatencyPacket packet) {
            buffer.writeLongLE(packet.getTimestamp());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkStackLatencyPacket packet) {
            packet.setTimestamp(buffer.readLongLE());
        }
    }

    record v332 implements NetworkStackLatencyPacket {


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
