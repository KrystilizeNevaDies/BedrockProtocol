package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Used to maintain synchronization with a server running in authoritative mode.
 */
interface TickSyncPacket extends BedrockPacket {
    private long requestTimestamp;
    private long responseTimestamp;


    public class TickSyncReader_v388 implements BedrockPacketReader<TickSyncPacket> {

        public static final TickSyncReader_v388 INSTANCE = new TickSyncReader_v388();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, TickSyncPacket packet) {
            buffer.writeLongLE(packet.getRequestTimestamp());
            buffer.writeLongLE(packet.getResponseTimestamp());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, TickSyncPacket packet) {
            packet.setRequestTimestamp(buffer.readLongLE());
            packet.setResponseTimestamp(buffer.readLongLE());
        }
    }

}
