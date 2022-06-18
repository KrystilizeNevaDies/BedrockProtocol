package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface ContainerOpenPacket extends BedrockPacket {
    byte id();

    ContainerType valueType();

    Vector3i blockPosition();

    long uniqueEntityId();


    record v291(byte id, @NotNull ContainerType valueType,
                @NotNull Vector3i blockPosition, long uniqueEntityId) implements ContainerOpenPacket {

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeByte(output, id());
            writeByte(output, (byte) valueType().getId());
            writeBlockPosition(output, blockPosition());
            writeLong(output, uniqueEntityId());
        }

        public static final Interpreter<v291> INTERPRETER = new Interpreter<v291>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                byte id = readByte(input);
                ContainerType containerType = ContainerType.from(readByte(input));
                Vector3i blockPosition = readBlockPosition(input);
                long uniqueEntityId = readLong(input);
                return new v291(id, containerType, blockPosition, uniqueEntityId);
            }
        };
    }

}
