package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface GuiDataPickItemPacket extends BedrockPacket {
    String description();
    String itemEffects();
    int hotbarSlot();


    record v291(String description, String itemEffects, int hotbarSlot) implements GuiDataPickItemPacket {
        public static final Interpreter<v291> INTERPRETER = Interpreter.generate(v291.class);
        private static final Deferer<v291> DEFERER = Deferer.generate(v291.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

}
