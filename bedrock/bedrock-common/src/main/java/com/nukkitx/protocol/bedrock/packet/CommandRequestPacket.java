package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginData;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface CommandRequestPacket extends BedrockPacket {
    String command();

    CommandOriginData commandOriginData();

    boolean internal();


    record v291(@NotNull String command, @NotNull CommandOriginData commandOriginData,
                boolean internal) implements CommandRequestPacket {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<v291>() {
            @Override
            public @NotNull CommandRequestPacket.v291 interpret(@NotNull BitInput input) throws IOException {
                String command = readString(input);
                CommandOriginData commandOriginData = CommandOriginData.INTERPRETER.interpret(input);
                boolean internal = input.readBoolean();
                return new v291(command, commandOriginData, internal);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeString(output, command());
            commandOriginData().write(output);
            writeBoolean(output, internal());
        }
    }

}
