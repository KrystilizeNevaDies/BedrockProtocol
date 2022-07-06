package com.nukkitx.protocol.bedrock.packet;

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

interface MapCreateLockedCopyPacket extends BedrockPacket {
    long originalMapId();
    long newMapId();


    record v354(long originalMapId, long newMapId) implements MapCreateLockedCopyPacket {
        public static final Interpreter<v354> INTERPRETER = Interpreter.generate(v354.class);
        public static final Deferer<v354> DEFERER = Deferer.generate(v354.class);


        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

}
