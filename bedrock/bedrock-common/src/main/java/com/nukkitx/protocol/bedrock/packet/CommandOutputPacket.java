package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
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
        public static final Interpreter<v291> INTERPRETER = new Interpreter<v291>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                CommandOriginData commandOriginData = CommandOriginData.INTERPRETER.interpret(input);
                CommandOutputType valueType = CommandOutputType.INTERPRETER.interpret(input);
                int successCount = readUnsignedInt(input);
                CommandOutputMessage[] messages = readArray(input, CommandOutputMessage.INTERPRETER::interpret);
                return new v291(messages, commandOriginData, valueType, successCount);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            commandOriginData().write(output);
            writeByte(output, (byte) valueType().id());
            writeUnsignedInt(output, successCount());
            writeArray(output, messages());
            valueType().write(output);
        }
    }

}
