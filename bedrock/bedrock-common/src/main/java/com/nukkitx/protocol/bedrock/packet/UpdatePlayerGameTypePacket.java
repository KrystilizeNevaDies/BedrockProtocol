package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.GameType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface UpdatePlayerGameTypePacket extends BedrockPacket {
    GameType gameType;
    long entityId;


    record v407 implements UpdatePlayerGameTypePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdatePlayerGameTypePacket packet) {
            VarInts.writeInt(buffer, packet.getGameType().ordinal());
            VarInts.writeLong(buffer, packet.getEntityId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdatePlayerGameTypePacket packet) {
            packet.setGameType(GameType.from(VarInts.readInt(buffer)));
            packet.setEntityId(VarInts.readLong(buffer));
        }
    }

}
