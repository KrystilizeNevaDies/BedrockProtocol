package com.nukkitx.protocol.bedrock.data.command;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

interface CommandParamData extends PacketDataWriter, BitDataWritable {

    @NonNull String name();
    int index();
    boolean optional();

    BedrockPacket.Interpreter<CommandParamData> INTERPRETER = new BedrockPacket.Interpreter<>() {
        @Override
        public @NotNull CommandParamData interpret(@NotNull BitInput input) throws IOException {
            String name = readString(input);
            int index = readIntLE(input);
            boolean optional = readBoolean(input);
            return new Unknown(name, index, optional);
        }
    };

    record Unknown(@NotNull String name, int unidentifiedFlags, boolean optional) implements CommandParamData {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeString(output, name);
            writeIntLE(output, unidentifiedFlags);
            writeBoolean(output, optional);
        }

        @Override
        public int index() {
            return unidentifiedFlags;
        }
    }

    record PostFix(@NotNull String name, int index, boolean optional) implements CommandParamData {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeString(output, name());
            CommandSymbolData symbolData = new CommandSymbolData(index, false, false, true);
            writeIntLE(output, symbolData.serialize());
            writeBoolean(output, optional());
        }
    }

    record SoftEnumData(@NotNull String name, int index, boolean optional) implements CommandParamData {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeString(output, name());
            CommandSymbolData symbolData = new CommandSymbolData(index, false, true, false);
            writeIntLE(output, symbolData.serialize());
            writeBoolean(output, optional());
        }
    }

    record EnumData(@NotNull String name, int index, boolean optional) implements CommandParamData {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeString(output, name());
            CommandSymbolData symbolData = new CommandSymbolData(index, true, false, false);
            writeIntLE(output, symbolData.serialize());
            writeBoolean(output, optional());
        }
    }

    record Type(@NotNull String name, int index, boolean optional) implements CommandParamData {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeString(output, name());
            CommandSymbolData symbolData = new CommandSymbolData(index, false, false, false);
            writeIntLE(output, symbolData.serialize());
            writeBoolean(output, optional());
        }
    }
}
