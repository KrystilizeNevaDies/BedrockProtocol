package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
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

interface DebugInfoPacket extends BedrockPacket {
    long uniqueEntityId();

    @NotNull String data();


    record v407(long uniqueEntityId, String data) implements DebugInfoPacket {
        public static final Interpreter<v407> INTERPRETER = new Interpreter<v407>() {
            @Override
            public @NotNull v407 interpret(@NotNull BitInput input) throws IOException {
                long uniqueEntityId = readLong(input);
                String data = readString(input);
                return new v407(uniqueEntityId, data);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeLong(output, uniqueEntityId());
            writeString(output, data());
        }
    }

}
