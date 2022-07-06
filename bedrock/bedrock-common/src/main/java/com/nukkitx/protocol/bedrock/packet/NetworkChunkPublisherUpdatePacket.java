package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface NetworkChunkPublisherUpdatePacket extends BedrockPacket {
    Vector3i position();
    int radius();


    record v313(Vector3i position, @Unsigned int radius) implements NetworkChunkPublisherUpdatePacket {
        public static final Interpreter<v313> INTERPRETER = Interpreter.generate(v313.class);
        public static final Deferer<v313> DEFERER = Deferer.generate(v313.class);

        @Override
        public void write(@org.jetbrains.annotations.NotNull com.github.jinahya.bit.io.BitOutput output) throws java.io.IOException {
            DEFERER.defer(output, this);
        }
    }

}
