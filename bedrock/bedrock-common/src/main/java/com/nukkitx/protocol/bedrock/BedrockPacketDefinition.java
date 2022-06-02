package com.nukkitx.protocol.bedrock;

import com.github.jinahya.bit.io.BitInput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@Value
public class BedrockPacketDefinition<T extends BedrockPacket> {
    int id;
    Supplier<T> factory;
    BedrockPacketReader<T> serializer;

    public @NotNull T read(@NotNull BitInput input) {
        throw new UnsupportedOperationException();
    }
}
