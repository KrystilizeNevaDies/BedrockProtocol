package com.nukkitx.protocol.bedrock.data;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataReader;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public record MapDecoration(int image, int rotation, int xOffset, int yOffset, @NotNull String label,
                            int color) implements BitDataWritable, PacketDataWriter {
    public static final BedrockPacket.Interpreter<MapDecoration> INTERPRETER = new BedrockPacket.Interpreter<MapDecoration>() {
        @Override
        public @NotNull MapDecoration interpret(@NotNull BitInput input) throws IOException {
            int image = readByte(input);
            int rotation = readByte(input);
            int xOffset = readByte(input);
            int yOffset = readByte(input);
            String label = readString(input);
            int color = readUnsignedInt(input);
            return new MapDecoration(image, rotation, xOffset, yOffset, label, color);
        }
    };

    @Override
    public void write(@NotNull BitOutput output) throws IOException {
        writeByte(output, (byte) image());
        writeByte(output, (byte) rotation());
        writeByte(output, (byte) xOffset());
        writeByte(output, (byte) yOffset());
        writeString(output, label());
        writeUnsignedInt(output, color());
    }
}
