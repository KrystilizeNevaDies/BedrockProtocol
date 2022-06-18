package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface ContainerClosePacket extends BedrockPacket {
    byte id();

    record v291(byte id) implements ContainerClosePacket {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<v291>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                byte id = readByte(input);
                return new v291(id);
            }
        };


        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeByte(output, id());
        }
    }

    record v419(byte id, boolean unknown) implements ContainerClosePacket {
        public static final Interpreter<v419> INTERPRETER = new Interpreter<v419>() {
            @Override
            public @NotNull v419 interpret(@NotNull BitInput input) throws IOException {
                byte id = readByte(input);
                boolean unknown = readBoolean(input);
                return new v419(id, unknown);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeByte(output, id());
            writeBoolean(output, unknown());
        }
    }


}
