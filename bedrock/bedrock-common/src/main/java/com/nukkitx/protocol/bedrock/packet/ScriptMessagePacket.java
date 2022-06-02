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

interface ScriptMessagePacket extends BedrockPacket {

    private String channel;
    private String message;


    @Overrid

    public class ScriptMessageReader_v486 implements BedrockPacketReader<ScriptMessagePacket> {

        public static final ScriptMessageReader_v486 INSTANCE = new ScriptMessageReader_v486();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ScriptMessagePacket packet) {
            helper.writeString(buffer, packet.getChannel());
            helper.writeString(buffer, packet.getMessage());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ScriptMessagePacket packet) {
            packet.setChannel(helper.readString(buffer));
            packet.setMessage(helper.readString(buffer));
        }
    }

}
