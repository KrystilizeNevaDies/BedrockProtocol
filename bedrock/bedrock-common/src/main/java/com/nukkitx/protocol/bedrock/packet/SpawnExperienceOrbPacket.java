package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface SpawnExperienceOrbPacket extends BedrockPacket {
    Vector3f position;
    int amount;


    record v291 implements SpawnExperienceOrbPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SpawnExperienceOrbPacket packet) {
            helper.writeVector3f(buffer, packet.getPosition());
            VarInts.writeInt(buffer, packet.getAmount());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SpawnExperienceOrbPacket packet) {
            packet.setPosition(helper.readVector3f(buffer));
            packet.setAmount(VarInts.readInt(buffer));
        }
    }

}
