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

interface OnScreenTextureAnimationPacket extends BedrockPacket {
    int effectId();

    record v354(@LE int effectId) implements OnScreenTextureAnimationPacket {
        public static final Interpreter<v354> INTERPRETER = Interpreter.generate(v354.class);
        public static final Deferer<v354> DEFERER = Deferer.generate(v354.class);

        @Override
        public void write(@org.jetbrains.annotations.NotNull BitOutput writer) throws java.io.IOException {
            DEFERER.defer(writer, this);
        }
    }

}
