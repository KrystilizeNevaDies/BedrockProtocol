package com.nukkitx.protocol.bedrock.data.command;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataReader;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import lombok.NonNull;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.function.Function;

public record CommandEnumData(@NotNull String name, @NotNull String[] values,
                              boolean isSoft) implements PacketDataWriter, BitDataWritable {
    public static final BedrockPacket.@NotNull Interpreter<CommandEnumData> INTERPRETER = new BedrockPacket.Interpreter<>() {
        @Override
        public @NotNull CommandEnumData interpret(@NotNull BitInput input) throws IOException {
            String name = readString(input);
            String[] values = readArray(input, this::readString);
            boolean isSoft = input.readBoolean();
            return new CommandEnumData(name, values, isSoft);
        }
    };

    @Override
    public void write(@NotNull BitOutput output) throws IOException {
        writeString(output, name);
        writeArray(output, values, this::writeString);
        output.writeBoolean(isSoft);
    }
}
