package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.CodeBuilderCategoryType;
import com.nukkitx.protocol.bedrock.data.CodeBuilderOperationType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

interface CodeBuilderSourcePacket extends BedrockPacket {

    @NotNull CodeBuilderOperationType operation();
    @NotNull CodeBuilderCategoryType category();
    @NotNull String value();


    record v486(@NotNull CodeBuilderOperationType operation, @NotNull CodeBuilderCategoryType category, @NotNull String value) implements CodeBuilderSourcePacket {
        public static final Interpreter<v486> INTERPRETER = new Interpreter<v486>() {
            @Override
            public @NotNull v486 interpret(@NotNull BitInput input) throws IOException {
                CodeBuilderOperationType operation = CodeBuilderOperationType.INTERPRETER.interpret(input);
                CodeBuilderCategoryType category = CodeBuilderCategoryType.INTERPRETER.interpret(input);
                String value = readString(input);

                return new v486(operation, category, value);
            }
        };



        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeByte(output, (byte) operation().ordinal());
            writeByte(output, (byte) category().ordinal());
            writeString(output, value());
        }
    }

}
