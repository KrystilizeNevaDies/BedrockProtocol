package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Client bound packet to indicate whether the server has preloaded the ticking areas.
 *
 * @since v503
 */
interface TickingAreasLoadStatusPacket extends BedrockPacket {
    boolean waitingForPreload;


    @Overrid

    public class TickingAreasLoadStatusReader_v503 implements BedrockPacketReader<TickingAreasLoadStatusPacket> {
        public static final TickingAreasLoadStatusReader_v503 INSTANCE = new TickingAreasLoadStatusReader_v503();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, TickingAreasLoadStatusPacket packet) {
            buffer.writeBoolean(packet.isWaitingForPreload());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, TickingAreasLoadStatusPacket packet) {
            packet.setWaitingForPreload(buffer.readBoolean());
        }
    }
}
