package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.MultiplayerMode;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;

interface MultiplayerSettingsPacket extends BedrockPacket {
    int modeId();


    record v388(int modeId) implements MultiplayerSettingsPacket {
        public v388(MultiplayerMode mode) {
            this(mode.id());
        }
        public static final Interpreter<v388> INTERPRETER = Interpreter.generate(v388.class);
        public static final Deferer<v388> DEFERER = Deferer.generate(v388.class);

        @Override
        public void write(@org.jetbrains.annotations.NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

}
