package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface SetCommandsEnabledPacket extends BedrockPacket {
    boolean commandsEnabled;


    record v291 implements SetCommandsEnabledPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetCommandsEnabledPacket packet) {
            buffer.writeBoolean(packet.isCommandsEnabled());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetCommandsEnabledPacket packet) {
            packet.setCommandsEnabled(buffer.readBoolean());
        }
    }

}
