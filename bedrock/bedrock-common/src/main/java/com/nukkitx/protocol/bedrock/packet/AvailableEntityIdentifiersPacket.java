package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

interface AvailableEntityIdentifiersPacket extends BedrockPacket {
    @NotNull NbtMap identifiers();

    record v313(@NotNull NbtMap identifiers) implements AvailableEntityIdentifiersPacket, Codec313 {
        public static final Interpreter<v313> INTERPRETER = new Interpreter<v313>() {
            @Override
            public @NotNull v313 interpret(@NotNull BitInput input) throws IOException {
                return new v313(readNBTMap(input));
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeNBTMap(output, identifiers);
        }
    }
}
