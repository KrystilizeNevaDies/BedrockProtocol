package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.EduSharedUriResource;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

interface EduUriResourcePacket extends BedrockPacket {
    private EduSharedUriResource eduSharedUriResource;


    @Overrid

    public class EduUriResourceReader_v465 implements BedrockPacketReader<EduUriResourcePacket> {

        public static final EduUriResourceReader_v465 INSTANCE = new EduUriResourceReader_v465();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EduUriResourcePacket packet) {
            helper.writeString(buffer, packet.getEduSharedUriResource().getButtonName());
            helper.writeString(buffer, packet.getEduSharedUriResource().getLinkUri());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EduUriResourcePacket packet) {
            packet.setEduSharedUriResource(new EduSharedUriResource(helper.readString(buffer), helper.readString(buffer)));
        }
    }

}
