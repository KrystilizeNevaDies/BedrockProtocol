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
    private boolean commandsEnabled;


    public class SetCommandsEnabledReader_v291 implements BedrockPacketReader<SetCommandsEnabledPacket> {
        public static final SetCommandsEnabledReader_v291 INSTANCE = new SetCommandsEnabledReader_v291();


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
