package com.nukkitx.protocol.bedrock.data;

import com.github.jinahya.bit.io.BitInput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import jdk.internal.foreign.abi.Binding;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public enum CodeBuilderCategoryType {
    NONE,
    CODE_STATUS,
    INSTANTIATION;
    public static final BedrockPacket.Interpreter<CodeBuilderCategoryType> INTERPRETER = new BedrockPacket.Interpreter<CodeBuilderCategoryType>() {
        @Override
        public @NotNull CodeBuilderCategoryType interpret(@NotNull BitInput input) throws IOException {
            return values()[readByte(input)];
        }
    };
}
