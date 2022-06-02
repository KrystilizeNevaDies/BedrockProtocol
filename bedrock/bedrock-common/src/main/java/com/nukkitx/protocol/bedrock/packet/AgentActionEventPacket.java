package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.ee.AgentActionType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @since v503
 */
interface AgentActionEventPacket extends BedrockPacket {
    String requestId();
    AgentActionType actionType();
    /**
     * @see AgentActionType for valueType specific JSON
     */
    String responseJson();

    record v503(String requestId, AgentActionType actionType, String responseJson) implements AgentActionEventPacket, Codec503 {
        public static final Interpreter<v503> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v503 interpret(@NotNull BitInput input) throws IOException {
                return new v503(
                        readString(input),
                        VALUES[readIntLE(input)],
                        readString(input));
            }
        };

        private static final AgentActionType[] VALUES = AgentActionType.values();

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeString(output, requestId());
            writeIntLE(output, actionType().ordinal());
            writeString(output, responseJson());
        }
    }
}
