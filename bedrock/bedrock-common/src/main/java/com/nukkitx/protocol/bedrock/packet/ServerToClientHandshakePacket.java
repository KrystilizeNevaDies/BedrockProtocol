package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.annotation.NoEncryption;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;


public interface ServerToClientHandshakePacket extends BedrockPacket {
    private String jwt;


    public class ServerToClientHandshakeReader_v291 implements BedrockPacketReader<ServerToClientHandshakePacket> {
        public static final ServerToClientHandshakeReader_v291 INSTANCE = new ServerToClientHandshakeReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ServerToClientHandshakePacket packet) {
            helper.writeString(buffer, packet.getJwt());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ServerToClientHandshakePacket packet) {
            packet.setJwt(helper.readString(buffer));
        }
    }

}
