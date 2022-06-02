package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface FilterTextPacket extends BedrockPacket {
    private String text;
    private boolean fromServer;


    public class FilterTextReader_v422 implements BedrockPacketReader<FilterTextPacket> {

        public static final FilterTextReader_v422 INSTANCE = new FilterTextReader_v422();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, FilterTextPacket packet) {
            helper.writeString(buffer, packet.getText());
            buffer.writeBoolean(packet.isFromServer());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, FilterTextPacket packet) {
            packet.setText(helper.readString(buffer));
            packet.setFromServer(buffer.readBoolean());
        }
    }
}
