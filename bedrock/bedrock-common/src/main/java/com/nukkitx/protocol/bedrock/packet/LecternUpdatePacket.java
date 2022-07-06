package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3i;
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

interface LecternUpdatePacket extends BedrockPacket {
    int page();
//    int totalPages();
    Vector3i blockPosition();
    boolean isDroppingBook();


    record v340(@AsByte int page, @BlockPosition Vector3i blockPosition, boolean isDroppingBook) implements LecternUpdatePacket {
        public static final Interpreter<v340> INTERPRETER = Interpreter.generate(v340.class);
        private static final Deferer<v340> DEFERER = Deferer.generate(v340.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

    record v354(@AsByte int page, @AsByte int totalPages, @BlockPosition Vector3i blockPosition, boolean isDroppingBook) implements LecternUpdatePacket {
        public static final Interpreter<v354> INTERPRETER = Interpreter.generate(v354.class);
        private static final Deferer<v354> DEFERER = Deferer.generate(v354.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }
}
