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
    private EntityLinkData entityLink;


    public class SetEntityLinkReader_v291 implements BedrockPacketReader<SetEntityLinkPacket> {
        public static final SetEntityLinkReader_v291 INSTANCE = new SetEntityLinkReader_v291();


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
