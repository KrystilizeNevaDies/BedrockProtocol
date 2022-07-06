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
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface EntityPickRequestPacket extends BedrockPacket {
    long runtimeEntityId();
    int hotbarSlot();


    record v291(long runtimeEntityId, @AsByte int hotbarSlot) implements EntityPickRequestPacket {
        public static final Interpreter<v291> INTERPRETER = Interpreter.generate(v291.class);
        private static final Deferer<v291> DEFERER = Deferer.generate(v291.class);
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

    record v465(long runtimeEntityId, @AsByte int hotbarSlot, boolean data) implements EntityPickRequestPacket {
        public static final Interpreter<v465> INTERPRETER = Interpreter.generate(v465.class);
        private static final Deferer<v465> DEFERER = Deferer.generate(v465.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }
}
