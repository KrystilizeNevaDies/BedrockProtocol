package com.nukkitx.protocol.serializer;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.AsciiString;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public interface BitDataWriter {

    default void writeBoolean(@NotNull BitOutput output, boolean value) throws IOException {
    }

    default void writeUnsignedInt(@NotNull BitOutput output, int value) throws IOException {
    }

    default void writeInt(@NotNull BitOutput output, int value) throws IOException {
    }

    default void writeIntLE(@NotNull BitOutput output, int value) throws IOException {
    }

    default void writeByte(@NotNull BitOutput output, byte value) throws IOException {
    }

    default void writeUnsignedLong(@NotNull BitOutput output, long value) throws IOException {

    }

    default void writeLong(@NotNull BitOutput output, long value) throws IOException {

    }

    default void writeLongLE(@NotNull BitOutput output, long value) throws IOException {

    }

    default void writeShortLE(@NotNull BitOutput output, short value) throws IOException {

    }

    default void writeFloat(@NotNull BitOutput output, float value) throws IOException {

    }

    default void writeFloatLE(@NotNull BitOutput output, float value) throws IOException {

    }

    default void writeString(@NotNull BitOutput output, @NotNull String value) throws IOException {

    }

    default void writeUuid(@NotNull BitOutput output, @NotNull UUID uuid) throws IOException {
        writeUnsignedLong(output, uuid.getMostSignificantBits());
        writeUnsignedLong(output, uuid.getLeastSignificantBits());
    }

    default void writeLongArray(@NotNull BitOutput output, @NotNull long[] array) throws IOException {
        writeUnsignedInt(output, array.length);
        for (long value : array) {
            writeLong(output, value);
        }
    }
}
