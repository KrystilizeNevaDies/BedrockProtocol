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

public interface CameraPacket extends BedrockPacket {
    long cameraUniqueEntityId();
    long playerUniqueEntityId();

    record v291(long cameraUniqueEntityId, long playerUniqueEntityId) implements CameraPacket {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<v291>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                long cameraUniqueEntityId = readLong(input);
                long playerUniqueEntityId = readLong(input);
                return new v291(cameraUniqueEntityId, playerUniqueEntityId);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeLong(output, cameraUniqueEntityId());
            writeLong(output, playerUniqueEntityId());
        }
    }
}
