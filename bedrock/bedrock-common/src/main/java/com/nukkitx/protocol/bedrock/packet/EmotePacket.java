package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.EmoteFlag;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

interface EmotePacket extends BedrockPacket {
    long runtimeEntityId();
    String emoteId();
    Set<EmoteFlag> flags();


    record v388(long runtimeEntityId, String emoteId, Set<EmoteFlag> flags) implements EmotePacket {

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeUnsignedLong(output, runtimeEntityId());
            writeString(output, emoteId());
            int flags = 0;
            for (EmoteFlag flag : flags()) {
                flags |= 1 << flag.ordinal();
            }
            writeByte(output, (byte) flags);
        }

        public static final Interpreter<v388> INTERPRETER = new Interpreter<v388>() {
            @Override
            public @NotNull v388 interpret(@NotNull BitInput input) throws IOException {
                long runtimeEntityId = readUnsignedLong(input);
                String emoteId = readString(input);
                int flags = readUnsignedByte(input);
                Set<EmoteFlag> flagSet = new HashSet<>();
                // TODO: Actually read the flags properly
                if ((flags & 0b1) != 0) flagSet.add(EmoteFlag.SERVER_SIDE);
                return new v388(runtimeEntityId, emoteId, Set.copyOf(flagSet));
            }
        };
    }

}
