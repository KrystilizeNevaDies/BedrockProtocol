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

public interface SetHealthPacket extends BedrockPacket {
    int health;


    record v291 implements SetHealthPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetHealthPacket packet) {
            VarInts.writeInt(buffer, packet.getHealth());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetHealthPacket packet) {
            packet.setHealth(VarInts.readInt(buffer));
        }
    }
}
