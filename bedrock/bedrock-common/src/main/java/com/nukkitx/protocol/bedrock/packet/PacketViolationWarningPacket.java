package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.PacketViolationSeverity;
import com.nukkitx.protocol.bedrock.data.PacketViolationType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

interface PacketViolationWarningPacket extends BedrockPacket {
    int typeId();
    int severityId();
    int causeId();
    String context();


    /**
     * For some reason, typeId and severityId is decremented before being sent and incremented when being received.
     * Probably a bedrock bug.
     *
     * So if you are using the constructor with the direct ints, make sure to decrement the id before sending.
     * e.g. if you want type with id 0, you would set -1 in the constructor.
     */
    record v407(int typeId, int severityId, int causeId, String context) implements PacketViolationWarningPacket {

        public v407(PacketViolationType type, PacketViolationSeverity severity, int causeId,
                    String context) {
            this(type.ordinal() - 1, severity.ordinal() - 1, causeId, context); // see here, the offset
        }

        public static final Interpreter<v407> INTERPRETER = Interpreter.generate(v407.class);
        public static final Deferer<v407> DEFERER = Deferer.generate(v407.class);

        @Override
        public void write(@NotNull BitOutput writer) throws IOException {
            DEFERER.defer(writer, this);
        }
    }

}
