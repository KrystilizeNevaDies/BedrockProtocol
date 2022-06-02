package com.nukkitx.protocol.bedrock.data.command;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

/**
 * CommandEnumConstraintData is sent in the AvailableCommandsPacket to limit what values of an enum may be used
 * taking in account things such as whether cheats are enabled.
 */
public record CommandEnumConstraintData(
        // The index of the value that the constraints should be applied to.
        int valueIndex,
        // The index of the enum that the constraints should be applied to.
        int enumIndex,
        // List of constraints that should be applied to the enum.
        @NotNull CommandEnumConstraintType @NotNull [] constraints
) implements PacketDataWriter, BitDataWritable {
    public static final BedrockPacket.Interpreter<CommandEnumConstraintData> INTERPRETER = new BedrockPacket.Interpreter<>() {
        @Override
        public @NotNull CommandEnumConstraintData interpret(@NotNull BitInput input) throws IOException {
            int valueIndex = readIntLE(input);
            int enumIndex = readIntLE(input);
            CommandEnumConstraintType[] constraints = readArray(input, CommandEnumConstraintType.INTERPRETER::interpret);
            return new CommandEnumConstraintData(valueIndex, enumIndex, constraints);
        }
    };

    @Override
    public void write(@NotNull BitOutput output) throws IOException {
        writeIntLE(output, valueIndex);
        writeIntLE(output, enumIndex);
        writeArray(output, constraints);
    }
}
