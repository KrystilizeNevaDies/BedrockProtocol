package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CraftingEventPacket extends BedrockPacket {
    byte containerId();

    CraftingType valueType();

    UUID uuid();

    ItemData[] inputs();

    ItemData[] outputs();


    record v291(byte containerId, CraftingType valueType, UUID uuid, ItemData[] inputs,
                ItemData[] outputs) implements CraftingDataPacket, Codec291 {
        public static final Interpreter<CraftingEventPacket.v291> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull CraftingEventPacket.v291 interpret(@NotNull BitInput input) throws IOException {
                byte containerId = readByte(input);
                CraftingType valueType = CraftingType.values()[readInt(input)];
                UUID uuid = readUuid(input);
                ItemData[] inputs = readArray(input, ItemData.INTERPRETER);
                ItemData[] outputs = readArray(input, ItemData.INTERPRETER);
                return new CraftingEventPacket.v291(containerId, valueType, uuid, inputs, outputs);
            }

            ;
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeByte(output, containerId());
            writeInt(output, valueType().ordinal());
            writeUuid(output, uuid());
            writeArray(output, inputs());
            writeArray(output, outputs());
        }
    }

}
