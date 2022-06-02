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

interface InventorySlotPacket extends BedrockPacket {
    private int containerId;
    private int slot;
    private ItemData item;


    public class InventorySlotReader_v291 implements BedrockPacketReader<InventorySlotPacket> {
        public static final InventorySlotReader_v291 INSTANCE = new InventorySlotReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InventorySlotPacket packet, BedrockSession session) {
            VarInts.writeUnsignedInt(buffer, packet.getContainerId());
            VarInts.writeUnsignedInt(buffer, packet.getSlot());
            helper.writeItem(buffer, packet.getItem(), session);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InventorySlotPacket packet, BedrockSession session) {
            packet.setContainerId(VarInts.readUnsignedInt(buffer));
            packet.setSlot(VarInts.readUnsignedInt(buffer));
            packet.setItem(helper.readItem(buffer, session));
        }
    }

    public class InventorySlotReader_v407 implements BedrockPacketReader<InventorySlotPacket> {
        public static final InventorySlotReader_v407 INSTANCE = new InventorySlotReader_v407();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InventorySlotPacket packet, BedrockSession session) {
            VarInts.writeUnsignedInt(buffer, packet.getContainerId());
            VarInts.writeUnsignedInt(buffer, packet.getSlot());
            helper.writeNetItem(buffer, packet.getItem(), session);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InventorySlotPacket packet, BedrockSession session) {
            packet.setContainerId(VarInts.readUnsignedInt(buffer));
            packet.setSlot(VarInts.readUnsignedInt(buffer));
            packet.setItem(helper.readNetItem(buffer, session));
        }
    }


}
