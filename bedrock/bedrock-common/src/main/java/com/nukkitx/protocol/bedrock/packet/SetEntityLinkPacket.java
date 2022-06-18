package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.entity.EntityLinkData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface SetEntityLinkPacket extends BedrockPacket {
    EntityLinkData entityLink;


    record v291 implements SetEntityLinkPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityLinkPacket packet) {
            helper.writeEntityLink(buffer, packet.getEntityLink());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityLinkPacket packet) {
            packet.setEntityLink(helper.readEntityLink(buffer));
        }
    }

}
