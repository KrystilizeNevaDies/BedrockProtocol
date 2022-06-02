package com.nukkitx.protocol.bedrock.packet;

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


/**
 * CreativeContent is a packet sent by the server to set the creative inventory's content for a player.
 * Introduced in 1.16, this packet replaces the previous method - sending an InventoryContent packet with
 * creative inventory window ID.
 */
interface CreativeContentPacket extends BedrockPacket {
    /**
     * Item entries for the creative menu. Each item must have a unique ID for the net ID manager
     *
     * @see ItemData#fromNet
     */
    private ItemData[] contents;


    @Overrid

    public class CreativeContentReader_v407 implements BedrockPacketReader<CreativeContentPacket> {

        public static final CreativeContentReader_v407 INSTANCE = new CreativeContentReader_v407();

        private static final ItemData[] EMPTY = new ItemData[0];

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CreativeContentPacket packet, BedrockSession session) {
            helper.writeArray(buffer, packet.getContents(), session, this::writeCreativeItem);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CreativeContentPacket packet, BedrockSession session) {
            packet.setContents(helper.readArray(buffer, EMPTY, session, this::readCreativeItem));
        }

        protected ItemData readCreativeItem(ByteBuf buffer, BedrockPacketHelper helper, BedrockSession session) {
            int netId = VarInts.readUnsignedInt(buffer);
            ItemData item = helper.readItemInstance(buffer, session);
            item.setNetId(netId);
            return item;
        }

        protected void writeCreativeItem(ByteBuf buffer, BedrockPacketHelper helper, BedrockSession session, ItemData item) {
            VarInts.writeUnsignedInt(buffer, item.getNetId());
            helper.writeItemInstance(buffer, item, session);
        }
    }
}
