package com.nukkitx.protocol.serializer;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NBTInputStream;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtType;
import com.nukkitx.nbt.NbtUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

public interface PacketDataWriter extends BitDataWriter {
    static @NotNull PacketDataWriter create() {
        return new PacketDataWriter() {};
    }

    default void writeVector3i(@NotNull BitOutput output, @NotNull Vector3i value) {
    }

    default void writeVector3f(@NotNull BitOutput output, @NotNull Vector3f value) {

    }

    default void writeNBTMap(@NotNull BitOutput output, @NotNull NbtMap value) {

    }

    default void writeLongFlags(@NotNull BitOutput output, boolean @NotNull [] value) throws IOException {
        int length = Long.SIZE;
        for (int i = 0; i < length; i++) {
            output.writeBoolean(value.length > i && value[i]);
        }
    }

    default <T> void writeArray(@NotNull BitOutput output, T @NotNull [] value, IOExceptionWriter<T> writer) throws IOException {
        writeUnsignedInt(output, value.length);
        for (T t : value) {
            writer.apply(output, t);
        }
    }

    default <T extends BitDataWritable> void writeArray(@NotNull BitOutput output, T @NotNull[] value) throws IOException {
        writeUnsignedInt(output, value.length);
        for (T t : value) {
            t.write(output);
        }
    }

    default <T> void composeAndWriteArray(@NotNull BitOutput output, int length, Int2ObjectFunction<T> factory, IOExceptionWriter<T> writer) throws IOException {
        writeUnsignedInt(output, length);
        for (int i = 0; i < length; i++) {
            writer.apply(output, factory.get(i));
        }
    }

    default <T> void writeCollectionAsArray(@NotNull BitOutput output, @NotNull Collection<@NotNull T> value,
                                            IOExceptionWriter<T> writer) throws IOException {
        writeUnsignedInt(output, value.size());
        for (T t : value) {
            writer.apply(output, t);
        }
    }

    default void writeBlockPosition(@NotNull BitOutput input, @NotNull Vector3i value) throws IOException {
        writeInt(input, value.getX());
        writeUnsignedInt(input, value.getY());
        writeInt(input, value.getZ());
    }

    interface IOExceptionWriter<T> {
        void apply(@NotNull BitOutput output, @NotNull T value) throws IOException;
    }
}
