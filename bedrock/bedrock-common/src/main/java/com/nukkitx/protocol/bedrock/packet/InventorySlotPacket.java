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

public interface InventorySlotPacket extends BedrockPacket {
    int containerId;
    int slot;
    ItemData item;


    record v291 implements InventorySlotPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InventorySlotPacket packet, BedrockSession
                session) {
            VarInts.writeUnsignedInt(buffer, packet.getContainerId());
            VarInts.writeUnsignedInt(buffer, packet.getSlot());
            helper.writeItem(buffer, packet.getItem(), session);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InventorySlotPacket packet, BedrockSession
                session) {
            packet.setContainerId(VarInts.readUnsignedInt(buffer));
            packet.setSlot(VarInts.readUnsignedInt(buffer));
            packet.setItem(helper.readItem(buffer, session));
        }
    }

    record v407 implements InventorySlotPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InventorySlotPacket packet, BedrockSession
                session) {
            VarInts.writeUnsignedInt(buffer, packet.getContainerId());
            VarInts.writeUnsignedInt(buffer, packet.getSlot());
            helper.writeNetItem(buffer, packet.getItem(), session);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InventorySlotPacket packet, BedrockSession
                session) {
            packet.setContainerId(VarInts.readUnsignedInt(buffer));
            packet.setSlot(VarInts.readUnsignedInt(buffer));
            packet.setItem(helper.readNetItem(buffer, session));
        }
    }


}
