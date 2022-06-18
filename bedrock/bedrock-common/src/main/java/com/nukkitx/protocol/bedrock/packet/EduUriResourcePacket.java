package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.EduSharedUriResource;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

interface EduUriResourcePacket extends BedrockPacket {
    EduSharedUriResource eduSharedUriResource();


    record v465(EduSharedUriResource eduSharedUriResource) implements EduUriResourcePacket {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            eduSharedUriResource().write(output);
        }

        public static final Interpreter<v465> INTERPRETER = new Interpreter<v465>() {
            @Override
            public @NotNull v465 interpret(@NotNull BitInput input) throws IOException {
                EduSharedUriResource eduSharedUriResource = EduSharedUriResource.INTERPRETER.interpret(input);
                return new v465(eduSharedUriResource);
            }
        };
    }
}
