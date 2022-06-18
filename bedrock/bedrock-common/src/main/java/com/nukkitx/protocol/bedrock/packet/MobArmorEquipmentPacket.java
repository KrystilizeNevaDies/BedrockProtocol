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

public interface MobArmorEquipmentPacket extends BedrockPacket {
    long runtimeEntityId;
    ItemData helmet;
    ItemData chestplate;
    ItemData leggings;
    ItemData boots;


    record v291 implements MobArmorEquipmentPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MobArmorEquipmentPacket
                packet, BedrockSession session) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            helper.writeItem(buffer, packet.getHelmet(), session);
            helper.writeItem(buffer, packet.getChestplate(), session);
            helper.writeItem(buffer, packet.getLeggings(), session);
            helper.writeItem(buffer, packet.getBoots(), session);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MobArmorEquipmentPacket
                packet, BedrockSession session) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setHelmet(helper.readItem(buffer, session));
            packet.setChestplate(helper.readItem(buffer, session));
            packet.setLeggings(helper.readItem(buffer, session));
            packet.setBoots(helper.readItem(buffer, session));
        }
    }

}
