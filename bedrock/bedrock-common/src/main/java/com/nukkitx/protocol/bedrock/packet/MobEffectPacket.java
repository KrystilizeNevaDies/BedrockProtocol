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

interface MobEffectPacket extends BedrockPacket {
    private long runtimeEntityId;
    private Event event;
    private int effectId;
    private int amplifier;
    private boolean particles;
    private int duration;


    public enum Event {
        NONE,
        ADD,
        MODIFY,
        REMOVE,
    }

    public class MobEffectReader_v291 implements BedrockPacketReader<MobEffectPacket> {
        public static final MobEffectReader_v291 INSTANCE = new MobEffectReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MobEffectPacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            buffer.writeByte(packet.getEvent().ordinal());
            VarInts.writeInt(buffer, packet.getEffectId());
            VarInts.writeInt(buffer, packet.getAmplifier());
            buffer.writeBoolean(packet.isParticles());
            VarInts.writeInt(buffer, packet.getDuration());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MobEffectPacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setEvent(MobEffectPacket.Event.values()[buffer.readUnsignedByte()]);
            packet.setEffectId(VarInts.readInt(buffer));
            packet.setAmplifier(VarInts.readInt(buffer));
            packet.setParticles(buffer.readBoolean());
            packet.setDuration(VarInts.readInt(buffer));
        }
    }

}
