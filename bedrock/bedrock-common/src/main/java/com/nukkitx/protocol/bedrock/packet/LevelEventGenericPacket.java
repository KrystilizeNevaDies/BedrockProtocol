package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtType;
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

interface LevelEventGenericPacket extends BedrockPacket {
    int eventId();
    NbtMap tag();


    record v361(int eventId, NbtMap tag) implements LevelEventGenericPacket {
        public static final Interpreter<v361> INTERPRETER = Interpreter.generate(v361.class);
        private static final Deferer<v361> DEFERER = Deferer.generate(v361.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }
}
