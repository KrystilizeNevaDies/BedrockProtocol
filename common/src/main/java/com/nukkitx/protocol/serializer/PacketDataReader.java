package com.nukkitx.protocol.serializer;

import com.github.jinahya.bit.io.BitInput;
import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NBTInputStream;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtType;
import com.nukkitx.nbt.NbtUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public interface PacketDataReader extends BitDataReader {

    default UUID readUuid(@NotNull BitInput input) throws IOException {

        return new UUID(readLongLE(input), readLongLE(input));
    }

    default @NotNull Vector3f readVector3f(@NotNull BitInput input) throws IOException {
        float x = readFloatLE(input);
        float y = readFloatLE(input);
        float z = readFloatLE(input);
        return Vector3f.from(x, y, z);
    }

    default Vector2f readVector2f(@NotNull BitInput input) throws IOException {
        float x = readFloatLE(input);
        float y = readFloatLE(input);
        return Vector2f.from(x, y);
    }

    default @NotNull Vector3i readVector3i(@NotNull BitInput input) throws IOException {
        int x = readIntLE(input);
        int y = readIntLE(input);
        int z = readIntLE(input);
        return Vector3i.from(x, y, z);
    }

    default Vector3i readBlockPosition(@NotNull BitInput input) throws IOException {
        return readVector3i(input);
    }

    default Vector3f readByteRotation(@NotNull BitInput input) throws IOException {
        float pitch = readByteAngle(input);
        float yaw = readByteAngle(input);
        float roll = readByteAngle(input);
        return Vector3f.from(pitch, yaw, roll);
    }

    default float readByteAngle(@NotNull BitInput input) throws IOException {
        return input.readByte8() * (360f / 256f);
    }

    default <T> T readTag(@NotNull BitInput input) throws IOException {
        NBTInputStream reader = NbtUtils.createNetworkReader(new BitInputStream(input));
        //noinspection unchecked
        return (T) reader.readTag();
    }

    default <T> T readTagValue(@NotNull BitInput input, NbtType<T> type) throws IOException {
        NBTInputStream reader = NbtUtils.createNetworkReader(new BitInputStream(input));
        return reader.readValue(type);
    }

    default <T> T @NotNull [] readArray(@NotNull BitInput input, @NotNull IOExceptionSupplier<T> supplier) throws IOException {
        int length = readUnsignedInt(input);
        //noinspection unchecked
        T[] array = (T[]) new Object[length];
        for (int i = 0; i < length; i++) {
            array[i] = supplier.get();
        }
        return array;
    }

    default <T> T @NotNull [] readArray(@NotNull BitInput input, @NotNull IOExceptionFunction<T> function) throws IOException {
        int length = readUnsignedInt(input);
        //noinspection unchecked
        T[] array = (T[]) new Object[length];
        for (int i = 0; i < length; i++) {
            array[i] = function.apply(input);
        }
        return array;
    }

    default <T> Set<T> readArrayToSet(@NotNull BitInput input, @NotNull IOExceptionFunction<T> function) throws IOException {
        return Set.of(readArray(input, function));
    }

    default @NotNull NbtMap readNBTMap(@NotNull BitInput input) throws IOException {
        NBTInputStream reader = NbtUtils.createNetworkReader(new BitInputStream(input));
        return reader.readValue(NbtType.COMPOUND);
    }

    default boolean @NotNull [] readLongFlags(@NotNull BitInput input) throws IOException {
        long flags = readLong(input);
        boolean[] array = new boolean[64];
        for (int i = 0; i < 64; i++) {
            array[i] = (flags & (1L << i)) != 0;
        }
        return array;
    }

    interface IOExceptionSupplier<T> {
        T get() throws IOException;
    }

    interface IOExceptionFunction<T> {
        T apply(@NotNull BitInput input) throws IOException;
    }
}
