package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface NetworkStackLatencyPacket extends BedrockPacket {
    long timestamp();
//    boolean fromServer;


    record v291(@LE long timestamp) implements NetworkStackLatencyPacket {
        public static final Interpreter<v291> INTERPRETER = Interpreter.generate(v291.class);
        public static final Deferer<v291> DEFERER = Deferer.generate(v291.class);

        @Override
        public void write(@org.jetbrains.annotations.NotNull BitOutput writer) throws java.io.IOException {
            DEFERER.defer(writer, this);
        }
    }

    record v332(@LE long timestamp, boolean isFromServer) implements NetworkStackLatencyPacket {
        public static final Interpreter<v332> INTERPRETER = Interpreter.generate(v332.class);
        public static final Deferer<v332> DEFERER = Deferer.generate(v332.class);

        @Override
        public void write(@org.jetbrains.annotations.NotNull BitOutput writer) throws java.io.IOException {
            DEFERER.defer(writer, this);
        }
    }
}
