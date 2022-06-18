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

public interface RespawnPacket extends BedrockPacket {
    Vector3f position;
    State state;
    long runtimeEntityId; // Only used server bound and pretty pointless


    public enum State {
        SERVER_SEARCHING,
        SERVER_READY,
        CLIENT_READY
    }

    record v291 implements RespawnPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RespawnPacket packet) {
            helper.writeVector3f(buffer, packet.getPosition());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RespawnPacket packet) {
            packet.setPosition(helper.readVector3f(buffer));
        }
    }

    record v388 implements RespawnPacket {


        static final RespawnPacket.State[] VALUES = RespawnPacket.State.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RespawnPacket packet) {
            helper.writeVector3f(buffer, packet.getPosition());
            buffer.writeByte(packet.getState().ordinal());
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RespawnPacket packet) {
            packet.setPosition(helper.readVector3f(buffer));
            packet.setState(VALUES[buffer.readUnsignedByte()]);
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        }
    }

}
