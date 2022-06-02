package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemUseType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface CompletedUsingItemPacket extends BedrockPacket {
    private int itemId;
    private ItemUseType valueType;


    public class CompletedUsingItemReader_v388 implements BedrockPacketReader<CompletedUsingItemPacket> {

        public static final CompletedUsingItemReader_v388 INSTANCE = new CompletedUsingItemReader_v388();

        private static final ItemUseType[] VALUES = ItemUseType.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CompletedUsingItemPacket packet) {
            buffer.writeShortLE(packet.getItemId());
            buffer.writeIntLE(packet.getType().ordinal() - 1); // Enum starts at -1
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CompletedUsingItemPacket packet) {
            packet.setItemId(buffer.readUnsignedShortLE());
            packet.setType(VALUES[buffer.readIntLE() + 1]); // Enum starts at -1
        }
    }

}
