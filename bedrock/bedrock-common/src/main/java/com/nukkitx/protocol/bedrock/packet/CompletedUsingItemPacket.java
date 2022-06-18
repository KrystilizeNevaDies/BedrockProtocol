package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.inventory.ItemUseType;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

interface CompletedUsingItemPacket extends BedrockPacket {
    short itemId();

    ItemUseType valueType();


    record v388(short itemId, ItemUseType valueType) implements CompletedUsingItemPacket {
        public static final Interpreter<v388> INTERPRETER = new Interpreter<v388>() {
            @Override
            public @NotNull v388 interpret(@NotNull BitInput input) throws IOException {
                short itemId = readShortLE(input);
                ItemUseType type = ItemUseType.values()[readIntLE(input) + 1]; // Enum starts at -1
                return new v388(itemId, type);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeShortLE(output, itemId());
            writeIntLE(output, valueType().ordinal() - 1); // Enum starts at -1
        }
    }

}
