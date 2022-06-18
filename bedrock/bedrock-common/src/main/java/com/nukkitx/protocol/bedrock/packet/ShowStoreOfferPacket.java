package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface ShowStoreOfferPacket extends BedrockPacket {
    String offerId;
    boolean shownToAll;


    record v291 implements ShowStoreOfferPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ShowStoreOfferPacket packet) {
            helper.writeString(buffer, packet.getOfferId());
            buffer.writeBoolean(packet.isShownToAll());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ShowStoreOfferPacket packet) {
            packet.setOfferId(helper.readString(buffer));
            packet.setShownToAll(buffer.readBoolean());
        }
    }

}
