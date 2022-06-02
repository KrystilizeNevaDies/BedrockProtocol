package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

interface CodeBuilderPacket extends BedrockPacket {
    String url();
    boolean opening();


    record v407(String url, boolean opening) implements CodeBuilderPacket {
        public static final Interpreter<v407> INTERPRETER = new Interpreter<v407>() {
            @Override
            public @NotNull v407 interpret(@NotNull BitInput input) throws IOException {
                return new v407(readString(input), readBoolean(input));
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeString(output, url());
            writeBoolean(output, opening());
        }
    }

}
