package com.nukkitx.protocol.bedrock.data.command;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public record CommandData(
        String name,
        String description,
        List<Flag> flags,
        int permission,
        int aliasesIndex,
        CommandParamData[][] overloads
) implements PacketDataWriter, BitDataWritable {
    public static final BedrockPacket.Interpreter<CommandData> INTERPRETER = new BedrockPacket.Interpreter<>() {
        @Override
        public @NotNull CommandData interpret(@NotNull BitInput input) throws IOException {
            String name = readString(input);
            String description = readString(input);
            List<Flag> flags = Flag.fromByte(readByte(input));
            int permission = readByte(input);
            int aliasesIndex = readIntLE(input);
            CommandParamData[][] overloads = readArray(input, (aux) ->
                    readArray(aux, CommandParamData.INTERPRETER::interpret));
            return new CommandData(name, description, flags, permission, aliasesIndex, overloads);
        }
    };

    @Override
    public void write(@NotNull BitOutput output) throws IOException {
        writeString(output, name());
        writeString(output, description());
        byte flags = 0;
        for (Flag flag : flags()) {
            flags |= 1 << flag.ordinal();
        }
        writeByte(output, flags);
        writeByte(output, (byte) permission());

        writeIntLE(output, aliasesIndex());

        CommandParamData[][] overloads = overloads();
        writeUnsignedInt(output, overloads.length);
        for (CommandParamData[] overload : overloads) {
            writeUnsignedInt(output, overload.length);
            for (CommandParamData param : overload) {
                param.write(output);
            }
        }
    }

    public String toString() {
        StringBuilder overloads = new StringBuilder("[\r\n");

        for (CommandParamData[] overload : this.overloads) {
            overloads.append("    [\r\n");
            for (CommandParamData parameter : overload) {
                overloads.append("       ").append(parameter).append("\r\n");
            }
            overloads.append("    ]\r\n");
        }
        overloads.append("]\r\n");

        StringBuilder builder = new StringBuilder("CommandData(\r\n");
        List<?> objects = Arrays.asList("name=" + name, "description=" + description,
                "flags=" + Arrays.toString(flags.toArray()), "permission=" + permission,
                "overloads=" + overloads);

        for (Object object : objects) {
            builder.append("    ").append(Objects.toString(object).replaceAll("\r\n", "\r\n    ")).append("\r\n");
        }
        return builder.append(")").toString();
    }

    // Bit flags
    public enum Flag {
        USAGE,
        VISIBILITY,
        SYNC,
        EXECUTE,
        TYPE,
        CHEAT,
        UNKNOWN_6;

        public static List<Flag> fromByte(byte flags) {
            List<Flag> result = new ArrayList<>();
            for (Flag flag : values()) {
                if ((flags & (1 << flag.ordinal())) != 0) {
                    result.add(flag);
                }
            }
            return result;
        }
    }
}
