package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface AddBehaviorTreePacket extends BedrockPacket {
    @NotNull String json();

    record v291(@NotNull String json) implements AddBehaviorTreePacket, Codec291 {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<>() {

            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                return new v291(readString(input));
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeString(output, json);
        }
    }
}
