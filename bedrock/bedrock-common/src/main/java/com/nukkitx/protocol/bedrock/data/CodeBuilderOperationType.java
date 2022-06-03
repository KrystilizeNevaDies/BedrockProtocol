package com.nukkitx.protocol.bedrock.data;

import com.github.jinahya.bit.io.BitInput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import jdk.internal.foreign.abi.Binding;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public enum CodeBuilderOperationType {
    NONE,
    GET,
    SET,
    RESET;
    public static final BedrockPacket.Interpreter<CodeBuilderOperationType> INTERPRETER = new BedrockPacket.Interpreter<CodeBuilderOperationType>() {
        @Override
        public @NotNull CodeBuilderOperationType interpret(@NotNull BitInput input) throws IOException {
            return values()[readByte(input)];
        }
    };
}
