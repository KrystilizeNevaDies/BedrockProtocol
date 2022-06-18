package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.MultiplayerMode;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface MultiplayerSettingsPacket extends BedrockPacket {
    MultiplayerMode mode;


    record v388 implements MultiplayerSettingsPacket {


        static final MultiplayerMode[] VALUES = MultiplayerMode.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MultiplayerSettingsPacket packet) {
            VarInts.writeInt(buffer, packet.getMode().ordinal());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MultiplayerSettingsPacket packet) {
            packet.setMode(VALUES[VarInts.readInt(buffer)]);
        }
    }

}
