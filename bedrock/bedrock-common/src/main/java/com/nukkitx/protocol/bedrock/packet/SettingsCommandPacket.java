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
    private String command;
    private boolean suppressingOutput;


    public class SettingsCommandReader_v388 implements BedrockPacketReader<SettingsCommandPacket> {

        public static final SettingsCommandReader_v388 INSTANCE = new SettingsCommandReader_v388();

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
