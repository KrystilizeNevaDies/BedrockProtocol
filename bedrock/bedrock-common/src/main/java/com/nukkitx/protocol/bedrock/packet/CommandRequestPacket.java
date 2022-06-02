package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface CommandRequestPacket extends BedrockPacket {
    private String command;
    private CommandOriginData commandOriginData;
    private boolean internal;


    public class CommandRequestReader_v291 implements BedrockPacketReader<CommandRequestPacket> {
        public static final CommandRequestReader_v291 INSTANCE = new CommandRequestReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CommandRequestPacket packet) {
            helper.writeString(buffer, packet.getCommand());
            helper.writeCommandOrigin(buffer, packet.getCommandOriginData());
            buffer.writeBoolean(packet.isInternal());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CommandRequestPacket packet) {
            packet.setCommand(helper.readString(buffer));
            packet.setCommandOriginData(helper.readCommandOrigin(buffer));
            packet.setInternal(buffer.readBoolean());
        }
    }

}
