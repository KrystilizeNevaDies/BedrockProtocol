package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3f;
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

public interface ChangeDimensionPacket extends BedrockPacket {
    int dimension();

    @NotNull Vector3f position();

    boolean respawn();

    record v291(int dimension, Vector3f position, boolean respawn) implements ChangeDimensionPacket {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<v291>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                int dimension = readInt(input);
                Vector3f position = readVector3f(input);
                boolean respawn = input.readBoolean();
                return new v291(dimension, position, respawn);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeInt(output, dimension());
            writeVector3f(output, position());
            writeBoolean(output, respawn());
        }
    }

}
