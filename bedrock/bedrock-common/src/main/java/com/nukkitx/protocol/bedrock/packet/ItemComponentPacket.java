package com.nukkitx.protocol.bedrock.packet;


import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.ComponentItemData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

/**
 * Definitions for custom component items added to the game
 */
interface ItemComponentPacket extends BedrockPacket {

    ComponentItemData[] items();


    record v419(ComponentItemData[] items) implements ItemComponentPacket {
        public static final Interpreter<v419> INTERPRETER = Interpreter.generate(v419.class);
        private static final Deferer<v419> DEFERER = Deferer.generate(v419.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

}
