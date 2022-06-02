package com.nukkitx.protocol.bedrock.data;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface MapTrackedObject extends BitDataWritable, PacketDataWriter {

    BedrockPacket.Interpreter<MapTrackedObject> INTERPRETER = new BedrockPacket.Interpreter<>() {
        @Override
        public @NotNull MapTrackedObject interpret(@NotNull BitInput input) throws IOException {
            int id = readIntLE(input);
            return switch (id) {
                case ENTITY_TYPE -> new Entity(readLong(input));
                case BLOCK_TYPE -> new Block(readBlockPosition(input));
                default -> throw new IllegalArgumentException("Unknown tracked object id: " + id);
            };
        }
    };

    int ENTITY_TYPE = 0;
    record Entity(long entityId) implements MapTrackedObject {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeIntLE(output, ENTITY_TYPE);
            writeLong(output, entityId);
        }
    }

    int BLOCK_TYPE = 1;
    record Block(@NotNull Vector3i position) implements MapTrackedObject {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeIntLE(output, BLOCK_TYPE);
            writeBlockPosition(output, position);
        }
    }
}