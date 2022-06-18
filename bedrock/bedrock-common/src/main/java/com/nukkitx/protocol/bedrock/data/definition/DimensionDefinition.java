package com.nukkitx.protocol.bedrock.data.definition;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataReader;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public record DimensionDefinition(String id, int maximumHeight, int minimumHeight,
                                  int generatorType) implements BitDataWritable, PacketDataWriter {
    public static final BedrockPacket.Interpreter<DimensionDefinition> INTERPRETER = new BedrockPacket.Interpreter<DimensionDefinition>() {
        @Override
        public @NotNull DimensionDefinition interpret(@NotNull BitInput input) throws IOException {
            String id = readString(input);
            int maximumHeight = readInt(input);
            int minimumHeight = readInt(input);
            int generatorType = readInt(input);
            return new DimensionDefinition(id, maximumHeight, minimumHeight, generatorType);
        }
    };

    @Override
    public void write(@NotNull BitOutput output) throws IOException {
        writeString(output, id());
        writeInt(output, maximumHeight());
        writeInt(output, minimumHeight());
        writeInt(output, generatorType());
    }
}