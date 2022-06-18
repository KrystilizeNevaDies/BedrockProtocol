package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemStackRequest;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

interface ItemStackRequestPacket extends BedrockPacket {
    final List<ItemStackRequest> requests = new ArrayList<>();


    record v407 implements ItemStackRequestPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ItemStackRequestPacket packet, BedrockSession
                session) {
            helper.writeArray(buffer, packet.getRequests(), (buf, requests) -> helper.writeItemStackRequest(buffer, session, requests));
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ItemStackRequestPacket
                packet, BedrockSession session) {
            helper.readArray(buffer, packet.getRequests(), buf -> helper.readItemStackRequest(buf, session));
        }
    }

}
