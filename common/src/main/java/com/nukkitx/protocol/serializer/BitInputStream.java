package com.nukkitx.protocol.serializer;

import com.github.jinahya.bit.io.BitInput;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

public class BitInputStream extends InputStream {

    private final BitInput input;

    public BitInputStream(final @NotNull BitInput input) {
        this.input = input;
    }

    @Override
    public int read() throws IOException {
        return input.readInt32();
    }
}
