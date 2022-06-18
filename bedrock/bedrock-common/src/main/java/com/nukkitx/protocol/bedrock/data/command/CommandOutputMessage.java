package com.nukkitx.protocol.bedrock.data.command;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import lombok.NonNull;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public record CommandOutputMessage(boolean internal, @NonNull String message,
                                   String @NotNull [] parameters) implements BitDataWritable, PacketDataWriter {
    @Override
    public void write(@NotNull BitOutput output) throws IOException {
        writeBoolean(output, internal());
        writeString(output, message());
        writeArray(output, parameters(), this::writeString);
    }

    public static final BedrockPacket.Interpreter<CommandOutputMessage> INTERPRETER = new BedrockPacket.Interpreter<CommandOutputMessage>() {
        @Override
        public @NotNull CommandOutputMessage interpret(@NotNull BitInput input) throws IOException {
            boolean internal = readBoolean(input);
            String message = readString(input);
            String[] parameters = readArray(input, this::readString);
            return new CommandOutputMessage(internal, message, parameters);
        }
    };
}
