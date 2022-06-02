package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface AddPaintingPacket extends AddHangingEntityPacket {
    @NotNull String motive();

    record v291(long uniqueEntityId, long runtimeEntityId, @NotNull Vector3f position, int direction,
                @NotNull String motive) implements AddPaintingPacket, Codec291, PositionedByFloat {
        public static final Interpreter<AddPaintingPacket.v291> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                long uniqueEntityId = readLong(input);
                long runtimeEntityId = readUnsignedLong(input);
                Vector3f position = readVector3f(input);
                int direction = readInt(input);
                String motive = readString(input);
                return new v291(uniqueEntityId, runtimeEntityId, position, direction, motive);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeLong(output, uniqueEntityId());
            writeUnsignedLong(output, runtimeEntityId());
            writeVector3f(output, position());
            writeInt(output, direction());
            writeString(output, motive());
        }
    }

    record v332(long uniqueEntityId, long runtimeEntityId, @NotNull Vector3i position, int direction,
                @NotNull String motive) implements AddPaintingPacket, Codec332, PositionedByInt {
        public static final Interpreter<v332> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v332 interpret(@NotNull BitInput input) throws IOException {
                long uniqueEntityId = readLong(input);
                long runtimeEntityId = readUnsignedLong(input);
                Vector3i position = readBlockPosition(input);
                int direction = readInt(input);
                String motive = readString(input);
                return new v332(uniqueEntityId, runtimeEntityId, position, direction, motive);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeLong(output, uniqueEntityId());
            writeUnsignedLong(output, runtimeEntityId());
            writeBlockPosition(output, position());
            writeInt(output, direction());
            writeString(output, motive());
        }
    }

    record v361(long uniqueEntityId, long runtimeEntityId, @NotNull Vector3f position, int direction,
                @NotNull String motive) implements AddPaintingPacket, Codec291, PositionedByFloat {
        public static final Interpreter<v361> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v361 interpret(@NotNull BitInput input) throws IOException {
                long uniqueEntityId = readLong(input);
                long runtimeEntityId = readUnsignedLong(input);
                Vector3f position = readVector3f(input);
                int direction = readInt(input);
                String motive = readString(input);
                return new v361(uniqueEntityId, runtimeEntityId, position, direction, motive);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeLong(output, uniqueEntityId());
            writeUnsignedLong(output, runtimeEntityId());
            writeVector3f(output, position());
            writeInt(output, direction());
            writeString(output, motive());
        }
    }

}
