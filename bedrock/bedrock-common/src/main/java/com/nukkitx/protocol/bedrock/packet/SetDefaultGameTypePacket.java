package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface SetDefaultGameTypePacket extends BedrockPacket {
    int gamemode;


    record v291 implements SetDefaultGameTypePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetDefaultGameTypePacket packet) {
            VarInts.writeInt(buffer, packet.getGamemode());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetDefaultGameTypePacket packet) {
            packet.setGamemode(VarInts.readInt(buffer));
        }
    }

}
