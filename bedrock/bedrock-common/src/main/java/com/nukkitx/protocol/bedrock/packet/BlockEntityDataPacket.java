package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface BlockEntityDataPacket extends BedrockPacket {
    @NotNull Vector3i blockPosition();

    @NotNull NbtMap data();

    record v291(@NotNull Vector3i blockPosition, @NotNull NbtMap data) implements BlockEntityDataPacket, Codec291 {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                return new v291(
                        readBlockPosition(input),
                        readNBTMap(input)
                );
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeBlockPosition(output, blockPosition());
            writeNBTMap(output, data());
        }
    }
}
