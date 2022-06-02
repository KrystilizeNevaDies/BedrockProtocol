package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface TransferPacket extends BedrockPacket {
    private String address;
    private int port;


    public class TransferReader_v291 implements BedrockPacketReader<TransferPacket> {
        public static final TransferReader_v291 INSTANCE = new TransferReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, TransferPacket packet) {
            helper.writeString(buffer, packet.getAddress());
            buffer.writeShortLE(packet.getPort());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, TransferPacket packet) {
            packet.setAddress(helper.readString(buffer));
            packet.setPort(buffer.readShortLE());
        }
    }

}
