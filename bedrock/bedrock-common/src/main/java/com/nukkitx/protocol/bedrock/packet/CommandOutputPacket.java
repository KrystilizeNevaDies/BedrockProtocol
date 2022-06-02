package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginData;
import com.nukkitx.protocol.bedrock.data.command.CommandOutputMessage;
import com.nukkitx.protocol.bedrock.data.command.CommandOutputType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static java.util.Objects.requireNonNull;


interface CommandOutputPacket extends BedrockPacket {
    private final List<CommandOutputMessage> messages = new ObjectArrayList<>();
    private CommandOriginData commandOriginData;
    private CommandOutputType valueType;
    private int successCount;
    private String data;


    public class CommandOutputReader_v291 implements BedrockPacketReader<CommandOutputPacket> {
        public static final CommandOutputReader_v291 INSTANCE = new CommandOutputReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CommandOutputPacket packet) {
            helper.writeCommandOrigin(buffer, packet.getCommandOriginData());
            buffer.writeByte(packet.getType().ordinal());
            VarInts.writeUnsignedInt(buffer, packet.getSuccessCount());

            helper.writeArray(buffer, packet.getMessages(), this::writeMessage);

            if (packet.getType() == CommandOutputType.DATA_SET) {
                helper.writeString(buffer, packet.getData());
            }
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CommandOutputPacket packet) {
            packet.setCommandOriginData(helper.readCommandOrigin(buffer));
            packet.setType(CommandOutputType.values()[buffer.readUnsignedByte()]);
            packet.setSuccessCount(VarInts.readUnsignedInt(buffer));

            helper.readArray(buffer, packet.getMessages(), this::readMessage);

            if (packet.getType() == CommandOutputType.DATA_SET) {
                packet.setData(helper.readString(buffer));
            }
        }

        public CommandOutputMessage readMessage(ByteBuf buffer, BedrockPacketHelper helper) {
            boolean internal = buffer.readBoolean();
            String messageId = helper.readString(buffer);
            String[] parameters = helper.readArray(buffer, new String[0], helper::readString);
            return new CommandOutputMessage(internal, messageId, parameters);
        }

        public void writeMessage(ByteBuf buffer, BedrockPacketHelper helper, CommandOutputMessage outputMessage) {
            requireNonNull(outputMessage, "CommandOutputMessage is null");

            buffer.writeBoolean(outputMessage.isInternal());
            helper.writeString(buffer, outputMessage.getMessageId());
            helper.writeArray(buffer, outputMessage.getParameters(), helper::writeString);
        }
    }

}
