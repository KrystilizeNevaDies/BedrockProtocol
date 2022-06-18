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

public interface SetLastHurtByPacket extends BedrockPacket {
    int entityTypeId;


    record v291 implements SetLastHurtByPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetLastHurtByPacket packet) {
            VarInts.writeInt(buffer, packet.getEntityTypeId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetLastHurtByPacket packet) {
            packet.setEntityTypeId(VarInts.readInt(buffer));
        }
    }

}
