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

public interface MobEquipmentPacket extends BedrockPacket {
    long runtimeEntityId;
    ItemData item;
    int inventorySlot;
    int hotbarSlot;
    int containerId;


    record v291 implements MobEquipmentPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MobEquipmentPacket packet, BedrockSession
                session) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            helper.writeItem(buffer, packet.getItem(), session);
            buffer.writeByte(packet.getInventorySlot());
            buffer.writeByte(packet.getHotbarSlot());
            buffer.writeByte(packet.getContainerId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MobEquipmentPacket packet, BedrockSession
                session) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setItem(helper.readItem(buffer, session));
            packet.setInventorySlot(buffer.readUnsignedByte());
            packet.setHotbarSlot(buffer.readUnsignedByte());
            packet.setContainerId(buffer.readByte());
        }
    }

}
