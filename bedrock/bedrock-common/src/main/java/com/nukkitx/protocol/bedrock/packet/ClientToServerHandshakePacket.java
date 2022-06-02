package com.nukkitx.protocol.bedrock.packet;

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

interface ClientToServerHandshakePacket extends BedrockPacket {

    record v291() implements ClientToServerHandshakePacket, Codec291 {
        public static final Interpreter<v291> INTERPRETER = input -> new v291();

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
        }
    }
}
