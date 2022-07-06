package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface NetworkSettingsPacket extends BedrockPacket {
    /**
     * The smallest amount of bytes that should be compressed by the client. 0-65535
     */
    int compressionThreshold();


    record v388(@AsShort @LE int compressionThreshold) implements NetworkSettingsPacket {
        public static final Interpreter<v388> INTERPRETER = Interpreter.generate(v388.class);
        public static final Deferer<v388> DEFERER = Deferer.generate(v388.class);

        @Override
        public void write(@org.jetbrains.annotations.NotNull com.github.jinahya.bit.io.BitOutput writer) throws java.io.IOException {
            DEFERER.defer(writer, this);
        }
    }

}
