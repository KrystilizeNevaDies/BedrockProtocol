package com.nukkitx.protocol.serializer;

import com.github.jinahya.bit.io.BitInput;
import org.jetbrains.annotations.NotNull;

public interface PacketReader<P> extends PacketDataReader {
    @NotNull P read(@NotNull BitInput input);
}
