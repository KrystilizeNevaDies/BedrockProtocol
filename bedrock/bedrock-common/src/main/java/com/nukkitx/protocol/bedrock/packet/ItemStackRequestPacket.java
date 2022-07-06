package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemStackRequest;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

interface ItemStackRequestPacket extends BedrockPacket {
    ItemStackRequest[] requests();


    record v407(ItemStackRequest[] requests) implements ItemStackRequestPacket {
        public static final Interpreter<v407> INTERPRETER = Interpreter.generate(v407.class);
        private static final Deferer<v407> DEFERER = Deferer.generate(v407.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

}
