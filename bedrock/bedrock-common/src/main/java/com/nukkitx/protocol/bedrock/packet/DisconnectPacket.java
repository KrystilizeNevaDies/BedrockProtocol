package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface DisconnectPacket extends BedrockPacket {
    private boolean messageSkipped;
    private String kickMessage;


    public class DisconnectReader_v291 implements BedrockPacketReader<DisconnectPacket> {
        public static final DisconnectReader_v291 INSTANCE = new DisconnectReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, DisconnectPacket packet) {
            buffer.writeBoolean(packet.isMessageSkipped());
            if (!packet.isMessageSkipped()) {
                helper.writeString(buffer, packet.getKickMessage());
            }
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, DisconnectPacket packet) {
            packet.setMessageSkipped(buffer.readBoolean());
            if (!packet.isMessageSkipped()) {
                packet.setKickMessage(helper.readString(buffer));
            }
        }
    }
}
