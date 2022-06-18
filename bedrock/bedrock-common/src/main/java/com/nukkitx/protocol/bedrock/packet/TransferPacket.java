package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface TransferPacket extends BedrockPacket {
    String address;
    int port;


    record v291 implements TransferPacket {


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
