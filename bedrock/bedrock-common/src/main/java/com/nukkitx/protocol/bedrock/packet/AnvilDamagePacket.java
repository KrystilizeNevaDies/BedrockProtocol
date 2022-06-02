package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3i;
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

interface AnvilDamagePacket extends BedrockPacket {

    byte damage();
    @NotNull Vector3i position();

    record v388(byte damage, @NotNull Vector3i position) implements AnvilDamagePacket, Codec388 {
        public static final Interpreter<v388> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v388 interpret(@NotNull BitInput input) throws IOException {
                byte damage = readByte(input);
                Vector3i position = readBlockPosition(input);
                return new v388(damage, position);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeByte(output, damage());
            writeBlockPosition(output, position());
        }
    }

}
