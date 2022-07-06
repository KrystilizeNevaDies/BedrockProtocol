package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.entity.EntityEventType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface EntityEventPacket extends BedrockPacket {
    long runtimeEntityId();
    EntityEventType entityEventType();
    int data();


    record v291(long runtimeEntityId, EntityEventType entityEventType, int data) implements EntityEventPacket {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<v291>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                long runtimeEntityId = readUnsignedLong(input);
                int entityEventTypeId = readUnsignedByte(input);
                EntityEventType entityEventType = EntityEventType.from(entityEventTypeId);
                int data = readInt(input);
                return new v291(runtimeEntityId, entityEventType, data);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeUnsignedLong(output, runtimeEntityId());
            writeByte(output, (byte) entityEventType().id());
            writeInt(output, data());
        }
    }

}
