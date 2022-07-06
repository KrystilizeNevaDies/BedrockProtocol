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

interface PhotoInfoRequestPacket extends BedrockPacket {
    long photoId();


    record v471(long photoId) implements PhotoInfoRequestPacket {
        public static final Interpreter<v471> INTERPRETER = Interpreter.generate(v471.class);
        public static final Deferer<v471> DEFERER = Deferer.generate(v471.class);

        @Override
        public void write(@NotNull BitOutput writer) throws IOException {
            DEFERER.defer(writer, this);
        }
    }

}
