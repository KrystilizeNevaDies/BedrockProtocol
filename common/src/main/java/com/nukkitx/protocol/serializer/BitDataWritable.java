package com.nukkitx.protocol.serializer;

import com.github.jinahya.bit.io.BitOutput;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;

public interface BitDataWritable {
    /**
     * Writes the packet's binary content to the given output.
     * @param output the output to write to
     */
    void write(@NotNull BitOutput output) throws IOException;
}
