package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginData;
import com.nukkitx.protocol.bedrock.data.command.CommandOutputMessage;
import com.nukkitx.protocol.bedrock.data.command.CommandOutputType;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static java.util.Objects.requireNonNull;


public interface CommandOutputPacket extends BedrockPacket {
    CommandOutputMessage[] messages();
    @NotNull CommandOriginData commandOriginData();
    @NotNull CommandOutputType valueType();
    int successCount();


    record v291(CommandOutputMessage[] messages, CommandOriginData commandOriginData, CommandOutputType valueType,
                int successCount) implements CommandOutputPacket {
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

        public void writeMessage(@NotNull BitOutput output, @NotNull CommandOutputMessage message) {
            requireNonNull(outputMessage, "CommandOutputMessage is null");

            buffer.writeBoolean(outputMessage.isInternal());
            helper.writeString(buffer, outputMessage.getMessageId());
            helper.writeArray(buffer, outputMessage.getParameters(), helper::writeString);
        }

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            commandOriginData().write(output);
            writeByte(output, (byte) valueType().id());
            writeUnsignedInt(output, successCount());
            writeArray(output, messages(), this::writeMessage);
            data().write(output);
        }
    }

}
