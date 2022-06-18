package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
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

public interface DisconnectPacket extends BedrockPacket {
    boolean messageSkipped();

    String kickMessage();


    // TODO: Should kickMessage be Nullable?
    record v291(boolean messageSkipped, String kickMessage) implements DisconnectPacket {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<v291>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                boolean messageSkipped = readBoolean(input);
                if (!messageSkipped) {
                    String kickMessage = readString(input);
                    return new v291(false, kickMessage);
                }
                return new v291(true, null);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeBoolean(output, messageSkipped());
            if (!messageSkipped()) {
                writeString(output, kickMessage());
            }
        }
    }
}
