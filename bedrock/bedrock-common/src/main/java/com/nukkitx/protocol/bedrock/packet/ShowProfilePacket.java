package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface ShowProfilePacket extends BedrockPacket {
    private String xuid;


    public class ShowProfileReader_v291 implements BedrockPacketReader<ShowProfilePacket> {
        public static final ShowProfileReader_v291 INSTANCE = new ShowProfileReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ShowProfilePacket packet) {
            helper.writeString(buffer, packet.getXuid());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ShowProfilePacket packet) {
            packet.setXuid(helper.readString(buffer));
        }
    }

}
