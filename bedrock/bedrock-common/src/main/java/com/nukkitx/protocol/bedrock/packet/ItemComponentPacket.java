package com.nukkitx.protocol.bedrock.packet;


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

import java.util.List;

/**
 * Definitions for custom component items added to the game
 */
interface ItemComponentPacket extends BedrockPacket {

    private final List<ComponentItemData> items = new ObjectArrayList<>();


    @Overrid

    public class ItemComponentReader_v419 implements BedrockPacketReader<ItemComponentPacket> {

        public static final ItemComponentReader_v419 INSTANCE = new ItemComponentReader_v419();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ItemComponentPacket packet) {
            helper.writeArray(buffer, packet.getItems(), (buf, packetHelper, item) -> {
                packetHelper.writeString(buf, item.getName());
                packetHelper.writeTag(buf, item.getData());
            });
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ItemComponentPacket packet) {
            helper.readArray(buffer, packet.getItems(), (buf, packetHelper) -> {
                String name = packetHelper.readString(buf);
                NbtMap data = packetHelper.readTag(buf);

                return new ComponentItemData(name, data);
            });
        }
    }

}
