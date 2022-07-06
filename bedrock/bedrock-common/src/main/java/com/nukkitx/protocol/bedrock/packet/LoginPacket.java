package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import io.netty.util.AsciiString;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

public interface LoginPacket extends BedrockPacket {
    int protocolVersion();
    int unknown();
    AsciiString chainData();
    AsciiString skinData();


    record v291(int protocolVersion, @Unsigned int unknown, AsciiString chainData,
                AsciiString skinData) implements LoginPacket {

        public v291(int protocolVersion, AsciiString chainData, AsciiString skinData) {
            this(protocolVersion, chainData.length() + skinData.length() + 8, chainData, skinData);
        }

        public static final Interpreter<v291> INTERPRETER = Interpreter.generate(v291.class);
        private static final Deferer<v291> DEFERER = Deferer.generate(v291.class);

        public void write(@org.jetbrains.annotations.NotNull com.github.jinahya.bit.io.BitOutput output) throws java.io.IOException {
            DEFERER.defer(output, this);
        }
    }
}
