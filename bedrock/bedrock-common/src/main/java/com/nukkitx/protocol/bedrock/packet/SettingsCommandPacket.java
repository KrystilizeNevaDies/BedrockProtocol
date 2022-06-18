package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface SettingsCommandPacket extends BedrockPacket {
    String command;
    boolean suppressingOutput;


    record v388 implements SettingsCommandPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SettingsCommandPacket packet) {
            helper.writeString(buffer, packet.getCommand());
            buffer.writeBoolean(packet.isSuppressingOutput());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SettingsCommandPacket packet) {
            packet.setCommand(helper.readString(buffer));
            packet.setSuppressingOutput(buffer.readBoolean());
        }
    }

}
