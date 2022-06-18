package com.nukkitx.protocol.bedrock.data;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public record EduSharedUriResource(String buttonName, String linkUri) implements BitDataWritable, PacketDataWriter {
    public static final EduSharedUriResource EMPTY = new EduSharedUriResource("", "");

    @Override
    public void write(@NotNull BitOutput output) throws IOException {
        writeString(output, buttonName());
        writeString(output, linkUri());
    }

    public static final BedrockPacket.Interpreter<EduSharedUriResource> INTERPRETER = new BedrockPacket.Interpreter<EduSharedUriResource>() {
        @Override
        public @NotNull EduSharedUriResource interpret(@NotNull BitInput input) throws IOException {
            String buttonName = readString(input);
            String linkUri = readString(input);
            return new EduSharedUriResource(buttonName, linkUri);
        }
    };
}