package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface GuiDataPickItemPacket extends BedrockPacket {
    String description;
    String itemEffects;
    int hotbarSlot;


    record v291 implements GuiDataPickItemPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GuiDataPickItemPacket packet) {
            helper.writeString(buffer, packet.getDescription());
            helper.writeString(buffer, packet.getItemEffects());
            buffer.writeIntLE(packet.getHotbarSlot());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GuiDataPickItemPacket packet) {
            packet.setDescription(helper.readString(buffer));
            packet.setItemEffects(helper.readString(buffer));
            packet.setHotbarSlot(buffer.readIntLE());
        }
    }

}
