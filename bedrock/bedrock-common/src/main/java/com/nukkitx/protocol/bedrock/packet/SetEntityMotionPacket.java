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

public interface SetEntityMotionPacket extends BedrockPacket {
    long runtimeEntityId;
    Vector3f motion;


    record v291 implements SetEntityMotionPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityMotionPacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            helper.writeVector3f(buffer, packet.getMotion());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityMotionPacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setMotion(helper.readVector3f(buffer));
        }
    }

}
