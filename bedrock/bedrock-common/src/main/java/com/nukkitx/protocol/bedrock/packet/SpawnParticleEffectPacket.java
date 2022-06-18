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

import java.util.Optional;

interface SpawnParticleEffectPacket extends BedrockPacket {
    int dimensionId;
    long uniqueEntityId = -1;
    Vector3f position;
    String identifier;
    /**
     * @since v503
     */
    Optional<String> molangVariablesJson;


    record v313 implements SpawnParticleEffectPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SpawnParticleEffectPacket packet) {
            buffer.writeByte(packet.getDimensionId());
            helper.writeVector3f(buffer, packet.getPosition());
            helper.writeString(buffer, packet.getIdentifier());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SpawnParticleEffectPacket packet) {
            packet.setDimensionId(buffer.readUnsignedByte());
            packet.setPosition(helper.readVector3f(buffer));
            packet.setIdentifier(helper.readString(buffer));
        }
    }

    record v332 implements SpawnParticleEffectPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SpawnParticleEffectPacket packet) {
            buffer.writeByte(packet.getDimensionId());
            VarInts.writeLong(buffer, packet.getUniqueEntityId());
            helper.writeVector3f(buffer, packet.getPosition());
            helper.writeString(buffer, packet.getIdentifier());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SpawnParticleEffectPacket packet) {
            packet.setDimensionId(buffer.readUnsignedByte());
            packet.setUniqueEntityId(VarInts.readLong(buffer));
            packet.setPosition(helper.readVector3f(buffer));
            packet.setIdentifier(helper.readString(buffer));
        }
    }

}
