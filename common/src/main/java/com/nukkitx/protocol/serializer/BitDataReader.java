package com.nukkitx.protocol.serializer;

import com.github.jinahya.bit.io.BitInput;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.AsciiString;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface BitDataReader {
    @NotNull BitDataReader INSTANCE = new BitDataReader() {};
    /**
     * Reads an unsigned int from the input.
     * @param input the input
     * @return the unsigned int
     * @throws IOException if an I/O error occurs
     */
    default int readUnsignedInt(@NotNull BitInput input) throws IOException {
        return (int) decode(input);
    }

    /**
     * Reads an int from the input.
     * @param input the input
     * @return the int
     * @throws IOException if an I/O error occurs
     */
    default int readInt(@NotNull BitInput input) throws IOException {
        int n = (int) decode(input);
        return n >>> 1 ^ -(n & 1);
    }

    /**
     * Reads a LE int from the input.
     * @param input the input
     * @return the int
     * @throws IOException if an I/O error occurs
     */
    default int readIntLE(@NotNull BitInput input) throws IOException {
        return Integer.reverse(input.readInt32());
    }

    /**
     * Reads an unsigned long from the input.
     * @param input the input
     * @return the unsigned long
     * @throws IOException if an I/O error occurs
     */
    default long readUnsignedLong(@NotNull BitInput input) throws IOException {
        return decode(input);
    }

    /**
     * Reads a long from the input.
     * @param input the input
     * @return the long
     * @throws IOException if an I/O error occurs
     */
    default long readLong(@NotNull BitInput input) throws IOException {
        long n = decode(input);
        return n >>> 1 ^ -(n & 1L);
    }

    /**
     * Reads a LE long from the input.
     * @param input the input
     * @return the long
     * @throws IOException if an I/O error occurs
     */
    default long readLongLE(@NotNull BitInput input) throws IOException {
        return Long.reverse(input.readLong64());
    }

    default int readUnsignedByte(@NotNull BitInput input) throws IOException {
        return input.readUnsignedInt(Byte.SIZE);
    }

    default byte readByte(@NotNull BitInput input) throws IOException {
        return input.readByte8();
    }

    default short readShortLE(@NotNull BitInput input) throws IOException {
        return (short) (Integer.reverse(input.readShort16()) >> Short.SIZE);
    }

    default float readFloat(@NotNull BitInput input) throws IOException {
        return Float.intBitsToFloat(readInt(input));
    }

    default float readFloatLE(@NotNull BitInput input) throws IOException {
        return Float.intBitsToFloat(readIntLE(input));
    }

    default double readDouble(@NotNull BitInput input) throws IOException {
        return Double.longBitsToDouble(readLong(input));
    }

    default double readDoubleLE(@NotNull BitInput input) throws IOException {
        return Double.longBitsToDouble(readLongLE(input));
    }

    default boolean readBoolean(@NotNull BitInput input) throws IOException {
        return input.readBoolean();
    }

    /**
     * Reads a byte array from the input.
     * @param input the input
     * @return the byte array
     * @throws IOException if an I/O error occurs
     */
    default byte @NotNull [] readByteArray(@NotNull BitInput input) throws IOException {
        return readBytes(input, readUnsignedInt(input));
    }

    /**
     * Reads a long array from the input.
     * @param input the input
     * @return the long array
     * @throws IOException if an I/O error occurs
     */
    default long @NotNull [] readLongArray(@NotNull BitInput input) throws IOException {
        int length = readUnsignedInt(input);
        long[] array = new long[length];
        for (int i = 0; i < length; i++) {
            array[i] = readLong(input);
        }
        return array;
    }

    /**
     * Reads the specified number of bytes from the input.
     * @param input the input
     * @param length the number of bytes to read
     * @return the byte array
     * @throws IOException if an I/O error occurs
     */
    default byte[] readBytes(@NotNull BitInput input, int length) throws IOException {
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = readByte(input);
        }
        return result;
    }

    /**
     * Reads a UTF-8 string from the input.
     * @param input the input
     * @return the string
     * @throws IOException if an I/O error occurs
     */
    default @NotNull String readString(@NotNull BitInput input) throws IOException {
        return new String(readByteArray(input), StandardCharsets.UTF_8);
    }

    /**
     * Reads a LE ASCII string from the input.
     * @param input the input
     * @return the string
     * @throws IOException if an I/O error occurs
     */
    default @NotNull AsciiString readLEAsciiString(@NotNull BitInput input) throws IOException {
        String string = decodeString(input, readIntLE(input), StandardCharsets.US_ASCII);
        // Older Netty versions do not necessarily provide an AsciiString for this method
        return new AsciiString(string);
    }

    /**
     * Decodes a string from the input.
     * @param input the input
     * @param length the length of the string
     * @param charset the charset
     * @return the string
     * @throws IOException if an I/O error occurs
     */
    default @NotNull String decodeString(@NotNull BitInput input, int length, @NotNull Charset charset) throws IOException {
        if (length == 0) {
            return "";
        }
        byte[] bytes = readBytes(input, length);

        return new String(bytes, charset);
    }

    /**
     * Decodes a section of the input.
     * @param input the input
     * @return the decoded value
     * @throws IOException if an I/O error occurs
     */
    default long decode(@NotNull BitInput input) throws IOException {
        long result = 0L;

        for (int shift = 0; shift < 64; shift += 7) {
            byte b = input.readByte8();
            result |= ((long)b & 127L) << shift;
            if ((b & 128) == 0) {
                return result;
            }
        }

        return result;
    }

    /**
     * Reads a byte buffer from the input stream.
     * @param input the input stream
     * @return the byte buffer
     * @throws IOException if an I/O error occurs
     */
    default @NotNull ByteBuf readBuffer(BitInput input) throws IOException {
        byte[] bytes = readBytes(input, readUnsignedInt(input));
        return Unpooled.wrappedBuffer(bytes);
    }
}
