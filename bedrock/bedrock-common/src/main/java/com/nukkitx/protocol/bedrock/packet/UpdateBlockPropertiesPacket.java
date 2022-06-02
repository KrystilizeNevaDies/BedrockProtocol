package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface UpdateBlockPropertiesPacket extends BedrockPacket {
    private NbtMap properties;


    public class UpdateBlockPropertiesReader_v361 implements BedrockPacketReader<UpdateBlockPropertiesPacket> {
        public static final UpdateBlockPropertiesReader_v361 INSTANCE = new UpdateBlockPropertiesReader_v361();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateBlockPropertiesPacket packet) {
            helper.writeTag(buffer, packet.getProperties());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateBlockPropertiesPacket packet) {
            packet.setProperties(helper.readTag(buffer));
        }
    }

}
