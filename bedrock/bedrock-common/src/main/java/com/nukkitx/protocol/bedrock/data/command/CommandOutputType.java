package com.nukkitx.protocol.bedrock.data.command;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface CommandOutputType extends BitDataWritable, PacketDataWriter {
    int id();

    BedrockPacket.Interpreter<CommandOutputType> INTERPRETER = new BedrockPacket.Interpreter<CommandOutputType>() {
        @Override
        public @NotNull CommandOutputType interpret(@NotNull BitInput input) throws IOException {
            int id = readInt(input);
            // TODO: CommandOutputType.INTERPRETER
            return null;
        }
    };

    record None() implements CommandOutputType {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
        }

        @Override
        public int id() {
            return 0;
        }
    }
    record LastOutput() implements CommandOutputType {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
        }

        @Override
        public int id() {
            return 1;
        }
    }
    record Silent() implements CommandOutputType {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
        }

        @Override
        public int id() {
            return 2;
        }
    }
    record AllOutput() implements CommandOutputType {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
        }

        @Override
        public int id() {
            return 3;
        }
    }
    record DataSet(@NotNull String data) implements CommandOutputType {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeString(output, data());
        }

        @Override
        public int id() {
            return 4;
        }
    }
}
