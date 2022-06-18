package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;


/**
 * CreativeContent is a packet sent by the server to set the creative inventory's content for a player.
 * Introduced in 1.16, this packet replaces the previous method - sending an InventoryContent packet with
 * creative inventory window ID.
 */
interface CreativeContentPacket extends BedrockPacket {
    /**
     * Item entries for the creative menu. Each item must have a unique ID for the net ID manager
     */
    ItemData[] contents();


    record v407(ItemData.Creative[] contents) implements CreativeContentPacket {
        public static final Interpreter<v407> INTERPRETER = new Interpreter<v407>() {
            @Override
            public @NotNull v407 interpret(@NotNull BitInput input) throws IOException {
                ItemData.Creative[] contents = readArray(input, ItemData.Creative.INTERPRETER);
                return new v407(contents);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeArray(output, contents());
        }
    }
}
