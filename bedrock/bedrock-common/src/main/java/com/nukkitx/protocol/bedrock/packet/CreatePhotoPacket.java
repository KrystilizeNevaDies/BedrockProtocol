package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

interface CreatePhotoPacket extends BedrockPacket {
    private long id;
    private String photoName;
    private String photoItemName;


    @Overrid

    public class CreatePhotoReader_v465 implements BedrockPacketReader<CreatePhotoPacket> {

        public static final CreatePhotoReader_v465 INSTANCE = new CreatePhotoReader_v465();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CreatePhotoPacket packet) {
            buffer.writeLongLE(packet.getId());
            helper.writeString(buffer, packet.getPhotoName());
            helper.writeString(buffer, packet.getPhotoItemName());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CreatePhotoPacket packet) {
            packet.setId(buffer.readLongLE());
            packet.setPhotoName(helper.readString(buffer));
            packet.setPhotoItemName(helper.readString(buffer));
        }
    }

}
