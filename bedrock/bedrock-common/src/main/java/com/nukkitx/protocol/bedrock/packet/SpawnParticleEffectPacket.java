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
    private int dimensionId;
    private long uniqueEntityId = -1;
    private Vector3f position;
    private String identifier;
    /**
     * @since v503
     */
    private Optional<String> molangVariablesJson;


    public class SpawnParticleEffectReader_v313 implements BedrockPacketReader<SpawnParticleEffectPacket> {
        public static final SpawnParticleEffectReader_v313 INSTANCE = new SpawnParticleEffectReader_v313();

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

    public class SpawnParticleEffectReader_v332 implements BedrockPacketReader<SpawnParticleEffectPacket> {
        public static final SpawnParticleEffectReader_v332 INSTANCE = new SpawnParticleEffectReader_v332();

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
