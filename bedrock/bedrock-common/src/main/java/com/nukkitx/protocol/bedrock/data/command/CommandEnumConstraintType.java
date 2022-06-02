package com.nukkitx.protocol.bedrock.data.command;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public enum CommandEnumConstraintType implements PacketDataWriter, BitDataWritable {
    CHEATS_ENABLED,
    OPERATOR_PERMISSIONS,
    HOST_PERMISSIONS,
    UNKNOWN_3;

    public static final BedrockPacket.Interpreter<CommandEnumConstraintType> INTERPRETER = new BedrockPacket.Interpreter<>() {
        @Override
        public @NotNull CommandEnumConstraintType interpret(@NotNull BitInput input) throws IOException {
            int ordinal = readByte(input);
            if (ordinal >= 0 && ordinal < values().length) {
                return values()[ordinal];
            }
            throw new IllegalStateException("Invalid ordinal " + ordinal);
        }
    };

    @Override
    public void write(@NotNull BitOutput output) throws IOException {
        writeByte(output, (byte) ordinal());
    }
}
