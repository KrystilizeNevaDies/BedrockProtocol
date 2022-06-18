package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

interface PlayerStartItemCooldownPacket extends BedrockPacket {

    String itemCategory;
    int cooldownDuration;


    record v486 implements PlayerStartItemCooldownPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerStartItemCooldownPacket packet) {
            helper.writeString(buffer, packet.getItemCategory());
            VarInts.writeInt(buffer, packet.getCooldownDuration());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerStartItemCooldownPacket packet) {
            packet.setItemCategory(helper.readString(buffer));
            packet.setCooldownDuration(VarInts.readInt(buffer));
        }
    }

}
