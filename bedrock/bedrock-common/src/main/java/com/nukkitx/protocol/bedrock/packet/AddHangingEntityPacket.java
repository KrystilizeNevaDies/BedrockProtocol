package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface AddHangingEntityPacket extends BedrockPacket {
    long uniqueEntityId();

    long runtimeEntityId();

    int direction();

    // One of these interfaces is required.
    // If they are not present, an IllegalStateException should be thrown.
    interface PositionedByFloat {
        @NotNull Vector3f position();
    }

    interface PositionedByInt {
        @NotNull Vector3i position();
    }

    record v291(long uniqueEntityId, long runtimeEntityId, @NotNull Vector3f position, int direction
    ) implements AddHangingEntityPacket, Codec291, PositionedByFloat {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                return new v291(
                        readLong(input),
                        readUnsignedLong(input),
                        readVector3f(input),
                        readInt(input)
                );
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeLong(output, uniqueEntityId);
            writeUnsignedLong(output, runtimeEntityId);
            writeVector3f(output, position);
            writeInt(output, direction);
        }
    }
}
