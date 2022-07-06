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
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

interface EmoteListPacket extends BedrockPacket {
    long runtimeEntityId();
    UUID[] pieceIds();


    record v407(long runtimeEntityId, UUID[] pieceIds) implements EmoteListPacket {

        public static final Interpreter<v407> INTERPRETER = new Interpreter<v407>() {
            @Override
            public @NotNull v407 interpret(@NotNull BitInput input) throws IOException {
                long runtimeEntityId = readUnsignedLong(input);
                UUID[] pieceIds = readArray(input, this::readUuid);
                return new v407(runtimeEntityId, pieceIds);
            }
        };
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeUnsignedLong(output, runtimeEntityId());
            writeArray(output, pieceIds(), this::writeUuid);
        }
    }

}
