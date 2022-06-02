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

interface ClientCacheStatusPacket extends BedrockPacket {
    boolean supported();

    record v361(boolean supported) implements ClientCacheStatusPacket, Codec361 {
        public static final Interpreter<v361> INTERPRETER = new Interpreter<v361>() {
            @Override
            public @NotNull v361 interpret(@NotNull BitInput input) throws IOException {
                return new v361(readBoolean(input));
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            output.writeBoolean(supported);
        }
    }

}
