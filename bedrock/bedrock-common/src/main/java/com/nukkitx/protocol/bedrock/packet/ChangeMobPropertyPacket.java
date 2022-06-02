package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
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
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Server-bound packet to change the properties of a mob.
 *
 * @since v503
 */
interface ChangeMobPropertyPacket extends BedrockPacket {
    long uniqueEntityId();
    String property();
    boolean boolValue();
    String stringValue();
    int intValue();
    float floatValue();

    record v503(long uniqueEntityId, String property, boolean boolValue, String stringValue, int intValue,
                float floatValue) implements ChangeMobPropertyPacket {
        public static final Interpreter<v503> INTERPRETER = new Interpreter<v503>() {
            @Override
            public @NotNull v503 interpret(@NotNull BitInput input) throws IOException {
                long uniqueEntityId = readLong(input);
                String property = readString(input);
                boolean boolValue = readBoolean(input);
                String stringValue = readString(input);
                int intValue = readInt(input);
                float floatValue = readFloatLE(input);
                return new v503(uniqueEntityId, property, boolValue, stringValue, intValue, floatValue);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeLong(output, uniqueEntityId());
            writeString(output, property());
            writeBoolean(output, boolValue());
            writeString(output, stringValue());
            writeInt(output, intValue());
            writeFloatLE(output, floatValue());
        }
    }
}